package com.juango.photoviewer.service.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juango.photoviewer.service.model.Album

@Dao
interface AlbumDao {

    @Query("SELECT * FROM album")
    suspend fun getAlbums(): List<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlbum(album: Album)

}