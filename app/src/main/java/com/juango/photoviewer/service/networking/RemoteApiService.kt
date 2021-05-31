package com.juango.photoviewer.service.networking

import com.juango.photoviewer.service.model.Album
import com.juango.photoviewer.service.model.Photo
import retrofit2.http.GET

interface RemoteApiService {

    @GET("/albums/1/photos")
    suspend fun getPhotos(): List<Photo>

    @GET("/users/1/albums")
    suspend fun getAlbums(): List<Album>

}
