package com.tomtre.pixabay.feature

import com.tomtre.pixabay.core.model.Image
import com.tomtre.pixabay.core.ui.util.UiText

data class HomeState(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val images: List<Image> = emptyList(),
    val navigateToDetails: Int? = null
)
