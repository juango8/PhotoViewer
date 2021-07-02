package com.juango.photoviewer.di

import com.juango.photoviewer.service.repository.PhotoViewerRepository
import com.juango.photoviewer.service.repository.PhotoViewerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(photoViewerRepositoryImpl: PhotoViewerRepositoryImpl): PhotoViewerRepository

}