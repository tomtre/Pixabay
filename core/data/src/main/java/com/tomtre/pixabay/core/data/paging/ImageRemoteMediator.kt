package com.tomtre.pixabay.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.tomtre.pixabay.core.data.toEntity
import com.tomtre.pixabay.core.database.dao.ImagesDao
import com.tomtre.pixabay.core.database.dao.RemoteKeysDao
import com.tomtre.pixabay.core.database.model.ImageEntity
import com.tomtre.pixabay.core.database.model.RemoteKeys
import com.tomtre.pixabay.core.database.util.DatabaseTransactionProvider
import com.tomtre.pixabay.core.model.DomainError
import com.tomtre.pixabay.core.network.NetworkDataSource
import com.tomtre.pixabay.core.network.model.CustomApiErrorResponse
import com.tomtre.pixabay.core.network.model.ImagesResponse
import com.tomtre.pixabay.core.network.util.RequestError
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ImageRemoteMediator @Inject constructor(
    private val query: String?,
    private val networkDataSource: NetworkDataSource,
    private val imagesDao: ImagesDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val databaseTransactionProvider: DatabaseTransactionProvider
) : RemoteMediator<Int, ImageEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    @Suppress("ReturnCount")
    override suspend fun load(loadType: LoadType, state: PagingState<Int, ImageEntity>): MediatorResult {
        Timber.d("ImageRemoteMediator load() loadType: $loadType state: $state")
        val pageNumber = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: PIXABAY_STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    Timber.d(
                        "ImageRemoteMediator load() PREPEND RemoteKeys or prevKey is null, " +
                                "returning MediatorResult.Success(endOfPaginationReached = ${remoteKeys != null})"
                    )
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    Timber.d(
                        "ImageRemoteMediator load() APPEND RemoteKeys or nextKey is null, " +
                                "returning MediatorResult.Success(endOfPaginationReached = ${remoteKeys != null})"
                    )
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }
        Timber.d("ImageRemoteMediator pageNumber: $pageNumber")

        val networkResponse = networkDataSource.getImages(query = query, pageNumber, state.config.pageSize)
        return networkResponse
            .fold(
                ifLeft = { handleRequestError(it) },
                ifRight = { handleResponseSuccess(it, loadType, pageNumber) }
            )
    }

    private fun handleRequestError(requestError: RequestError<out CustomApiErrorResponse>): MediatorResult {
        return when (requestError) {
            is RequestError.Api -> MediatorResult.Error(DomainError.ApiError(requestError.response))
            is RequestError.Exception -> MediatorResult.Error(DomainError.UnknownError)
        }
    }

    private suspend fun handleResponseSuccess(imagesResponse: ImagesResponse, loadType: LoadType, pageNumber: Int): MediatorResult {
        val images = imagesResponse.hits
        val endOfPaginationReached = images.isEmpty()
        databaseTransactionProvider.withTransaction {
            if (loadType == LoadType.REFRESH) {
                remoteKeysDao.clearRemoteKeys()
                imagesDao.clearImages()
            }
            val prevKey = if (pageNumber == PIXABAY_STARTING_PAGE_INDEX) null else pageNumber - 1
            val nextKey = if (endOfPaginationReached) null else pageNumber + 1
            val remoteKeys = images.map { RemoteKeys(imageId = it.id, prevKey = prevKey, nextKey = nextKey) }
            val imageEntities = images.mapIndexed { index, networkHit -> networkHit.toEntity(popularity = pageNumber * 10 + index) }
            remoteKeysDao.insertAll(remoteKeys)
            imagesDao.insertAll(imageEntities)
        }
        return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ImageEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { imageId -> remoteKeysDao.getRemoteKeysImageId(imageId) }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ImageEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { image -> remoteKeysDao.getRemoteKeysImageId(image.id) }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ImageEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { image -> remoteKeysDao.getRemoteKeysImageId(image.id) }
    }

    companion object {
        private const val PIXABAY_STARTING_PAGE_INDEX = 1
    }
}
