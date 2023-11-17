package com.tomtre.pixabay.core.model

data class ImageDetails(
    val id: Int,
    val largeImageURL: String,
    val user: String,
    val tags: String,
    val likes: Int,
    val downloads: Int,
    val comments: Int
)
