package com.example.myworkout.domain.usecase.training

import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status

interface TrainingUseCase {
    suspend fun getTrainingDays(): List<DayOfWeek>
    suspend fun getTrainingDaysStatus(): List<Pair<DayOfWeek, Boolean>>
    suspend fun getTrainings(): List<TrainingModel>
    suspend fun insertTraining(training: TrainingModel)
    suspend fun updateTraining(training: TrainingModel)
    suspend fun deleteTraining(training: TrainingModel)
    suspend fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel)
    suspend fun deleteTrainingMuscleGroup(trainingId: Int)
    suspend fun clearStatus(trainingId: Int, status: Status)
}