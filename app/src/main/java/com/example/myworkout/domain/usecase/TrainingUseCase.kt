package com.example.myworkout.domain.usecase

import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.enums.Status

interface TrainingUseCase {
    suspend fun getMuscleSubGroupsForTraining(trainingId: Int): List<MuscleSubGroupModel>
    suspend fun insertTraining(training: TrainingModel)
    suspend fun insertMuscleGroup(muscleGroup: MuscleGroupModel)
    suspend fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel)
    suspend fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel)
    suspend fun insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupModel)
    suspend fun getTrainings(): List<TrainingModel>
    suspend fun clearStatus(trainingId: Int, status: Status)
}