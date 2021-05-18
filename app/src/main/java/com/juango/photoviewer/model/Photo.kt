package com.juango.photoviewer.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Photo(
    val albumId: Int,
    @PrimaryKey
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)
