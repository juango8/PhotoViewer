package com.juango.photoviewer.service.repository

import android.net.Uri
import com.juango.photoviewer.service.model.Album
import com.juango.photoviewer.service.model.Photo
import com.juango.photoviewer.service.model.relations.PhotoAndAlbum

interface PhotoViewerRepository {

//    suspend fun addPhoto(photo: Photo)

    suspend fun getPhotos(albumId: String): List<Photo>

    suspend fun getPhotoById(photoId: String): Photo

    suspend fun getAlbums(): List<Album>

    suspend fun getPhotosByAlbum(albumId: String): List<PhotoAndAlbum>

    fun saveImageInCache(photo: Photo): Uri

}
