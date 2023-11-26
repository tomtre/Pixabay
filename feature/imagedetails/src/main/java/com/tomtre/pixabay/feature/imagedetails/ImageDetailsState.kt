package com.tomtre.pixabay.feature.imagedetails

import com.tomtre.pixabay.core.model.ImageDetails
import com.tomtre.pixabay.core.ui.util.UiText

data class ImageDetailsState(
    val isLoading: Boolean = false,
    val imageDetails: ImageDetails? = null,
    val error: UiText? = null
)
