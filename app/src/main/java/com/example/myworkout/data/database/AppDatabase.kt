package com.example.myworkout.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myworkout.data.model.Training

@Database(entities = [Training::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
}