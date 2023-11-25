package com.tomtre.pixabay.core.database.di

import android.content.Context
import androidx.room.Room
import com.tomtre.pixabay.core.database.PixabayDatabase
import com.tomtre.pixabay.core.database.dao.ImagesDao
import com.tomtre.pixabay.core.database.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PixabayDatabase =
        Room.databaseBuilder(
            context,
            PixabayDatabase::class.java,
            "pixabay-database"
        )
            .build()

    @Provides
    fun provideImagesDao(database: PixabayDatabase): ImagesDao =
        database.imagesDao()

    @Provides
    fun provideRemoteKeysDao(database: PixabayDatabase): RemoteKeysDao =
        database.remoteKeysDao()
}
