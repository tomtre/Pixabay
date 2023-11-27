package com.tomtre.pixabay.core.domain

import android.content.res.Resources.NotFoundException
import com.tomtre.pixabay.core.data.repository.ImageRepository
import com.tomtre.pixabay.core.model.ImageDetails
import com.tomtre.pixabay.core.model.Result
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GetImageDetailsUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) : suspend (Int) -> Result<ImageDetails> {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun invoke(imageId: Int): Result<ImageDetails> =
        try {
            val imageDetails = imageRepository.getImageDetails(imageId)
            if (imageDetails != null) {
                Result.Success(imageDetails)
            } else {
                Result.Error(NotFoundException())
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            Result.Error(e)
        }
}
