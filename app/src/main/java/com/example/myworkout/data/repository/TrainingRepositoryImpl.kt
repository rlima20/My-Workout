package com.example.myworkout.data.repository

import com.example.myworkout.data.database.TrainingDao
import com.example.myworkout.data.model.Status
import com.example.myworkout.data.model.Training

class TrainingRepositoryImpl(private val trainingDao: TrainingDao) : TrainingRepository {
    override suspend fun addTraining(training: Training) {
        trainingDao.insertTraining(training)
    }

    override suspend fun getTrainings(): List<Training> {
        return trainingDao.getAllTrainings()
    }

    override suspend fun updateTrainingStatus(trainingId: Int, status: Status) {
        trainingDao.updateTrainingStatus(trainingId, status)
    }
}