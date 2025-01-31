package com.example.myworkout.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myworkout.enums.Status
import com.example.myworkout.domain.room.entity.TrainingEntity

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
}
