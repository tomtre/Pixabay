package com.tomtre.pixabay.core.data.di

import com.tomtre.pixabay.core.data.repository.ImagesRepository
import com.tomtre.pixabay.core.data.repository.ImagesRepositoryImpl
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
    fun bindsImagesRepository(imagesRepositoryImpl: ImagesRepositoryImpl): ImagesRepository
}
