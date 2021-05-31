package com.juango.photoviewer.service.repository

import com.juango.photoviewer.service.model.Album
import com.juango.photoviewer.service.model.Photo
import com.juango.photoviewer.service.model.relations.PhotoAndAlbum

interface PhotoViewerRepository {

//    suspend fun addPhoto(photo: Photo)

    suspend fun getPhotos(): List<Photo>

    suspend fun getPhotoById(photoId: String): Photo

    suspend fun getAlbums(): List<Album>

    suspend fun getPhotosByAlbum(albumId: String): List<PhotoAndAlbum>

}
