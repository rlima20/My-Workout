package com.example.myworkout.domain.usecase

import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.enums.Status
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.repository.TrainingRepository
import com.example.myworkout.domain.room.entity.MuscleGroupEntity
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.MuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.TrainingEntity
import com.example.myworkout.domain.room.entity.TrainingMuscleGroupEntity

class TrainingUseCaseImpl(private val repository: TrainingRepository) :
    TrainingUseCase {
    override suspend fun getMuscleSubGroupsForTraining(trainingId: Int): List<MuscleSubGroupModel> {
        return repository.getMuscleSubGroupsForTraining(trainingId)
    }

    override suspend fun insertTraining(training: TrainingModel) {
        repository.insertTraining(training)
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

    override suspend fun getTrainings(): List<TrainingModel> {
        return repository.getTrainings()
    }

    override suspend fun clearStatus(trainingId: Int, status: Status) {
        repository.updateTrainingStatus(trainingId, status)
    }
}