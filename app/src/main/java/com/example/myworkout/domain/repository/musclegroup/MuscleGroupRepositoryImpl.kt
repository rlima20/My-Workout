package com.example.myworkout.domain.repository.musclegroup

import com.example.myworkout.domain.mapper.toEntity
import com.example.myworkout.domain.mapper.toModel
import com.example.myworkout.domain.mapper.toModelMuscleGroupList
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.room.dao.MuscleGroupDao
import com.example.myworkout.domain.room.dao.MuscleGroupMuscleSubGroupDao
import com.example.myworkout.domain.room.dao.MuscleSubGroupDao
import com.example.myworkout.domain.room.dao.TrainingMuscleGroupDao
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.TrainingMuscleGroupEntity

class MuscleGroupRepositoryImpl(
    private val muscleGroupDao: MuscleGroupDao,
    private val trainingMuscleGroupDao: TrainingMuscleGroupDao,
    private val muscleGroupMuscleSubGroupDao: MuscleGroupMuscleSubGroupDao,
    private val muscleSubGroupDao: MuscleSubGroupDao
) : MuscleGroupRepository {

    override suspend fun getMuscleSubGroupsForTraining(trainingId: Int): List<MuscleSubGroupModel> {
        // Lista de subgrupos que vai ser retornada
        val muscleSubGroups = mutableListOf<MuscleSubGroupModel>()

        // Obter todos os MuscleGroups relacionados ao Training
        val muscleGroupRelations = trainingMuscleGroupDao.getMuscleGroupsForTraining(trainingId)

        // Coleta todos os MuscleSubGroups
        muscleGroupRelations.forEach { trainingMuscleGroup ->
            // Atribui valor
            val muscleSubGroupRelations = getMuscleGroupMuscleSubGroups(trainingMuscleGroup)

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

    override suspend fun getMuscleSubGroupsByMuscleGroups(
        listOfMuscleGroups: List<MuscleSubGroupModel>
    ): List<MuscleSubGroupModel> {
        // Lista de subgrupos que vai ser retornada
        val muscleSubGroups = mutableListOf<MuscleSubGroupModel>()


        // Obter todos os MuscleGroups
        val muscleGroupRelations = trainingMuscleGroupDao.getAllMuscleGroupRelations()

        // Coleta todos os MuscleSubGroups
        muscleGroupRelations.forEach { trainingMuscleGroup ->
            // Atribui valor
            val muscleSubGroupRelations = getMuscleGroupMuscleSubGroups(trainingMuscleGroup)

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

    override suspend fun getSubGroupsGroupedByMuscleGroups(): Map<MuscleGroupModel, List<MuscleSubGroupModel>> {
        // Mapa que vai armazenar os grupos musculares e suas respectivas listas de subgrupos
        val groupedSubGroups = mutableMapOf<MuscleGroupModel, MutableList<MuscleSubGroupModel>>()

        // Obter todos os grupos musculares
        val muscleGroups = muscleGroupDao.getAllMuscleGroups().toModelMuscleGroupList()

        // Iterar sobre cada grupo muscular
        muscleGroups.forEach { muscleGroup ->
            val muscleSubGroupsForGroup = getMuscleGroupMuscleSubGroups(muscleGroup) // Obter os subgrupos relacionados a cada grupo muscular
            groupedSubGroups[muscleGroup] = muscleSubGroupsForGroup.mapNotNull { // Adicionar os subgrupos ao mapa
                    getMuscleSubGroup(it)?.toModel()
                }.toMutableList()
        }

        return groupedSubGroups
    }

    private fun getMuscleGroupMuscleSubGroups(muscleGroup: MuscleGroupModel): List<MuscleGroupMuscleSubGroupEntity> =
        muscleGroupMuscleSubGroupDao.getMuscleSubGroupsForMuscleGroup(muscleGroup.muscleGroupId)

    private fun getMuscleSubGroup(groupRelation: MuscleGroupMuscleSubGroupEntity) =
        muscleSubGroupDao.getMuscleSubGroupById(groupRelation.muscleSubGroupId)

    private fun getMuscleGroupMuscleSubGroups(trainingMuscleGroup: TrainingMuscleGroupEntity): List<MuscleGroupMuscleSubGroupEntity> =
        muscleGroupMuscleSubGroupDao.getMuscleSubGroupsForMuscleGroup(trainingMuscleGroup.muscleGroupId)

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

    override suspend fun getMuscleGroups(): List<MuscleGroupModel> {
        return muscleGroupDao.getAllMuscleGroups().toModelMuscleGroupList()
    }
}