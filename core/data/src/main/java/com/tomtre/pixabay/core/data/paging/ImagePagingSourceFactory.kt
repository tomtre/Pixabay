package com.tomtre.pixabay.core.data.paging

import com.tomtre.pixabay.core.network.NetworkDataSource
import javax.inject.Inject

class ImagePagingSourceFactory @Inject constructor(private val networkDataSource: NetworkDataSource) {

    fun getPagingSource(query: String?) =
        ImagePagingSource(query, networkDataSource)
}