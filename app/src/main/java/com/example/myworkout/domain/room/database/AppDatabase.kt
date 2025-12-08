package com.example.myworkout.domain.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myworkout.domain.room.dao.GroupSubgroupDao
import com.example.myworkout.domain.room.dao.MuscleGroupDao
import com.example.myworkout.domain.room.dao.MuscleGroupMuscleSubGroupDao
import com.example.myworkout.domain.room.dao.MuscleSubGroupDao
import com.example.myworkout.domain.room.dao.SubGroupDao
import com.example.myworkout.domain.room.dao.TrainingDao
import com.example.myworkout.domain.room.dao.TrainingMuscleGroupDao
import com.example.myworkout.domain.room.entity.MuscleGroupEntity
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.MuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.TrainingEntity
import com.example.myworkout.domain.room.entity.TrainingMuscleGroupEntity
import com.example.myworkout.domain.room.entity.training.homescreen.GroupSubGroupEntity
import com.example.myworkout.domain.room.entity.training.homescreen.SubGroupEntity

private const val DATA_BASE_NAME = "database"

@Database(
    entities = [
        MuscleSubGroupEntity::class,
        MuscleGroupEntity::class,
        TrainingEntity::class,
        MuscleGroupMuscleSubGroupEntity::class,
        TrainingMuscleGroupEntity::class,
        SubGroupEntity::class,
        GroupSubGroupEntity::class
    ],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
    abstract fun muscleGroupDao(): MuscleGroupDao
    abstract fun muscleSubGroupDao(): MuscleSubGroupDao
    abstract fun muscleGroupMuscleSubGroupDao(): MuscleGroupMuscleSubGroupDao
    abstract fun trainingMuscleGroupDao(): TrainingMuscleGroupDao
    abstract fun subGroupDao(): SubGroupDao
    abstract fun groupSubgroupDao(): GroupSubgroupDao

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