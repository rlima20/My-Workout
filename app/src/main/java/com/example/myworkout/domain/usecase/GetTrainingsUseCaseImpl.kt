package com.example.myworkout.domain.usecase

import com.example.myworkout.data.model.Training
import com.example.myworkout.data.repository.TrainingRepository

class GetTrainingsUseCaseImpl(private val repository: TrainingRepository) : GetTrainingsUseCase {
    override suspend fun getTrainings(): List<Training> {
        return repository.getTrainings()
    }
}