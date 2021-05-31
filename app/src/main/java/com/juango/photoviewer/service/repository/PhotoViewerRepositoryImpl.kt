package com.juango.photoviewer.service.repository

import android.content.Context
import android.net.ConnectivityManager
import com.juango.photoviewer.service.database.dao.AlbumDao
import com.juango.photoviewer.service.database.dao.PhotoDao
import com.juango.photoviewer.service.model.Album
import com.juango.photoviewer.service.model.Photo
import com.juango.photoviewer.service.model.Success
import com.juango.photoviewer.service.networking.NetworkStatusChecker
import com.juango.photoviewer.service.networking.RemoteApi

class PhotoViewerRepositoryImpl(
    private val context: Context,
    private val photoDao: PhotoDao,
    private val albumDao: AlbumDao,
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

    override suspend fun getAlbums(): List<Album> {
        return emptyList()
    }

}