package com.example.myworkout.domain.repository.musclegroup

import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel

interface MuscleGroupRepository {
    suspend fun getMuscleGroups(): List<MuscleGroupModel>
    suspend fun getMuscleSubGroupsForTraining(trainingId: Int): List<MuscleSubGroupModel>
    fun insertMuscleGroup(muscleGroup: MuscleGroupModel)
    fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel)
    fun insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupModel)
    fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel)
}
