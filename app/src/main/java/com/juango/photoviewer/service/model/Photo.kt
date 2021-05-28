package com.juango.photoviewer.service.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity
@Serializable
@Parcelize
data class Photo(
    val albumId: Int,
    @PrimaryKey
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) : Parcelable
