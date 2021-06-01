package com.juango.photoviewer.service.networking

import com.juango.photoviewer.service.model.*

class RemoteApi(private val apiService: RemoteApiService) {

    suspend fun getPhotos(albumId: String): Result<List<Photo>> =
        try {
            val data = apiService.getPhotos(albumId)
            Success(data)
        } catch (error: Throwable) {
            Failure(error)
        }

    suspend fun getAlbums(): Result<List<Album>> =
        try {
            val data = apiService.getAlbums()
            Success(data)
        } catch (error: Throwable) {
            Failure(error)
        }

}
