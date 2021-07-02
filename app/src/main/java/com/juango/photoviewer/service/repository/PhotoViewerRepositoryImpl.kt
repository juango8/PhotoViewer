package com.juango.photoviewer.service.repository

import com.juango.photoviewer.service.database.dao.AlbumDao
import com.juango.photoviewer.service.database.dao.CommentDao
import com.juango.photoviewer.service.database.dao.PhotoDao
import com.juango.photoviewer.service.database.dao.PostDao
import com.juango.photoviewer.service.model.*
import com.juango.photoviewer.service.model.relations.PhotoAndAlbum
import com.juango.photoviewer.service.networking.RemoteApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoViewerRepositoryImpl @Inject constructor(
    private val photoDao: PhotoDao,
    private val albumDao: AlbumDao,
    private val commentDao: CommentDao,
    private val postDao: PostDao,
    private val remoteApi: RemoteApi
) : PhotoViewerRepository {

    @set:Inject
    var networkStatusChecker: Boolean = false

    override suspend fun getPhotos(albumId: String): List<Photo> {
        if (networkStatusChecker) {
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
        if (networkStatusChecker) {
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

    override suspend fun getPosts(): List<Post> {
        if (networkStatusChecker) {
            val result = remoteApi.getPost()
            if (result is Success) {
                result.data.forEach {
                    postDao.addPost(it)
                }
            }
        }
        return postDao.getPost()
    }

    override suspend fun getPostById(postId: String): Post = postDao.getPostById(postId)

    override suspend fun getCommentsByPostId(postId: String): List<Comment> {
        if (networkStatusChecker) {
            val result = remoteApi.getDetailComments(postId)
            if (result is Success) {
                result.data.forEach {
                    commentDao.addComment(it)
                }
            }
        }
        return commentDao.getCommentsByPost(postId)
    }

}