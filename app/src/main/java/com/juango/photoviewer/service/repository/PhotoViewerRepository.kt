package com.juango.photoviewer.service.repository

import com.juango.photoviewer.service.model.Album
import com.juango.photoviewer.service.model.Comment
import com.juango.photoviewer.service.model.Photo
import com.juango.photoviewer.service.model.Post
import com.juango.photoviewer.service.model.relations.PhotoAndAlbum

interface PhotoViewerRepository {

//    suspend fun addPhoto(photo: Photo)

    suspend fun getPhotos(albumId: String): List<Photo>

    suspend fun getPhotoById(photoId: String): Photo

    suspend fun getAlbums(): List<Album>

    suspend fun getPhotosByAlbum(albumId: String): List<PhotoAndAlbum>

    suspend fun getPosts(): List<Post>

    suspend fun getPostById(postId: String): Post

    suspend fun getCommentsByPostId(postId: String): List<Comment>

}
