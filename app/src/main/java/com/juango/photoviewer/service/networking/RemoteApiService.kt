package com.juango.photoviewer.service.networking

import com.juango.photoviewer.service.model.Photo
import retrofit2.http.GET

interface RemoteApiService {

    @GET("/photos")
    suspend fun getPhotos(): List<Photo>

}
