package com.tomtre.pixabay.core.data.repository

import com.tomtre.pixabay.core.model.Image
import com.tomtre.pixabay.core.model.ImageDetails
import com.tomtre.pixabay.core.model.Result

interface ImagesRepository {
    suspend fun getImages(query: String? = null): Result<List<Image>>

    suspend fun getImage(id: Int): Result<ImageDetails>
}
