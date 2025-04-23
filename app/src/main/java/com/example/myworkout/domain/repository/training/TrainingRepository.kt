package com.example.myworkout.domain.repository.training

import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.enums.Status

interface TrainingRepository {
    fun insertTraining(training: TrainingModel)
    fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel)
    suspend fun getTrainings(): List<TrainingModel>
    suspend fun clearTrainingStatus(trainingId: Int, status: Status)
    suspend fun updateTrainingStatus(trainingId: Int, status: Status)
}
