package com.juango.photoviewer.service.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juango.photoviewer.service.model.Comment

interface CommentDao {

    @Query("SELECT * FROM comment")
    suspend fun getComments(): List<Comment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addComment(comment: Comment)

    @Query("SELECT * FROM comment WHERE id = :commentId")
    suspend fun getCommentById(commentId: String): Comment
}