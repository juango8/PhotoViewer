package com.juango.photoviewer.service.repository

import com.juango.photoviewer.service.model.Photo

interface PhotoViewerRepository {

//    suspend fun addPhoto(photo: Photo)

    suspend fun getPhotos(): List<Photo>

    suspend fun getPhotoById(photoId: String): Photo

}
