package com.tomtre.pixabay.core.network.di

import com.tomtre.pixabay.core.network.BuildConfig
import com.tomtre.pixabay.core.network.NetworkDataSource
import com.tomtre.pixabay.core.network.NetworkDataSourceImpl
import com.tomtre.pixabay.core.network.util.NetworkHttpClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module(includes = [NetworkModule.Bindings::class])
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /*

        @Provides
        @Named(ApiUrl)
        fun provideApiUrl(): String =
            BuildConfig.BACKEND_URL

        @Provides
        @Named(ApiKey)
        fun provideApiKey(): String =
            BuildConfig.API_KEY

    */
    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient =
        NetworkHttpClient(
            apiUrl = BuildConfig.BACKEND_URL,
            apiKey = BuildConfig.API_KEY
        )

    @Module
    @InstallIn(SingletonComponent::class)
    interface Bindings {

        @Singleton
        @Binds
        fun bindNetworkDataSource(networkDataSourceImpl: NetworkDataSourceImpl): NetworkDataSource
    }
}
