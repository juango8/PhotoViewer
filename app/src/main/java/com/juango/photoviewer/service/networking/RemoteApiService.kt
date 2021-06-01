package com.juango.photoviewer.service.networking

import com.juango.photoviewer.service.model.Album
import com.juango.photoviewer.service.model.Photo
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteApiService {

    @GET("/albums/{albumId}/photos")
    suspend fun getPhotos(@Path("albumId") id: String): List<Photo>

    @GET("/users/1/albums")
    suspend fun getAlbums(): List<Album>

}
