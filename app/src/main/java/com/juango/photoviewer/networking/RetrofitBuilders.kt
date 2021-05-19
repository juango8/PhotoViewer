package com.juango.photoviewer.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

const val BASE_URL = "https://jsonplaceholder.typicode.com"

fun buildClient(): OkHttpClient =
    OkHttpClient.Builder()
        .build()


fun buildRetrofit(): Retrofit {
    val contentType = "application/json".toMediaType()

    return Retrofit.Builder()
        .client(buildClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
}

fun buildApiService(): RemoteApiService =
    buildRetrofit().create(RemoteApiService::class.java)