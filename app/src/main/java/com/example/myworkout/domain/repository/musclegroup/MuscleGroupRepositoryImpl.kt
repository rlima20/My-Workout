package com.example.myworkout.domain.repository.musclegroup

import com.example.myworkout.domain.mapper.toEntity
import com.example.myworkout.domain.mapper.toListModel
import com.example.myworkout.domain.mapper.toModel
import com.example.myworkout.domain.mapper.toModelMuscleGroupList
import com.example.myworkout.domain.mapper.toModelMuscleSubGroupList
import com.example.myworkout.domain.mapper.toMuscleGroupMuscleSubGroupModel
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

    override suspend fun getMuscleSubGroupsByTrainingId(trainingId: Int): List<MuscleSubGroupModel> {
        // Lista de subgrupos que vai ser retornada
        val muscleSubGroups = mutableListOf<MuscleSubGroupModel>()

        // Obter todos os MuscleGroups relacionados ao Training
        val muscleGroupRelations = trainingMuscleGroupDao.getMuscleGroupsForTraining(trainingId)

        // Coleta todos os MuscleSubGroups
        muscleGroupRelations.forEach { trainingMuscleGroup ->
            // Atribui valor
            val muscleSubGroupRelations = getRelationByTrainingMuscleGroup(trainingMuscleGroup)

            // Passa pela lista coletando os items
            muscleSubGroupRelations.forEach { subGroupRelation ->

                // Obtem a lista de muscleSUbGroup a partir do id da relação
                val muscleSubGroup = getSubgroupById(subGroupRelation)?.toModel()

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
            val muscleSubGroupRelations = getRelationByTrainingMuscleGroup(trainingMuscleGroup)

            // Passa pela lista coletando os items
            muscleSubGroupRelations.forEach { subGroupRelation ->

                // Obtem a lista de muscleSUbGroup a partir do id da relação
                val muscleSubGroup = getSubgroupById(subGroupRelation)?.toModel()

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
            val muscleSubGroupsForGroup = getRelationById(muscleGroup.muscleGroupId) // Obter os subgrupos relacionados a cada grupo muscular
            groupedSubGroups[muscleGroup] = muscleSubGroupsForGroup.mapNotNull { // Adicionar os subgrupos ao mapa
                    getSubgroupById(it)?.toModel()
                }.toMutableList()
        }

        return groupedSubGroups
    }

    override suspend fun getSubGroupsById(id: Int): List<MuscleSubGroupModel> {
        return muscleSubGroupDao.getSubgroupsById(id)?.toListModel() ?: emptyList()
    }

    override suspend fun getSubGroupById(id: Int): MuscleSubGroupModel {
        return muscleSubGroupDao.getSubgroupById(id)?.toModel() ?: MuscleSubGroupModel(name = "")
    }

    override suspend fun getSubGroupIdFromRelation(id: Int): List<Int> {
        return muscleGroupMuscleSubGroupDao.getSubGroupsIdFromRelation(id)
    }

    override suspend fun getRelationById(muscleGroupId: Int): List<MuscleGroupMuscleSubGroupEntity> =
        muscleGroupMuscleSubGroupDao.getRelationById(muscleGroupId)

    override suspend fun getSubgroupById(groupRelation: MuscleGroupMuscleSubGroupEntity) =
        muscleSubGroupDao.getSubgroupById(groupRelation.muscleSubGroupId)

    override suspend fun getRelationByTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupEntity): List<MuscleGroupMuscleSubGroupEntity> =
        muscleGroupMuscleSubGroupDao.getRelationById(trainingMuscleGroup.muscleGroupId)

    override fun insertMuscleGroup(muscleGroup: MuscleGroupModel) {
        muscleGroupDao.insert(muscleGroup.toEntity())
    }

    override suspend fun getAllRelations(): List<MuscleGroupMuscleSubGroupModel> {
        return muscleGroupMuscleSubGroupDao.getAllMuscleGroupMuscleSubGroups().toMuscleGroupMuscleSubGroupModel()
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

    override suspend fun getMuscleSubGroups(): List<MuscleSubGroupModel> {
        return muscleSubGroupDao.getAllMuscleSubGroups().toModelMuscleSubGroupList()
    }

    override fun updateSubGroup(subGroup: MuscleSubGroupModel) {
        muscleSubGroupDao.updateSubGroup(subGroup.toEntity())
    }
}