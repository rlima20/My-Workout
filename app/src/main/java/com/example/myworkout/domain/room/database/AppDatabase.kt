package com.example.myworkout.domain.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myworkout.domain.room.dao.MuscleGroupDao
import com.example.myworkout.domain.room.dao.MuscleGroupMuscleSubGroupDao
import com.example.myworkout.domain.room.dao.MuscleSubGroupDao
import com.example.myworkout.domain.room.dao.TrainingDao
import com.example.myworkout.domain.room.dao.TrainingMuscleGroupDao
import com.example.myworkout.domain.room.entity.MuscleGroupEntity
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.MuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.TrainingEntity
import com.example.myworkout.domain.room.entity.TrainingMuscleGroupEntity

private const val DATA_BASE_NAME = "mystore_database"

@Database(
    entities = [
        MuscleSubGroupEntity::class,
        MuscleGroupEntity::class,
        TrainingEntity::class,
        MuscleGroupMuscleSubGroupEntity::class,
        TrainingMuscleGroupEntity::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
    abstract fun muscleGroupDao(): MuscleGroupDao
    abstract fun muscleSubGroupDao(): MuscleSubGroupDao
    abstract fun muscleGroupMuscleSubGroupDao(): MuscleGroupMuscleSubGroupDao
    abstract fun trainingMuscleGroupDao(): TrainingMuscleGroupDao

    companion object {

        private lateinit var db: AppDatabase

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) { /* Comportamento de migração */
            }
        }


        fun getInstance(context: Context): AppDatabase {
            if (Companion::db.isInitialized) return db

            db = Room.databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = DATA_BASE_NAME,
            )
                .addMigrations(MIGRATION_1_2)
                .build()

            return db
        }
    }
}