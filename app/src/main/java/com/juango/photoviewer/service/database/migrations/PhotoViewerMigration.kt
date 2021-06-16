package com.juango.photoviewer.service.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE Album (userId INTEGER NOT NULL, id INTEGER PRIMARY KEY NOT NULL, title TEXT NOT NULL)")
    }
}

val migration_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE Post (userId INTEGER NOT NULL, id INTEGER PRIMARY KEY NOT NULL, title TEXT NOT NULL, body TEXT NOT NULL)")
        database.execSQL("CREATE TABLE Comment (postId INTEGER NOT NULL, id INTEGER PRIMARY KEY NOT NULL, name TEXT NOT NULL, email TEXT NOT NULL, body TEXT NOT NULL)")
    }
}