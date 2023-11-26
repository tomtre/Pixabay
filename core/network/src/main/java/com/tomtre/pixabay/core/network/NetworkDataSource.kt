package com.tomtre.pixabay.core.network

import com.tomtre.pixabay.core.network.model.CustomApiErrorResponse
import com.tomtre.pixabay.core.network.model.ImagesResponse
import com.tomtre.pixabay.core.network.util.Response

interface NetworkDataSource {
    suspend fun getImages(query: String? = null, page: Int? = 1, loadSize: Int? = 20): Response<CustomApiErrorResponse, ImagesResponse>
}
