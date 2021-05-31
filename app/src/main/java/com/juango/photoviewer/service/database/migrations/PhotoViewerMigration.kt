package com.juango.photoviewer.service.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE Album (userId INTEGER NOT NULL, id INTEGER PRIMARY KEY NOT NULL, title TEXT NOT NULL)")
    }
}