package com.juango.photoviewer.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.juango.photoviewer.service.networking.RemoteApi
import com.juango.photoviewer.service.networking.RemoteApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        val client = OkHttpClient.Builder().build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): RemoteApiService =
        retrofit.create(RemoteApiService::class.java)

    @Singleton
    @Provides
    fun provideRemoteApi(apiService: RemoteApiService): RemoteApi = RemoteApi(apiService)

}