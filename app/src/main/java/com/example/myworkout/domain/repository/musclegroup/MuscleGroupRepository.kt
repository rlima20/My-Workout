package com.example.myworkout.domain.repository.musclegroup

import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.MuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.TrainingMuscleGroupEntity

// Todo - Substituir os Entity por models
interface MuscleGroupRepository {
    suspend fun getMuscleGroups(): List<MuscleGroupModel>
    suspend fun getMuscleSubGroups(): List<MuscleSubGroupModel>
    suspend fun getMuscleSubGroupsByTrainingId(trainingId: Int): List<MuscleSubGroupModel>
    suspend fun getSubGroupsGroupedByMuscleGroups(): Map<MuscleGroupModel, List<MuscleSubGroupModel>>
    suspend fun getMuscleSubGroupsByMuscleGroups(listOfMuscleGroups: List<MuscleSubGroupModel>): List<MuscleSubGroupModel>
    suspend fun getRelationById(muscleGroupId: Int): List<MuscleGroupMuscleSubGroupEntity>
    suspend fun getMuscleSubGroup(groupRelation: MuscleGroupMuscleSubGroupEntity): MuscleSubGroupEntity?
    suspend fun getRelationByTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupEntity): List<MuscleGroupMuscleSubGroupEntity>
    suspend fun getAllRelations(): List<MuscleGroupMuscleSubGroupModel>
    fun insertMuscleGroup(muscleGroup: MuscleGroupModel)
    fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel)
    fun insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupModel)
    fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel)
    fun updateSubGroup(subGroup: MuscleSubGroupModel)
}
