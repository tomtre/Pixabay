package com.tomtre.pixabay.feature.imagedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomtre.pixabay.core.domain.GetImageUseCase
import com.tomtre.pixabay.core.model.Result
import com.tomtre.pixabay.core.ui.util.UiText
import com.tomtre.pixabay.feature.imagedetails.navigation.ImageDetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getImageUseCase: GetImageUseCase
) : ViewModel() {
    private val imageDetailsArgs = ImageDetailsArgs(savedStateHandle)

    private val _state: MutableStateFlow<ImageDetailsState> = MutableStateFlow(ImageDetailsState(isLoading = true))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            when (val imageDetailsResult = getImageUseCase(imageDetailsArgs.imageId)) {
                is Result.Error -> _state.update { it.copy(isLoading = false, error = UiText.of(R.string.error_loading_image_details)) }
                is Result.Success -> _state.update { it.copy(isLoading = false, error = null, imageDetails = imageDetailsResult.data) }
            }
        }
    }
}
