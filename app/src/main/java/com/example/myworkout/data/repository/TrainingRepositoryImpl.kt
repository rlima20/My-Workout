package com.example.myworkout.data.repository

import com.example.myworkout.data.database.TrainingDao
import com.example.myworkout.data.model.Status
import com.example.myworkout.data.model.Training

class TrainingRepositoryImpl(private val trainingDao: TrainingDao) : TrainingRepository {
    override suspend fun addTraining(training: Training) {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainings(): List<Training> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTrainingStatus(trainingId: Int, status: Status) {
        TODO("Not yet implemented")
    }
}