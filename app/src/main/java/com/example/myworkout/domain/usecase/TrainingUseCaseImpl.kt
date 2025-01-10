package com.example.myworkout.domain.usecase

import com.example.myworkout.data.model.Status
import com.example.myworkout.data.model.Training
import com.example.myworkout.data.repository.TrainingRepository

interface TrainingUseCase {
    suspend fun saveTraining(training: Training)
    suspend fun getTrainings(): List<Training>
    suspend fun clearStatus(trainingId: Int, status: Status)
}

class TrainingUseCaseImpl(private val repository: TrainingRepository) :
    TrainingUseCase {
    override suspend fun saveTraining(training: Training) {
        repository.getTrainings()
    }

    override suspend fun getTrainings(): List<Training> {
        return repository.getTrainings()
    }

    override suspend fun clearStatus(trainingId: Int, status: Status) {
        repository.updateTrainingStatus(trainingId, status)
    }
}