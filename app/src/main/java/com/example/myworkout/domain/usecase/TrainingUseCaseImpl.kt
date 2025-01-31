package com.example.myworkout.domain.usecase

import com.example.myworkout.enums.Status
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.repository.TrainingRepository
import com.example.myworkout.domain.room.entity.TrainingEntity

class TrainingUseCaseImpl(private val repository: TrainingRepository) :
    TrainingUseCase {
    override suspend fun getMuscleSubGroupsForTraining(trainingId: Int): List<MuscleSubGroupModel> {
        return repository.getMuscleSubGroupsForTraining(trainingId)
    }

    override suspend fun insertTraining(trainingEntity: TrainingEntity) {
        repository.insertTraining(trainingEntity)
    }

    override suspend fun getTrainings(): List<TrainingModel> {
        return repository.getTrainings()
    }

    override suspend fun clearStatus(trainingId: Int, status: Status) {
        repository.updateTrainingStatus(trainingId, status)
    }
}