package com.example.myworkout.domain.usecase.musclegroup

import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.repository.musclegroup.MuscleGroupRepository

class MuscleGroupUseCaseImpl(private val repository: MuscleGroupRepository) :
    MuscleGroupUseCase {
    override suspend fun getMuscleSubGroupsByTrainingId(trainingId: Int): List<MuscleSubGroupModel> {
        return repository.getMuscleSubGroupsByTrainingId(trainingId)
    }

    override suspend fun getSubGroupsGroupedByMuscleGroups(): Map<MuscleGroupModel, List<MuscleSubGroupModel>> {
        return repository.getSubGroupsGroupedByMuscleGroups()
    }

    override suspend fun insertMuscleGroup(muscleGroup: MuscleGroupModel) {
        repository.insertMuscleGroup(muscleGroup)
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
}