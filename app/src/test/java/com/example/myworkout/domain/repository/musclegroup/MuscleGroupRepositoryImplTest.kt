package com.example.myworkout.domain.repository.musclegroup

import android.annotation.SuppressLint
import com.example.myworkout.domain.mapper.toModelMuscleGroupList
import com.example.myworkout.domain.mapper.toModelMuscleSubGroupList
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.room.dao.MuscleGroupDao
import com.example.myworkout.domain.room.dao.MuscleGroupMuscleSubGroupDao
import com.example.myworkout.domain.room.dao.MuscleSubGroupDao
import com.example.myworkout.domain.room.dao.TrainingMuscleGroupDao
import com.example.myworkout.domain.room.entity.MuscleGroupEntity
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.MuscleSubGroupEntity
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.presentation.viewmodel.BaseTest
import io.mockk.every
import io.mockk.mockk
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

        every { muscleSubGroupDao.getMuscleSubGroupById(1) } returns returnExpected

        // When
        val result = muscleGroupRepositoryImpl.getMuscleSubGroup(
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

        every { muscleSubGroupDao.getMuscleSubGroupById(1) } returns returnExpected

        // When
        val result = muscleSubGroupDao.getMuscleSubGroupById(1)

        // Then
        assertEquals(returnExpected, result)
    }

    @Test
    fun testGetRelationByTrainingMuscleGroup() {
        // Given
        val muscleGroupId = 1
        val muscleSubGroupId = 1

        val returnExpected = listOf(
            MuscleGroupMuscleSubGroupEntity(muscleGroupId, muscleSubGroupId),
            MuscleGroupMuscleSubGroupEntity(muscleGroupId, muscleSubGroupId),
        )

        every { muscleGroupMuscleSubGroupDao.getRelationById(1) } returns returnExpected

        // When
        val result = muscleGroupMuscleSubGroupDao.getRelationById(1)

        // Then
        assertEquals(returnExpected, result)
    }

    @Test
    fun testGetAllRelations() {
        // Given
        val returnExpected = listOf(
            MuscleGroupMuscleSubGroupEntity(1, 1),
            MuscleGroupMuscleSubGroupEntity(1, 1)
        )

        every { muscleGroupMuscleSubGroupDao.getAllMuscleGroupMuscleSubGroups() } returns returnExpected

        // When
        val result = muscleGroupMuscleSubGroupDao.getAllMuscleGroupMuscleSubGroups()

        // Then
        assertEquals(returnExpected, result)
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