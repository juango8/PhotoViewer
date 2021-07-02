package com.juango.photoviewer.di

import android.content.Context
import com.juango.photoviewer.service.database.dao.AlbumDao
import com.juango.photoviewer.service.database.dao.CommentDao
import com.juango.photoviewer.service.database.dao.PhotoDao
import com.juango.photoviewer.service.database.dao.PostDao
import com.juango.photoviewer.service.networking.RemoteApi
import com.juango.photoviewer.service.repository.PhotoViewerRepository
import com.juango.photoviewer.service.repository.PhotoViewerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        @ApplicationContext context: Context,
        photoDao: PhotoDao,
        albumDao: AlbumDao,
        commentDao: CommentDao,
        postDao: PostDao,
        remoteApi: RemoteApi
    ): PhotoViewerRepository {
        return PhotoViewerRepositoryImpl(
            context,
            photoDao,
            albumDao,
            commentDao,
            postDao,
            remoteApi
        )
    }
}