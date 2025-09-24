package com.example.myworkout.domain.usecase.training

import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.enums.Status

interface TrainingUseCase {
    suspend fun getTrainings(): List<TrainingModel>
    suspend fun insertTraining(training: TrainingModel)
    suspend fun updateTraining(training: TrainingModel)
    suspend fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel)
    suspend fun clearStatus(trainingId: Int, status: Status)
}