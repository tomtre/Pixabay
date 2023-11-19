package com.tomtre.pixabay.core.data.repository

import androidx.paging.PagingData
import com.tomtre.pixabay.core.model.Image
import com.tomtre.pixabay.core.model.ImageDetails
import com.tomtre.pixabay.core.model.Result
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    suspend fun getImages(query: String? = null): Result<List<Image>>

    suspend fun getImage(id: Int): Result<ImageDetails>

    fun getImagesStream(query: String? = null): Flow<PagingData<Image>>
}
