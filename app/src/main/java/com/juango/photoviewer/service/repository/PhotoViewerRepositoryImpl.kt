package com.juango.photoviewer.service.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.Uri
import androidx.core.content.FileProvider
import com.juango.photoviewer.R
import com.juango.photoviewer.service.database.dao.AlbumDao
import com.juango.photoviewer.service.database.dao.PhotoDao
import com.juango.photoviewer.service.model.Album
import com.juango.photoviewer.service.model.Photo
import com.juango.photoviewer.service.model.Success
import com.juango.photoviewer.service.model.relations.PhotoAndAlbum
import com.juango.photoviewer.service.networking.NetworkStatusChecker
import com.juango.photoviewer.service.networking.RemoteApi
import com.juango.photoviewer.viewmodel.PhotoDetailViewModel.Companion.FILE_AUTHORITY
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class PhotoViewerRepositoryImpl(
    private val context: Context,
    private val photoDao: PhotoDao,
    private val albumDao: AlbumDao,
    private val remoteApi: RemoteApi,

    ) : PhotoViewerRepository {

    private val networkStatusChecker by lazy {
        NetworkStatusChecker(context.getSystemService(ConnectivityManager::class.java))
    }

    override suspend fun getPhotos(albumId: String): List<Photo> {
        if (networkStatusChecker.hasInternetConnection()) {
            val result = remoteApi.getPhotos(albumId)
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
        if (networkStatusChecker.hasInternetConnection()) {
            val result = remoteApi.getAlbums()
            if (result is Success) {
                val onlyTwo = result.data.slice(0..1)
                onlyTwo.forEach {
                    albumDao.addAlbum(it)
                }
            }
        }
        return albumDao.getAlbums()
    }

    override suspend fun getPhotosByAlbum(albumId: String): List<PhotoAndAlbum> {
        getPhotos(albumId)
        getAlbums()
        albumDao.getPhotosByAlbum(albumId).let { photosByAlbum ->
            val photos = photosByAlbum.photos ?: return emptyList()

            return photos.map { PhotoAndAlbum(it, photosByAlbum.album) }
        }
    }

    override fun saveImageInCache(photo: Photo): Uri {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_android_red)
        val file = File(context.cacheDir.absolutePath, "test.jpg")
        try {
            val out: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return FileProvider.getUriForFile(context, FILE_AUTHORITY, file)
    }

}