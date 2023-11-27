package com.tomtre.pixabay.core.data.di

import com.tomtre.pixabay.core.data.repository.ImageRepository
import com.tomtre.pixabay.core.data.repository.ImageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindsImagesRepository(imagesRepositoryImpl: ImageRepositoryImpl): ImageRepository
}
