package com.juango.photoviewer.service.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.juango.photoviewer.service.database.dao.PhotoDao
import com.juango.photoviewer.service.model.Photo

const val DATABASE_VERSION = 1

@Database(
    entities = [Photo::class],
    version = DATABASE_VERSION
)
abstract class PhotoViewerDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "PhotoViewer"

        fun buildDatabase(context: Context): PhotoViewerDatabase {
            return Room.databaseBuilder(
                context,
                PhotoViewerDatabase::class.java,
                DATABASE_NAME
            )
                .allowMainThreadQueries()
                .build()
        }
    }

    abstract fun photoDao(): PhotoDao
}