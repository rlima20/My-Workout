package com.example.myworkout.domain.usecase.musclegroup

import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.repository.musclegroup.MuscleGroupRepository

class MuscleGroupUseCaseImpl(private val repository: MuscleGroupRepository) :
    MuscleGroupUseCase {
    override suspend fun getMuscleSubGroupsForTraining(trainingId: Int): List<MuscleSubGroupModel> {
        return repository.getMuscleSubGroupsForTraining(trainingId)
    }

    override suspend fun getMuscleSubGroupsByMuscleGroups(): List<MuscleSubGroupModel> {
        return repository.getSubGroupsGroupedByMuscleGroups().flatMap { it.value }
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
}