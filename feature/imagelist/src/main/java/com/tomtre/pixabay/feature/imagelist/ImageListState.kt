package com.tomtre.pixabay.feature.imagelist

data class ImageListState(
    val querySearch: String = "",
    val navigateToDetails: Int? = null
)
