package com.example.myworkout.domain.usecase.training

import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.repository.training.TrainingRepository
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status

class TrainingUseCaseImpl(private val repository: TrainingRepository) :
    TrainingUseCase {

    override suspend fun insertTraining(training: TrainingModel) {
        repository.insertTraining(training)
    }

    override suspend fun updateTraining(training: TrainingModel) {
        repository.updateTraining(training)
    }

    override suspend fun deleteTraining(training: TrainingModel) {
        repository.deleteTraining(training)
    }

    override suspend fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel) {
        repository.insertTrainingMuscleGroup(trainingMuscleGroup)
    }

    override suspend fun deleteTrainingMuscleGroup(trainingId: Int) {
        repository.deleteTrainingMuscleGroup(trainingId)
    }

    override suspend fun getTrainings(): List<TrainingModel> {
        return repository.getTrainings()
    }

    override suspend fun clearStatus(trainingId: Int, status: Status) {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainingDays(): List<DayOfWeek> {
        return repository.getTrainings()
            .map { it.dayOfWeek }
            .distinct()
            .sortedBy { it.ordinal }
    }

    override suspend fun getTrainingDaysStatus(): List<Pair<DayOfWeek, Boolean>> {
        val trainingDays = repository.getTrainings().map { it.dayOfWeek }

        return DayOfWeek.values().map { day ->
            Pair(day, trainingDays.contains(day))
        }
    }
}