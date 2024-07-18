package com.example.wineapp

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class WineApplication : Application() {

    companion object {
        lateinit var database: WineDatabase
    }

    override fun onCreate() {
        super.onCreate()

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE WineEntity ADD COLUMN isFavorite INTEGER DEFAULT 0 NOT NULL")
            }
        }

        database = Room.databaseBuilder(
            this,
            WineDatabase::
            class.java,
            "WineDatabase"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

}