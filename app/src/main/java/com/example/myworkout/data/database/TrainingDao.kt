package com.example.myworkout.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myworkout.data.model.Status
import com.example.myworkout.data.model.Training

@Dao
interface TrainingDao {
    @Query("SELECT * FROM training")
    fun getAllTrainings(): List<Training>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTraining(training: Training)

    @Query("UPDATE training SET status = :status WHERE id = :trainingId")
    fun updateTrainingStatus(trainingId: Int, status: Status)

    @Query("DELETE FROM training")
    fun clearTrainingStatus()
}