package com.example.myworkout.data.repository

import com.example.myworkout.data.model.Status
import com.example.myworkout.data.model.Training

interface TrainingRepository {
    suspend fun addTraining(training: Training)
    suspend fun getTrainings(): List<Training>
    suspend fun updateTrainingStatus(trainingId: Int, status: Status)
}