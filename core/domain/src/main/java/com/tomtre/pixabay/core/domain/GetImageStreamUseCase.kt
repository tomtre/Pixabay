package com.tomtre.pixabay.core.domain

import androidx.paging.PagingData
import com.tomtre.pixabay.core.data.repository.ImageRepository
import com.tomtre.pixabay.core.model.Image
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImageStreamUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) : (String?) -> Flow<PagingData<Image>> {

    override fun invoke(query: String?): Flow<PagingData<Image>> =
        imageRepository.getImagesStream(query)
}
