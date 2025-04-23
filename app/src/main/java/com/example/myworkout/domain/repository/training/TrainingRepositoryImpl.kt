package com.example.myworkout.domain.repository.training

import com.example.myworkout.domain.mapper.toEntity
import com.example.myworkout.domain.mapper.toModelTrainingList
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.room.dao.TrainingDao
import com.example.myworkout.domain.room.dao.TrainingMuscleGroupDao
import com.example.myworkout.enums.Status

class TrainingRepositoryImpl(
    private val trainingDao: TrainingDao,
    private val trainingMuscleGroupDao: TrainingMuscleGroupDao,
) : TrainingRepository {

    override suspend fun getTrainings(): List<TrainingModel> {
        return trainingDao.getAllTrainings().toModelTrainingList()
    }

    override fun insertTraining(training: TrainingModel) {
        trainingDao.insert(training.toEntity())
    }

    override fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel) {
        trainingMuscleGroupDao.insert(trainingMuscleGroup.toEntity())
    }

    override suspend fun clearTrainingStatus(trainingId: Int, status: Status) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTrainingStatus(trainingId: Int, status: Status) {
        TODO("Not yet implemented")
    }
}