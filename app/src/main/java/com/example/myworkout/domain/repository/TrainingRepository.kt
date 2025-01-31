package com.example.myworkout.domain.repository

import com.example.myworkout.enums.Status
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.room.entity.MuscleGroupEntity
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.MuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.TrainingEntity
import com.example.myworkout.domain.room.entity.TrainingMuscleGroupEntity

interface TrainingRepository {
    suspend fun getMuscleSubGroupsForTraining(trainingId: Int): List<MuscleSubGroupModel>
    fun insertTraining(training: TrainingEntity)
    fun insertMuscleGroup(muscleGroup: MuscleGroupEntity)
    fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupEntity)
    fun insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupEntity)
    fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupEntity)
    suspend fun saveTraining(training: TrainingModel)
    suspend fun getTrainings(): List<TrainingModel>
    suspend fun clearStatus(trainingId: Int, status: Status)
    suspend fun updateTrainingStatus(trainingId: Int, status: Status)
}
