package com.juango.photoviewer.service.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity
@Serializable
@Parcelize
data class Comment(
    val postId: Int,
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    val body: String
) : Parcelable