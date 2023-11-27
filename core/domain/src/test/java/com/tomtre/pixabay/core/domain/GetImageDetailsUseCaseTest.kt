package com.tomtre.pixabay.core.domain

import android.content.res.Resources.NotFoundException
import com.tomtre.pixabay.core.data.repository.ImageRepository
import com.tomtre.pixabay.core.model.ImageDetails
import com.tomtre.pixabay.core.model.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.coInvoking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldThrow
import org.junit.Test
import kotlin.coroutines.cancellation.CancellationException

class GetImageDetailsUseCaseTest {

    private val imageRepository = mockk<ImageRepository>()
    private val useCase = GetImageDetailsUseCase(imageRepository)

    @Test
    fun `return success when repository returns ImageDetails`() = runTest {
        val imageDetails = ImageDetails(
            id = 0,
            largeImageURL = "url",
            user = "user",
            likes = 100,
            tags = "window car",
            downloads = 33,
            comments = 22
        )
        coEvery { imageRepository.getImageDetails(any()) } returns imageDetails

        val value = useCase(0)

        value shouldBeEqualTo Result.Success(imageDetails)
        coVerify(exactly = 1) { imageRepository.getImageDetails(0) }
    }

    @Test
    fun `return error when repository returns null`() = runTest {
        coEvery { imageRepository.getImageDetails(any()) } returns null

        val value = useCase(0)

        value shouldBeInstanceOf Result.Error::class
        (value as Result.Error).exception shouldBeInstanceOf NotFoundException::class

        coVerify(exactly = 1) { imageRepository.getImageDetails(0) }
    }

    @Test
    fun `return error when repository returns any error`() = runTest {
        val exception = IllegalStateException()
        coEvery { imageRepository.getImageDetails(any()) } throws exception

        val value = useCase(0)

        value shouldBeEqualTo Result.Error(exception)

        coVerify(exactly = 1) { imageRepository.getImageDetails(0) }
    }

    @Test
    fun `should rethrow CancellationException`() = runTest {
        coEvery { imageRepository.getImageDetails(any()) } throws CancellationException()

        coInvoking { useCase(0) } shouldThrow CancellationException::class

        coVerify(exactly = 1) { imageRepository.getImageDetails(0) }
    }
}
