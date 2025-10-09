package com.example.myworkout.domain.repository.musclegroup

import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.MuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.TrainingMuscleGroupEntity

interface MuscleGroupRepository {

    //Muscle Group
    suspend fun getMuscleGroups(): List<MuscleGroupModel>
    fun insertMuscleGroup(muscleGroup: MuscleGroupModel)

    // SubGroups
    suspend fun getMuscleSubGroups(): List<MuscleSubGroupModel>
    suspend fun getMuscleSubGroupsByTrainingId(trainingId: Int): List<MuscleSubGroupModel>
    suspend fun getSubGroupsGroupedByMuscleGroups(): Map<MuscleGroupModel, List<MuscleSubGroupModel>>
    suspend fun getSubGroupsById(id: Int): List<MuscleSubGroupModel>
    suspend fun getSubGroupById(id: Int): MuscleSubGroupModel
    suspend fun getSubGroupIdFromRelation(id: Int): List<Int>
    suspend fun getMuscleSubGroupsByMuscleGroups(listOfMuscleGroups: List<MuscleSubGroupModel>): List<MuscleSubGroupModel>
    fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel)
    fun updateSubGroup(subGroup: MuscleSubGroupModel)

    // Group and Subgroup relation
    suspend fun getRelationById(muscleGroupId: Int): List<MuscleGroupMuscleSubGroupEntity>
    suspend fun getSubgroupById(groupRelation: MuscleGroupMuscleSubGroupEntity): MuscleSubGroupEntity?
    fun insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupModel)

    // Training group relation
    suspend fun getRelationByTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupEntity): List<MuscleGroupMuscleSubGroupEntity>
    suspend fun getAllRelations(): List<MuscleGroupMuscleSubGroupModel>
    fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel)
}
