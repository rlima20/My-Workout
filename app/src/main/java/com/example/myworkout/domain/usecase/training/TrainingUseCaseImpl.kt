package com.example.myworkout.domain.usecase.training

import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.repository.training.TrainingRepository
import com.example.myworkout.enums.Status

class TrainingUseCaseImpl(private val repository: TrainingRepository) :
    TrainingUseCase {

    override suspend fun insertTraining(training: TrainingModel) {
        repository.insertTraining(training)
    }

    override suspend fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel) {
        repository.insertTrainingMuscleGroup(trainingMuscleGroup)
    }

    override suspend fun getTrainings(): List<TrainingModel> {
        return repository.getTrainings()
    }

    override suspend fun clearStatus(trainingId: Int, status: Status) {
        TODO("Not yet implemented")
    }
}