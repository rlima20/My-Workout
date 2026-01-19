package com.example.nutrition.mynutrition.domain.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nutrition.mynutrition.domain.room.dao.CalorieGoalDao
import com.example.nutrition.mynutrition.domain.room.dao.UserInfoDao
import com.example.nutrition.mynutrition.domain.room.entity.CalorieGoalEntity
import com.example.nutrition.mynutrition.domain.room.entity.MacroResultEntity
import com.example.nutrition.mynutrition.domain.room.entity.UserInfoEntity

private const val DATA_BASE_NAME = "database"

@Database(
    entities = [
        UserInfoEntity::class,
        CalorieGoalEntity::class,
        MacroResultEntity::class
    ],
    version = 5
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao
    abstract fun calorieGoalDao(): CalorieGoalDao

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