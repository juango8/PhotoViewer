package com.juango.photoviewer.repository

import com.juango.photoviewer.model.Photo

interface PhotoViewerRepository {

//    suspend fun addPhoto(photo: Photo)

    suspend fun getPhotos(): List<Photo>

    suspend fun getPhotoById(photoId: String): Photo

}
