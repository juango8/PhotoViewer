package com.juango.photoviewer.service.database.dao

import androidx.room.*
import com.juango.photoviewer.service.model.Album
import com.juango.photoviewer.service.model.relations.PhotosByAlbum

@Dao
interface AlbumDao {

    @Query("SELECT * FROM album")
    suspend fun getAlbums(): List<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlbum(album: Album)

    @Transaction
    @Query("SELECT * FROM album WHERE id = :albumId")
    suspend fun getPhotosByAlbum(albumId: String): PhotosByAlbum
}