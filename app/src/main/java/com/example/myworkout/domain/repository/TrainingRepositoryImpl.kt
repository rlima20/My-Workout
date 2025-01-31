package com.example.myworkout.domain.repository

import com.example.myworkout.domain.mapper.toEntity
import com.example.myworkout.domain.mapper.toModel
import com.example.myworkout.domain.mapper.toModelList
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.room.dao.MuscleGroupDao
import com.example.myworkout.domain.room.dao.MuscleGroupMuscleSubGroupDao
import com.example.myworkout.domain.room.dao.MuscleSubGroupDao
import com.example.myworkout.domain.room.dao.TrainingDao
import com.example.myworkout.domain.room.dao.TrainingMuscleGroupDao
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.TrainingMuscleGroupEntity
import com.example.myworkout.enums.Status

class TrainingRepositoryImpl(
    private val trainingDao: TrainingDao,
    private val muscleGroupDao: MuscleGroupDao,
    private val trainingMuscleGroupDao: TrainingMuscleGroupDao,
    private val muscleGroupMuscleSubGroupDao: MuscleGroupMuscleSubGroupDao,
    private val muscleSubGroupDao: MuscleSubGroupDao
) : TrainingRepository {

    override suspend fun getMuscleSubGroupsForTraining(trainingId: Int): List<MuscleSubGroupModel> {
        // Lista de subgrupos que vai ser retornada
        val muscleSubGroups = mutableListOf<MuscleSubGroupModel>()

        // Obter todos os MuscleGroups relacionados ao Training
        val muscleGroupRelations = trainingMuscleGroupDao.getMuscleGroupsForTraining(trainingId)

        // Coleta todos os MuscleSubGroups
        muscleGroupRelations.forEach { trainingMuscleGroup ->
            // Atribui valor
            val muscleSubGroupRelations = muscleGroupMuscleSubGroups(trainingMuscleGroup)

            // Passa pela lista coletando os items
            muscleSubGroupRelations.forEach { subGroupRelation ->

                // Obtem a lista de muscleSUbGroup a partir do id da relação
                val muscleSubGroup = getMuscleSubGroup(subGroupRelation)?.toModel()

                muscleSubGroup?.let {
                    muscleSubGroups.add(it)
                }
            }
        }
        return muscleSubGroups
    }

    private fun getMuscleSubGroup(groupRelation: MuscleGroupMuscleSubGroupEntity) =
        muscleSubGroupDao.getMuscleSubGroupById(groupRelation.muscleSubGroupId)

    private fun muscleGroupMuscleSubGroups(trainingMuscleGroup: TrainingMuscleGroupEntity): List<MuscleGroupMuscleSubGroupEntity> =
        muscleGroupMuscleSubGroupDao.getMuscleSubGroupsForMuscleGroup(trainingMuscleGroup.muscleGroupId)

    override fun insertTraining(training: TrainingModel) {
        trainingDao.insert(training.toEntity())
    }

    override fun insertMuscleGroup(muscleGroup: MuscleGroupModel) {
        muscleGroupDao.insert(muscleGroup.toEntity())
    }

    override fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel) {
        muscleSubGroupDao.insert(muscleSubGroup.toEntity())
    }

    override fun insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupModel) {
        muscleGroupMuscleSubGroupDao.insert(muscleGroupMuscleSubGroup.toEntity())
    }

    override fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel) {
        trainingMuscleGroupDao.insert(trainingMuscleGroup.toEntity())
    }

    override suspend fun saveTraining(training: TrainingModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainings(): List<TrainingModel> {
        return trainingDao.getAllTrainings().toModelList()
    }

    override suspend fun clearStatus(trainingId: Int, status: Status) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTrainingStatus(trainingId: Int, status: Status) {
        TODO("Not yet implemented")
    }
}