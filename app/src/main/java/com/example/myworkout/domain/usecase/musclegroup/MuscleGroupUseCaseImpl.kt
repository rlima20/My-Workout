package com.example.myworkout.domain.usecase.musclegroup

import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.repository.musclegroup.MuscleGroupRepository
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.enums.BodyPart

class MuscleGroupUseCaseImpl(
    private val repository: MuscleGroupRepository

) :
    MuscleGroupUseCase {
    override suspend fun getMuscleSubGroupsByTrainingId(trainingId: Int): List<MuscleSubGroupModel> {
        return repository.getMuscleSubGroupsByTrainingId(trainingId)
    }

    override suspend fun getMuscleGroupsWithRelations(): List<MuscleGroupModel> {
        val muscleGroups = repository.getMuscleGroups()
        val relations = repository.getAllRelations().map { it.muscleGroupId }.toSet()
        return muscleGroups.filter { it.muscleGroupId in relations }
    }

    override suspend fun insertMuscleGroup(name: String, image: BodyPart): MuscleGroupModel {
        val newGroup = MuscleGroupModel(
            muscleGroupId = repository.getMuscleGroups().size + 1,
            name = name,
            image = image
        )
        repository.insertMuscleGroup(newGroup)
        return newGroup
    }

    override suspend fun deleteGroup(group: MuscleGroupModel) {
        repository.deleteGroup(group)
    }

    override suspend fun clearSelectedMuscleSubGroups(subGroups: List<MuscleSubGroupModel>) {
        val updatedSubGroups = subGroups.map { it.copy(selected = false) }
        updatedSubGroups.forEach { updateSubGroup(it) }
    }

    override suspend fun replaceRelationsForGroup(
        muscleGroupId: Int,
        newRelations: List<MuscleGroupMuscleSubGroupModel>
    ) {
        repository.replaceRelationsForGroup(muscleGroupId, newRelations)
    }

    override suspend fun getSubGroupsGroupedByMuscleGroups(): Map<MuscleGroupModel, List<MuscleSubGroupModel>> {
        return repository.getSubGroupsGroupedByMuscleGroups()
    }

    override suspend fun getSubgroupsById(id: Int): List<MuscleSubGroupModel> {
        return repository.getSubGroupsById(id)
    }

    override suspend fun getSubgroupById(id: Int): MuscleSubGroupModel {
        return repository.getSubGroupById(id)
    }

    override suspend fun getSubGroupIdFromRelation(id: Int): List<Int> {
        return repository.getSubGroupIdFromRelation(id)
    }

    override suspend fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel) {
        repository.insertMuscleSubGroup(muscleSubGroup)
    }

    override suspend fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel) {
        repository.insertTrainingMuscleGroup(trainingMuscleGroup)
    }

    override suspend fun insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupModel) {
        repository.insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup)
    }

    override suspend fun getMuscleGroups(): List<MuscleGroupModel> {
        return repository.getMuscleGroups()
    }

    override suspend fun getMuscleSubGroups(): List<MuscleSubGroupModel> {
        return repository.getMuscleSubGroups()
    }

    override suspend fun updateSubGroup(subGroup: MuscleSubGroupModel) {
        repository.updateSubGroup(subGroup)
    }

    override suspend fun updateGroup(group: MuscleGroupModel) {
        repository.updateGroup(group)
    }

    override suspend fun getRelationById(muscleGroupId: Int): List<MuscleGroupMuscleSubGroupEntity> {
        return repository.getRelationById(muscleGroupId)
    }

    override suspend fun getAllRelations(): List<MuscleGroupMuscleSubGroupModel> {
        return repository.getAllRelations()
    }
}