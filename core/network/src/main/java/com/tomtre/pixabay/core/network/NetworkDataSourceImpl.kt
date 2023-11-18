package com.tomtre.pixabay.core.network

import com.tomtre.pixabay.core.network.model.CustomApiErrorResponse
import com.tomtre.pixabay.core.network.model.ImagesResponse
import com.tomtre.pixabay.core.network.util.Response
import com.tomtre.pixabay.core.network.util.get
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient
) : NetworkDataSource {

    override suspend fun getImages(query: String?): Response<CustomApiErrorResponse, ImagesResponse> =
        httpClient.get {
            query?.let {
                parameter("query", it)
            }
        }

    override suspend fun getImage(id: Int): Response<CustomApiErrorResponse, ImagesResponse> =
        httpClient.get {
            parameter("id", id)
        }
}