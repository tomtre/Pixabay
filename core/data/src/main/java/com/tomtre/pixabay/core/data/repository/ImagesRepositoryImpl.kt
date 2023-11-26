package com.tomtre.pixabay.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.tomtre.pixabay.core.data.paging.ImageRemoteMediatorFactory
import com.tomtre.pixabay.core.data.toDomain
import com.tomtre.pixabay.core.data.toDomainImageDetails
import com.tomtre.pixabay.core.database.dao.ImagesDao
import com.tomtre.pixabay.core.model.Image
import com.tomtre.pixabay.core.model.ImageDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesDao: ImagesDao,
    private val imageRemoteMediatorFactory: ImageRemoteMediatorFactory
) : ImagesRepository {

    override suspend fun getImageDetails(imageId: Int): ImageDetails? =
        imagesDao.getImage(imageId)?.toDomainImageDetails()

    @OptIn(ExperimentalPagingApi::class)
    override fun getImagesStream(query: String?): Flow<PagingData<Image>> {
        Timber.d("ImagesRepositoryImpl New query: $query")

        // TODO
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
        const val ITEMS_PER_PAGE = 20
    }
}
