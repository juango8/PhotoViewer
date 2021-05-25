package com.juango.photoviewer

import android.app.Application
import com.juango.photoviewer.service.database.PhotoViewerDatabase
import com.juango.photoviewer.service.networking.RemoteApi
import com.juango.photoviewer.service.networking.buildApiService
import com.juango.photoviewer.service.repository.PhotoViewerRepository
import com.juango.photoviewer.service.repository.PhotoViewerRepositoryImpl

class App : Application() {

    companion object {
        private lateinit var instance: App

        private val database: PhotoViewerDatabase by lazy {
            PhotoViewerDatabase.buildDatabase(instance)
        }

        private val apiService by lazy { buildApiService() }

        private val remoteApi by lazy { RemoteApi(apiService) }

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