package com.juango.photoviewer.service.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.juango.photoviewer.service.model.Album
import com.juango.photoviewer.service.model.Photo

class PhotosByAlbum(
    @Embedded
    val album: Album,
    @Relation(parentColumn = "id", entityColumn = "albumId")
    val photos: List<Photo>?
)