package com.tomtre.pixabay.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.tomtre.pixabay.core.data.paging.ImageRemoteMediatorFactory
import com.tomtre.pixabay.core.data.toDomain
import com.tomtre.pixabay.core.data.toDomainImage
import com.tomtre.pixabay.core.data.toDomainImageDetails
import com.tomtre.pixabay.core.database.dao.ImagesDao
import com.tomtre.pixabay.core.model.Image
import com.tomtre.pixabay.core.model.ImageDetails
import com.tomtre.pixabay.core.model.Result
import com.tomtre.pixabay.core.network.NetworkDataSource
import com.tomtre.pixabay.core.network.util.RequestError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val imagesDao: ImagesDao,
    private val imageRemoteMediatorFactory: ImageRemoteMediatorFactory
) : ImagesRepository {
    override suspend fun getImages(query: String?): Result<List<Image>> =
        networkDataSource.getImages(query)
            .fold(
                ifLeft = { requestError ->
                    when (requestError) {
                        is RequestError.Api -> Result.ApiError(requestError.response)
                        is RequestError.Exception -> Result.Error(requestError.throwable)
                    }
                },
                ifRight = { Result.Success(it.hits.toDomainImage()) }
            )

    override suspend fun getImage(id: Int): Result<ImageDetails> =
        networkDataSource.getImage(id)
            .fold(
                ifLeft = { requestError ->
                    when (requestError) {
                        is RequestError.Api -> Result.ApiError(requestError.response)
                        is RequestError.Exception -> Result.Error(requestError.throwable)
                    }
                },
                ifRight = {
                    val hitNetwork = it.hits.firstOrNull() ?: return@fold Result.Error()
                    Result.Success(hitNetwork.toDomainImageDetails())
                }
            )

//    override fun getImagesStream(query: String?): Flow<PagingData<Image>> {
//        Timber.d("ImagesRepositoryImpl New query: $query")
//        return Pager(
//            config = PagingConfig(ITEMS_PER_PAGE, enablePlaceholders = false),
//            pagingSourceFactory = { imagePagingSourceFactory.getPagingSource(query) }
//        ).flow
//    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getImagesStream(query: String?): Flow<PagingData<Image>> {
        Timber.d("ImagesRepositoryImpl New query: $query")

        val dbQuery = query?.let { "%${it.replace(' ', '%')}%" } ?: ""

        val pagingSourceFactory = { imagesDao.observeImagesByName() }
        val flow = Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
            remoteMediator = imageRemoteMediatorFactory.getRemoteMediator(query),
            pagingSourceFactory = pagingSourceFactory
        ).flow

        return flow.map { pagingData ->
            pagingData.map { imageEntity -> imageEntity.toDomain() }
        }
    }

    companion object {
        const val ITEMS_PER_PAGE = 10
    }
}
