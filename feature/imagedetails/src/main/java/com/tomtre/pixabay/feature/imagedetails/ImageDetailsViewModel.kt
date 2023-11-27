package com.tomtre.pixabay.feature.imagedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomczyn.coroutines.Launched
import com.tomczyn.coroutines.stateInMerge
import com.tomtre.pixabay.core.domain.GetImageDetailsUseCase
import com.tomtre.pixabay.core.model.Result
import com.tomtre.pixabay.core.ui.util.UiText
import com.tomtre.pixabay.feature.imagedetails.navigation.ImageDetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getImageDetailsUseCase: GetImageDetailsUseCase
) : ViewModel() {

    private val imageDetailsArgs = ImageDetailsArgs(savedStateHandle)

    private val _state: MutableStateFlow<ImageDetailsState> = MutableStateFlow(ImageDetailsState(isLoading = true))
        .stateInMerge(
            scope = viewModelScope,
            launched = Launched.WhileSubscribed(5_000),
            {
                flow { emit(getImageDetailsUseCase(imageDetailsArgs.imageId)) }
                    .onEachToState { state, result ->
                        when (result) {
                            is Result.Error -> state.copy(isLoading = false, error = UiText.of(R.string.error_loading_image_details))
                            is Result.Success -> state.copy(isLoading = false, error = null, imageDetails = result.data)
                        }
                    }
            }
        )
    val state = _state.asStateFlow()
}
