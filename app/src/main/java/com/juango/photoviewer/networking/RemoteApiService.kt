package com.juango.photoviewer.networking

import com.juango.photoviewer.model.Photo
import retrofit2.http.GET

interface RemoteApiService {

    @GET("/photos")
    suspend fun getPhotos(): List<Photo>

}
