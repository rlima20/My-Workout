package com.example.nutrition.mynutrition.domain.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nutrition.mynutrition.domain.room.dao.UserInfoDao
import com.example.nutrition.mynutrition.domain.room.entity.UserInfoEntity

private const val DATA_BASE_NAME = "database"

@Database(
    entities = [
        UserInfoEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao

    companion object {

        private lateinit var db: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            if (Companion::db.isInitialized) return db

            db = Room.databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = DATA_BASE_NAME,
            )
                .fallbackToDestructiveMigration()
                .build()

            return db
        }
    }
}