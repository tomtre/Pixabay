package com.tomtre.pixabay.core.data.repository

import androidx.paging.PagingData
import com.tomtre.pixabay.core.model.Image
import com.tomtre.pixabay.core.model.ImageDetails
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {

    suspend fun getImageDetails(imageId: Int): ImageDetails?

    fun getImagesStream(query: String? = null): Flow<PagingData<Image>>
}
