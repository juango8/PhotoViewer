package com.juango.photoviewer

import android.app.Application
import com.juango.photoviewer.database.PhotoViewerDatabase
import com.juango.photoviewer.networking.RemoteApi
import com.juango.photoviewer.networking.buildApiService
import com.juango.photoviewer.repository.PhotoViewerRepository
import com.juango.photoviewer.repository.PhotoViewerRepositoryImpl
import kotlinx.serialization.ExperimentalSerializationApi

class App : Application() {

    companion object {
        private lateinit var instance: App

        private val database: PhotoViewerDatabase by lazy {
            PhotoViewerDatabase.buildDatabase(instance)
        }

        @ExperimentalSerializationApi
        private val apiService by lazy { buildApiService() }

        @ExperimentalSerializationApi
        val remoteApi by lazy { RemoteApi(apiService) }

        val repository: PhotoViewerRepository by lazy {
            PhotoViewerRepositoryImpl(
                instance,
                database.photoDao(),
                remoteApi
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}