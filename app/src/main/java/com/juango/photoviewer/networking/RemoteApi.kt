package com.juango.photoviewer.networking

import com.juango.photoviewer.model.Failure
import com.juango.photoviewer.model.Photo
import com.juango.photoviewer.model.Result
import com.juango.photoviewer.model.Success

class RemoteApi(private val apiService: RemoteApiService) {

    suspend fun getPhotos(): Result<List<Photo>> =
        try {
            val data = apiService.getPhotos()
            Success(data)
        } catch (error: Throwable) {
            Failure(error)
        }

}
