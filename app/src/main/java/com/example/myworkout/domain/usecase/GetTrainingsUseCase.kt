package com.example.myworkout.domain.usecase

import com.example.myworkout.data.model.Training

interface GetTrainingsUseCase {
    suspend fun getTrainings(): List<Training>
}