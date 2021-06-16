package com.juango.photoviewer.service.networking

import com.juango.photoviewer.service.model.Album
import com.juango.photoviewer.service.model.Comment
import com.juango.photoviewer.service.model.Photo
import com.juango.photoviewer.service.model.Post
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteApiService {

    @GET("/albums/{albumId}/photos")
    suspend fun getPhotos(@Path("albumId") id: String): List<Photo>

    @GET("/users/1/albums")
    suspend fun getAlbums(): List<Album>

    @GET("/posts")
    suspend fun getPost(): List<Post>

    @GET("/comments")
    suspend fun getDetailComments(@Query("postId") postId: Int): List<Comment>

}
