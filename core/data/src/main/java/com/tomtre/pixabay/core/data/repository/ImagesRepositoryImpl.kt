package com.tomtre.pixabay.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tomtre.pixabay.core.data.paging.ImagePagingSourceFactory
import com.tomtre.pixabay.core.data.toDomainImage
import com.tomtre.pixabay.core.data.toDomainImageDetails
import com.tomtre.pixabay.core.model.Image
import com.tomtre.pixabay.core.model.ImageDetails
import com.tomtre.pixabay.core.model.Result
import com.tomtre.pixabay.core.network.NetworkDataSource
import com.tomtre.pixabay.core.network.util.RequestError
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val imagePagingSourceFactory: ImagePagingSourceFactory
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

    override fun getImagesStream(query: String?): Flow<PagingData<Image>> {
        Timber.d("ImagesRepositoryImpl New query: $query")
        return Pager(
            config = PagingConfig(ITEMS_PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = { imagePagingSourceFactory.getPagingSource(query) }
        ).flow
    }

    companion object {
        const val ITEMS_PER_PAGE = 15
    }
}
