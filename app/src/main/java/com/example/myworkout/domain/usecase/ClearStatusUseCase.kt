package com.example.myworkout.domain.usecase

import com.example.myworkout.data.model.Status

interface ClearStatusUseCase {
    suspend fun clearStatus(trainingId: Int, status: Status)
}