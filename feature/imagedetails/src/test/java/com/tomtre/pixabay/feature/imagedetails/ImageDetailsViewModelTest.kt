package com.tomtre.pixabay.feature.imagedetails

import androidx.lifecycle.SavedStateHandle
import com.tom.pixabay.core.testing.util.MainDispatcherExtension
import com.tomtre.pixabay.core.domain.GetImageDetailsUseCase
import com.tomtre.pixabay.core.model.ImageDetails
import com.tomtre.pixabay.core.model.Result
import com.tomtre.pixabay.feature.imagedetails.navigation.imageIdArg
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class ImageDetailsViewModelTest {

    private lateinit var viewModel: ImageDetailsViewModel

    private val getImageDetailsUseCase = mockk<GetImageDetailsUseCase>()
    private val savedStateHandle = SavedStateHandle(mapOf(imageIdArg to 0))

    @BeforeEach
    fun setup() {
        viewModel = ImageDetailsViewModel(savedStateHandle, getImageDetailsUseCase)
    }

    @Test
    fun `state is initially loading`() = runTest {
        viewModel.state.value shouldBeEqualTo ImageDetailsState(isLoading = true)
    }

    @Test
    fun `error is shown when error result comes from domain`() = runTest {
        coEvery { getImageDetailsUseCase.invoke(any()) } returns Result.Error()

        val values = mutableListOf<ImageDetailsState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.toList(values)
        }

        values[0] shouldBeEqualTo ImageDetailsState(isLoading = true)
        values[1].error.shouldNotBeNull()

        coVerify(exactly = 1) { getImageDetailsUseCase(0) }
    }

    @Test
    fun `ImageDetails is shown when success result comes from domain`() = runTest {
        coEvery { getImageDetailsUseCase.invoke(any()) } returns Result.Success(imageDetails)

        val values = mutableListOf<ImageDetailsState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.toList(values)
        }

        values[0] shouldBeEqualTo ImageDetailsState(isLoading = true)
        values[1] shouldBeEqualTo ImageDetailsState(isLoading = false, imageDetails = imageDetails, error = null)

        coVerify(exactly = 1) { getImageDetailsUseCase(0) }
    }

    private val imageDetails = ImageDetails(
        id = 0,
        largeImageURL = "url",
        user = "tom",
        tags = "window cat sky",
        likes = 55,
        downloads = 4534,
        comments = 44
    )
}
