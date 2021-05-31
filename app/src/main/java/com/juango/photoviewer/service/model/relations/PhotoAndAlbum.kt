package com.juango.photoviewer.service.model.relations

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.juango.photoviewer.service.model.Album
import com.juango.photoviewer.service.model.Photo
import kotlinx.parcelize.Parcelize

@Parcelize
class PhotoAndAlbum(
    @Embedded
    val photo: Photo,
    @Relation(parentColumn = "albumId", entityColumn = "id")
    val album: Album
) : Parcelable
