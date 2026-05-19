package com.example.artlery.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ArtworkEntity::class, CommentEntity::class], version = 1, exportSchema = false)
abstract class ArtDatabase : RoomDatabase() {

    abstract fun artDao(): ArtDao

    companion object {
        @Volatile
        private var Instance: ArtDatabase? = null

        fun getDatabase(context: Context): ArtDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ArtDatabase::class.java,
                    "artlery_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}