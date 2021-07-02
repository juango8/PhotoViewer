package com.juango.photoviewer.di

import android.content.Context
import com.juango.photoviewer.service.database.PhotoViewerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        PhotoViewerDatabase.buildDatabase(appContext)

    @Singleton
    @Provides
    fun providePhotoDao(db: PhotoViewerDatabase) = db.photoDao()

    @Singleton
    @Provides
    fun provideAlbumDao(db: PhotoViewerDatabase) = db.albumDao()

    @Singleton
    @Provides
    fun provideCommentsDao(db: PhotoViewerDatabase) = db.commentDao()

    @Singleton
    @Provides
    fun providePostsDao(db: PhotoViewerDatabase) = db.postDao()

}