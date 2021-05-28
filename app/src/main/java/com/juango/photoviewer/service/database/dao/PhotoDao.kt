package com.juango.photoviewer.service.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juango.photoviewer.service.model.Photo

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo")
    suspend fun getPhotos(): List<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto(photo: Photo)

    @Query("SELECT * FROM photo WHERE id = :photoId")
    suspend fun getPhotoById(photoId: String): Photo
}