package com.juango.photoviewer.service.networking

import com.juango.photoviewer.service.model.Failure
import com.juango.photoviewer.service.model.Photo
import com.juango.photoviewer.service.model.Result
import com.juango.photoviewer.service.model.Success

class RemoteApi(private val apiService: RemoteApiService) {

    suspend fun getPhotos(): Result<List<Photo>> =
        try {
            val data = apiService.getPhotos()
            Success(data)
        } catch (error: Throwable) {
            Failure(error)
        }

}
