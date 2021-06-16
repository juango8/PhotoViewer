package com.juango.photoviewer.service.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.juango.photoviewer.service.database.dao.AlbumDao
import com.juango.photoviewer.service.database.dao.CommentDao
import com.juango.photoviewer.service.database.dao.PhotoDao
import com.juango.photoviewer.service.database.dao.PostDao
import com.juango.photoviewer.service.database.migrations.migration_1_2
import com.juango.photoviewer.service.database.migrations.migration_2_3
import com.juango.photoviewer.service.model.Album
import com.juango.photoviewer.service.model.Comment
import com.juango.photoviewer.service.model.Photo
import com.juango.photoviewer.service.model.Post

const val DATABASE_VERSION = 3

@Database(
    entities = [Photo::class, Album::class, Post::class, Comment::class],
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
                .addMigrations(migration_1_2)
                .addMigrations(migration_2_3)
                .build()
        }
    }

    abstract fun photoDao(): PhotoDao

    abstract fun albumDao(): AlbumDao

    abstract fun postDao(): PostDao

    abstract fun commentDao(): CommentDao
}