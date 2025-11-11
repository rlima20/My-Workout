package com.example.myworkout.domain.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myworkout.domain.room.entity.TrainingEntity
import com.example.myworkout.enums.Status

@Dao
interface TrainingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(training: TrainingEntity)

    @Query("SELECT * FROM training")
    fun getAllTrainings(): List<TrainingEntity>

    @Query("SELECT * FROM training WHERE trainingId = :id")
    fun getTrainingById(id: Int): TrainingEntity?

    @Query("DELETE FROM training")
    fun clearAllStatuses()

    @Query("UPDATE training SET status = :status WHERE trainingId = :trainingId")
    fun updateTrainingStatus(trainingId: Int, status: Status)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTraining(training: TrainingEntity)

    @Delete
    fun deleteTraining(training: TrainingEntity)
}
