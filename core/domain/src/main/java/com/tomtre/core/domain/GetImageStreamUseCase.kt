package com.tomtre.core.domain

import androidx.paging.PagingData
import com.tomtre.pixabay.core.data.repository.ImagesRepository
import com.tomtre.pixabay.core.model.Image
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImageStreamUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) : (String?) -> Flow<PagingData<Image>> {

    override fun invoke(query: String?): Flow<PagingData<Image>> =
        imagesRepository.getImagesStream(query)
}
