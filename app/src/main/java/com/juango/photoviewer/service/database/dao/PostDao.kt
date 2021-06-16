package com.juango.photoviewer.service.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juango.photoviewer.service.model.Post

@Dao
interface PostDao {

    @Query("SELECT * FROM post")
    suspend fun getPost(): List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPost(post: Post)

    @Query("SELECT * FROM post WHERE id = :postId")
    suspend fun getPostById(postId: String): Post

}