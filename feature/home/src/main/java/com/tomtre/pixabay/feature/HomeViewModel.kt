package com.tomtre.pixabay.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tomtre.core.common.MutableBehaviorFlow
import com.tomtre.pixabay.core.data.repository.ImagesRepository
import com.tomtre.pixabay.core.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val imagesRepository: ImagesRepository,
) : ViewModel() {

    private val refreshEvents = MutableBehaviorFlow(Unit)

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState(isLoading = true))
    val state = _state.asStateFlow()

    val items: Flow<PagingData<Image>> =
        imagesRepository.getImagesStream()
            .cachedIn(viewModelScope)

//    init {
//        viewModelScope.launch {
//            refreshEvents
//                .mapLatest {
//                    imagesRepository.getImages("window tom")
//                }
//                .collect { result ->
//                    when (result) {
//                        is Result.ApiError -> _state.update {
//                            it.copy(isLoading = false, errorMessage = UiText.of(result.message), images = emptyList())
//                        }
//
//                        is Result.Error -> _state.update {
//                            it.copy(isLoading = false, errorMessage = UiText.of(R.string.error_network), images = emptyList())
//                        }
//
//                        is Result.Success -> _state.update { it.copy(isLoading = false, errorMessage = null, images = result.data) }
//                    }
//                }
//        }
//    }

    fun refresh() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            refreshEvents.emit(Unit)
        }
    }

    fun clearNavigation() {
        _state.update { it.copy(navigateToDetails = null) }
    }

    fun onImageItemClick(id: Int) {
        _state.update { it.copy(navigateToDetails = id) }
    }
}