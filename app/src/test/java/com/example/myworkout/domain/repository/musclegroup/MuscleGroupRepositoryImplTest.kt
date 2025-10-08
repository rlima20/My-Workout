package com.example.myworkout.domain.repository.musclegroup

import android.annotation.SuppressLint
import com.example.myworkout.domain.mapper.toEntity
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
import com.example.myworkout.domain.room.entity.MuscleGroupEntity
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.MuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.TrainingEntity
import com.example.myworkout.domain.room.entity.TrainingMuscleGroupEntity
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status
import com.example.myworkout.presentation.viewmodel.BaseTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MuscleGroupRepositoryImplTest : BaseTest() {
    private lateinit var muscleGroupDao: MuscleGroupDao
    private lateinit var trainingMuscleGroupDao: TrainingMuscleGroupDao
    private lateinit var muscleGroupMuscleSubGroupDao: MuscleGroupMuscleSubGroupDao
    private lateinit var muscleSubGroupDao: MuscleSubGroupDao
    private lateinit var muscleGroupRepositoryImpl: MuscleGroupRepositoryImpl

    @Before
    fun setUp() {
        initMocks()
        initRepository()
    }

    @Test
    fun testGetMuscleSubGroupsByTrainingId() = runBlocking {
        // Given
        val expectedReturn = listOf(
            MuscleSubGroupModel(name = "name1"),
            MuscleSubGroupModel(name = "name2"),
            MuscleSubGroupModel(name = "name3")
        )

        val trainingId = 1
        val listOfTrainingMuscleGroupEntity = listOf(
            TrainingMuscleGroupEntity(trainingId, 2),
            TrainingMuscleGroupEntity(trainingId, 3)
        )

        val listOfMuscleGroupMuscleSubGroupEntity = listOf(
            MuscleGroupMuscleSubGroupEntity(2, 1),
            MuscleGroupMuscleSubGroupEntity(2, 2),
            MuscleGroupMuscleSubGroupEntity(2, 3)
        )

        val muscleSubGroupEntity1 = MuscleSubGroupEntity(name = "name1")
        val muscleSubGroupEntity2 = MuscleSubGroupEntity(name = "name2")
        val muscleSubGroupEntity3 = MuscleSubGroupEntity(name = "name3")

        // When
        every { trainingMuscleGroupDao.getMuscleGroupsForTraining(trainingId) } returns listOfTrainingMuscleGroupEntity
        every { muscleGroupMuscleSubGroupDao.getRelationById(2) } returns listOfMuscleGroupMuscleSubGroupEntity

        every { muscleSubGroupDao.getSubgroupById(1) } returns muscleSubGroupEntity1
        every { muscleSubGroupDao.getSubgroupById(2) } returns muscleSubGroupEntity2
        every { muscleSubGroupDao.getSubgroupById(3) } returns muscleSubGroupEntity3

        // Then
        val result = muscleGroupRepositoryImpl.getMuscleSubGroupsByTrainingId(trainingId)
        assertEquals(expectedReturn, result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetMuscleSubGroupsByMuscleGroups() = runTest {
        // Given
        val trainingEntity = TrainingEntity(
            1,
            Status.ACHIEVED,
            DayOfWeek.SATURDAY,
            "training"
        )

        val muscleGroupEntity = MuscleGroupEntity(
            1, "muscleGroup1", BodyPart.OTHER
        )

        val trainingMuscleGroupEntity = listOf(
            TrainingMuscleGroupEntity(
                trainingEntity.trainingId,
                muscleGroupEntity.muscleGroupId
            )
        )

        val muscleSubGroupEntity = MuscleSubGroupEntity(
            1, "subGroup", false
        )

        val muscleSubGroupModel = MuscleSubGroupModel(
            1, "subGroup", false
        )

        val relationEntity = listOf(
            MuscleGroupMuscleSubGroupEntity(
                muscleGroupEntity.muscleGroupId,
                muscleSubGroupEntity.muscleSubGroupId
            )
        )

        val listOfGroupsModel = listOf(muscleSubGroupModel)

        every { trainingMuscleGroupDao.getAllMuscleGroupRelations() } returns trainingMuscleGroupEntity


        every {
            muscleGroupMuscleSubGroupDao.getRelationById(muscleGroupEntity.muscleGroupId)
        } returns relationEntity

        every {
            muscleSubGroupDao.getSubgroupById(relationEntity.first().muscleSubGroupId)
        } returns muscleSubGroupEntity

        // When
        val result = muscleGroupRepositoryImpl.getMuscleSubGroupsByMuscleGroups(listOfGroupsModel)

        // Then
        assertEquals(listOfGroupsModel, result)
    }

    @Test
    fun testInsertMuscleGroupMuscleSubGroup() = runBlocking {
        // Given
        val muscleGroupMuscleSubGroup = MuscleGroupMuscleSubGroupModel(1, 1)

        // When
        muscleGroupRepositoryImpl.insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup)

        // Then
        verify { muscleGroupMuscleSubGroupDao.insert(muscleGroupMuscleSubGroup.toEntity()) }
    }

    @Test
    fun testInsertMuscleSubGroup() = runBlocking {
        // Given
        val muscleSubGroup = MuscleSubGroupModel(name = "name")

        // When
        muscleGroupRepositoryImpl.insertMuscleSubGroup(muscleSubGroup)

        // Then
        verify { muscleSubGroupDao.insert(muscleSubGroup.toEntity()) }
    }

    @Test
    fun testInsertMuscleGroup() = runBlocking {
        // Given
        val muscleGroup = MuscleGroupModel(
            muscleGroupId = 1,
            name = "name",
            image = BodyPart.OTHER
        )

        // When
        muscleGroupRepositoryImpl.insertMuscleGroup(muscleGroup)

        // Then
        verify { muscleGroupDao.insert(muscleGroup.toEntity()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testInsertTrainingMuscleGroup() = runBlocking {
        // Given
        val trainingMuscleGroup = TrainingMuscleGroupModel(1, 1)

        // When
        muscleGroupRepositoryImpl.insertTrainingMuscleGroup(trainingMuscleGroup)

        // Then
        verify { trainingMuscleGroupDao.insert(trainingMuscleGroup.toEntity()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testUpdateSubGroup() = runBlocking {
        // Given
        val subGroupToEdit = MuscleSubGroupModel(name = "name")

        // When
        muscleGroupRepositoryImpl.updateSubGroup(subGroupToEdit)

        // Then
        verify { muscleSubGroupDao.updateSubGroup(subGroupToEdit.toEntity()) }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetSubGroupsGroupedByMuscleGroups() = runBlocking {
        // Given
        val muscleGroupEntityList = listOf(
            MuscleGroupEntity(
                name = "name",
                image = BodyPart.OTHER
            )
        )

        val muscleGroupModel = MuscleGroupModel(
            muscleGroupId = 0,
            name = "name",
            image = BodyPart.OTHER
        )

        val returnExpected: Map<MuscleGroupModel, List<MuscleSubGroupModel>> = mapOf(
            Pair(
                muscleGroupModel,
                listOf()
            )
        )

        every { muscleGroupDao.getAllMuscleGroups() } returns muscleGroupEntityList

        // When
        val result = muscleGroupRepositoryImpl.getSubGroupsGroupedByMuscleGroups()

        // Then
        assertEquals(returnExpected, result)
    }

    @Test
    fun testGetRelationById() = runBlocking {
        // Given
        val returnExpected = MuscleSubGroupEntity(name = "name")

        every { muscleSubGroupDao.getSubgroupById(1) } returns returnExpected

        // When
        val result = muscleGroupRepositoryImpl.getSubgroupById(
            MuscleGroupMuscleSubGroupEntity(
                1, 1, false

            )
        )

        // Then
        assertEquals(returnExpected, result)
    }

    @Test
    fun testGetMuscleSubGroup() {
        // Given
        val returnExpected = MuscleSubGroupEntity(name = "name")

        every { muscleSubGroupDao.getSubgroupById(1) } returns returnExpected

        // When
        val result = muscleSubGroupDao.getSubgroupById(1)

        // Then
        assertEquals(returnExpected, result)
    }

    @Test
    fun testGetRelationByTrainingMuscleGroup() = runBlocking {
        // Given
        val muscleGroupId = 1
        val muscleSubGroupId = 1

        val returnExpected = listOf(
            MuscleGroupMuscleSubGroupEntity(muscleGroupId, muscleSubGroupId),
            MuscleGroupMuscleSubGroupEntity(muscleGroupId, muscleSubGroupId),
        )

        val trainingMuscleGroupEntity = TrainingMuscleGroupEntity(1, 1)

        every { muscleGroupMuscleSubGroupDao.getRelationById(1) } returns returnExpected

        // When
        val result =
            muscleGroupRepositoryImpl.getRelationByTrainingMuscleGroup(trainingMuscleGroupEntity)

        // Then
        assertEquals(returnExpected, result)
    }

    @Test
    fun testGetAllRelations() = runBlocking {
        // Given
        val returnExpected = listOf(
            MuscleGroupMuscleSubGroupEntity(1, 1),
            MuscleGroupMuscleSubGroupEntity(1, 1)
        )

        every { muscleGroupMuscleSubGroupDao.getAllMuscleGroupMuscleSubGroups() } returns returnExpected

        // When
        val result = muscleGroupRepositoryImpl.getAllRelations()

        // Then
        assertEquals(returnExpected.toMuscleGroupMuscleSubGroupModel(), result)
    }

    @Test
    fun testGetMuscleGroups() = runBlocking {
        // Given
        val returnExpected = listOf(
            MuscleGroupEntity(name = "name1", image = BodyPart.OTHER),
            MuscleGroupEntity(name = "name2", image = BodyPart.OTHER),
        )

        every { muscleGroupDao.getAllMuscleGroups() } returns returnExpected

        // When
        val result = muscleGroupRepositoryImpl.getMuscleGroups()

        // Then
        assertEquals(returnExpected.toModelMuscleGroupList(), result)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("CheckResult")
    @Test
    fun testGetMuscleSubGroups() = runTest {
        // Given
        val muscleSubGroupsExpected: List<MuscleSubGroupEntity> = listOf(
            MuscleSubGroupEntity(name = "name1"),
            MuscleSubGroupEntity(name = "name2")
        )

        every { muscleSubGroupDao.getAllMuscleSubGroups() } returns muscleSubGroupsExpected

        // When
        val result = muscleGroupRepositoryImpl.getMuscleSubGroups()

        // Then
        assertEquals(muscleSubGroupsExpected.toModelMuscleSubGroupList(), result)
    }

    private fun initRepository() {
        muscleGroupRepositoryImpl = MuscleGroupRepositoryImpl(
            muscleGroupDao = muscleGroupDao,
            trainingMuscleGroupDao = trainingMuscleGroupDao,
            muscleGroupMuscleSubGroupDao = muscleGroupMuscleSubGroupDao,
            muscleSubGroupDao = muscleSubGroupDao
        )
    }

    private fun initMocks() {
        muscleGroupDao = mockk(relaxed = true)
        trainingMuscleGroupDao = mockk(relaxed = true)
        muscleGroupMuscleSubGroupDao = mockk(relaxed = true)
        muscleSubGroupDao = mockk(relaxed = true)
    }
}