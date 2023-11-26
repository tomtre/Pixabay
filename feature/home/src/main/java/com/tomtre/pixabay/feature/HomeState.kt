package com.tomtre.pixabay.feature

data class HomeState(
    val querySearch: String = "",
    val navigateToDetails: Int? = null
)
