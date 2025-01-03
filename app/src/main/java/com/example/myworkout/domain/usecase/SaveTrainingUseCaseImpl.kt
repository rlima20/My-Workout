package com.example.myworkout.domain.usecase

import com.example.myworkout.data.model.Training
import com.example.myworkout.data.repository.TrainingRepository

class SaveTrainingUseCaseImpl(private val repository: TrainingRepository) : SaveTrainingUseCase {
    override suspend fun saveTraining(training: Training) {
        repository.getTrainings()
    }
}