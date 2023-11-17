package com.tomtre.pixabay.core.data.repository

import com.tomtre.pixabay.core.data.toDomainImage
import com.tomtre.pixabay.core.data.toDomainImageDetails
import com.tomtre.pixabay.core.model.Image
import com.tomtre.pixabay.core.model.ImageDetails
import com.tomtre.pixabay.core.model.Result
import com.tomtre.pixabay.core.network.NetworkDataSource
import com.tomtre.pixabay.core.network.util.RequestError
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource
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
}
