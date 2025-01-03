package com.example.myworkout.domain.usecase

import com.example.myworkout.data.model.Training

interface SaveTrainingUseCase {
    suspend fun saveTraining(training: Training)
}