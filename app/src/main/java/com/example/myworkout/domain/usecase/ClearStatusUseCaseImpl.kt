package com.example.myworkout.domain.usecase

import com.example.myworkout.data.model.Status
import com.example.myworkout.data.repository.TrainingRepository

class ClearStatusUseCaseImpl(private val repository: TrainingRepository) : ClearStatusUseCase {
    override suspend fun clearStatus(trainingId: Int, status: Status) {
        repository.updateTrainingStatus(trainingId, status)
    }
}