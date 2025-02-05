package com.example.myworkout.domain.repository

import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.enums.Status

interface TrainingRepository {
    suspend fun getMuscleSubGroupsForTraining(trainingId: Int): List<MuscleSubGroupModel>
    fun insertTraining(training: TrainingModel)
    fun insertMuscleGroup(muscleGroup: MuscleGroupModel)
    fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel)
    fun insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupModel)
    fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel)
    suspend fun saveTraining(training: TrainingModel)
    suspend fun getTrainings(): List<TrainingModel>
    suspend fun clearStatus(trainingId: Int, status: Status)
    suspend fun updateTrainingStatus(trainingId: Int, status: Status)
}
