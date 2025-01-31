package com.example.myworkout.domain.usecase

import com.example.myworkout.enums.Status
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.room.entity.TrainingEntity

interface TrainingUseCase {
    suspend fun getMuscleSubGroupsForTraining(trainingId: Int): List<MuscleSubGroupModel>
    suspend fun insertTraining(trainingEntity: TrainingEntity)
    suspend fun getTrainings(): List<TrainingModel>
    suspend fun clearStatus(trainingId: Int, status: Status)
}