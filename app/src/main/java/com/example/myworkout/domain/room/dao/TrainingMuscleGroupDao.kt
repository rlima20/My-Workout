package com.example.myworkout.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myworkout.domain.room.entity.MuscleGroupEntity
import com.example.myworkout.domain.room.entity.TrainingMuscleGroupEntity

@Dao
interface TrainingMuscleGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trainingMuscleGroup: TrainingMuscleGroupEntity)

    @Query("SELECT * FROM training_muscle_group")
    fun getAllTrainingMuscleGroups(): List<TrainingMuscleGroupEntity>

    @Query("SELECT * FROM training_muscle_group WHERE trainingId = :trainingId")
    fun getMuscleGroupsForTraining(trainingId: Int): List<TrainingMuscleGroupEntity>

    @Query("SELECT * FROM training_muscle_group")
    fun getAllMuscleGroupRelations(): List<TrainingMuscleGroupEntity>
}