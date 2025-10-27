package com.example.myworkout.domain.usecase.musclegroup

import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.enums.BodyPart

interface MuscleGroupUseCase {
    suspend fun getMuscleSubGroupsByTrainingId(trainingId: Int): List<MuscleSubGroupModel>
    suspend fun getSubGroupsGroupedByMuscleGroups(): Map<MuscleGroupModel, List<MuscleSubGroupModel>>
    suspend fun getSubgroupsById(id: Int): List<MuscleSubGroupModel>
    suspend fun getSubgroupById(id: Int): MuscleSubGroupModel
    suspend fun getSubGroupIdFromRelation(id: Int): List<Int>
    suspend fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel)
    suspend fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel)
    suspend fun insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupModel)
    suspend fun getMuscleGroups(): List<MuscleGroupModel>
    suspend fun getMuscleSubGroups(): List<MuscleSubGroupModel>
    suspend fun updateSubGroup(subGroup: MuscleSubGroupModel)
    suspend fun getRelationById(muscleGroupId: Int): List<MuscleGroupMuscleSubGroupEntity>
    suspend fun getAllRelations(): List<MuscleGroupMuscleSubGroupModel>
    suspend fun getMuscleGroupsWithRelations(): List<MuscleGroupModel>
    suspend fun insertMuscleGroup(name: String, image: BodyPart): MuscleGroupModel
    suspend fun clearSelectedMuscleSubGroups(subGroups: List<MuscleSubGroupModel>)
    suspend fun replaceRelationsForGroup(
        muscleGroupId: Int,
        newRelations: List<MuscleGroupMuscleSubGroupModel>
    )
}