package com.juango.photoviewer.repository

import android.content.Context
import android.net.ConnectivityManager
import com.juango.photoviewer.database.dao.PhotoDao
import com.juango.photoviewer.model.Photo
import com.juango.photoviewer.model.Success
import com.juango.photoviewer.networking.NetworkStatusChecker
import com.juango.photoviewer.networking.RemoteApi

class PhotoViewerRepositoryImpl(
    private val context: Context,
    private val photoDao: PhotoDao,
    private val remoteApi: RemoteApi,

    ) : PhotoViewerRepository {

    private val networkStatusChecker by lazy {
        NetworkStatusChecker(context.getSystemService(ConnectivityManager::class.java))
    }

    override suspend fun getPhotos(): List<Photo> {
        if (networkStatusChecker.hasInternetConnection()) {
            val result = remoteApi.getPhotos()
            if (result is Success) {
                val firstTwentyFive = result.data.slice(0..24)
                firstTwentyFive.forEach {
                    photoDao.addPhoto(it)
                }
            }
        }
        return photoDao.getPhotos()
    }

    override suspend fun getPhotoById(photoId: String): Photo = photoDao.getPhotoById(photoId)

}