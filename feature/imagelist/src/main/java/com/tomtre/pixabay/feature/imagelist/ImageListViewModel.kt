package com.tomtre.pixabay.feature.imagelist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tomtre.pixabay.core.domain.GetImageStreamUseCase
import com.tomtre.pixabay.core.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    getImageStreamUseCase: GetImageStreamUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableStateFlow<ImageListState> =
        MutableStateFlow(ImageListState(querySearch = savedStateHandle[LAST_SEARCH_QUERY_KEY] ?: INITIAL_QUERY))
    val state = _state.asStateFlow()

    private val searchQueryString: MutableStateFlow<String> = MutableStateFlow(INITIAL_QUERY)

    val items: Flow<PagingData<Image>> =
        searchQueryString
            .debounce(500)
            .distinctUntilChanged()
            .flatMapLatest { getImageStreamUseCase(query = it) }
            .cachedIn(viewModelScope)

    fun clearNavigation() {
        _state.update { it.copy(navigateToDetails = null) }
    }

    fun onImageItemClick(id: Int) {
        _state.update { it.copy(navigateToDetails = id) }
    }

    fun onSearchQueryTextChange(text: String) {
        savedStateHandle[LAST_SEARCH_QUERY_KEY] = text
        _state.update { it.copy(querySearch = text) }
        searchQueryString.value = text
    }

    companion object {
        private const val INITIAL_QUERY = "fruits"
        private const val LAST_SEARCH_QUERY_KEY = "last_search_query_key"
    }
}
