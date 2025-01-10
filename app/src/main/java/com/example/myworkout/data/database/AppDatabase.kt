package com.example.myworkout.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myworkout.data.database.entity.TrainingEntity

private const val DATA_BASE_NAME = "mystore_database"

@Database(
    entities = [TrainingEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trainingDao(): TrainingDao

    companion object {

        private lateinit var db: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            if (::db.isInitialized) return db

            db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATA_BASE_NAME,
            ).build()

            return db
        }
    }
}