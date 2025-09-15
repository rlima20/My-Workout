package com.example.myworkout.domain.usecase.musclegroup

import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.repository.musclegroup.MuscleGroupRepository
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.presentation.viewmodel.BaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MuscleGroupUseCaseImplTest : BaseTest() {

    private lateinit var repository: MuscleGroupRepository
    private lateinit var useCase: MuscleGroupUseCaseImpl

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = MuscleGroupUseCaseImpl(repository)
    }

    @Test
    fun testGetMuscleSubGroupsByTrainingId() = runBlocking {
        // Given
        val expected = listOf(
            MuscleSubGroupModel(1, "Biceps", true),
            MuscleSubGroupModel(2, "Triceps", false)
        )

        coEvery { repository.getMuscleSubGroupsByTrainingId(1) } returns expected

        // When
        val result = useCase.getMuscleSubGroupsByTrainingId(1)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun testGetMuscleGroupsWithRelations() = runBlocking {
        // Given
        val groups = listOf(
            MuscleGroupModel(1, "Arms", BodyPart.ARM),
            MuscleGroupModel(2, "Legs", BodyPart.LEG)
        )
        val relations = listOf(
            MuscleGroupMuscleSubGroupModel(1, 10)
        )

        coEvery { repository.getMuscleGroups() } returns groups
        coEvery { repository.getAllRelations() } returns relations

        // When
        val result = useCase.getMuscleGroupsWithRelations()

        // Then
        assertEquals(listOf(groups[0]), result)
    }

    @Test
    fun testInsertMuscleGroup() = runBlocking {
        // Given
        val groups = listOf(MuscleGroupModel(1, "Arms", BodyPart.ARM))
        coEvery { repository.getMuscleGroups() } returns groups

        // When
        val result = useCase.insertMuscleGroup("Legs", BodyPart.LEG)

        // Then
        assertEquals(2, result.muscleGroupId)
        assertEquals("Legs", result.name)
        assertEquals(BodyPart.LEG, result.image)
        coVerify { repository.insertMuscleGroup(result) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testClearSelectedMuscleSubGroups() = runTest {
        // Given
        val subGroups = listOf(
            MuscleSubGroupModel(1, "Biceps", true),
            MuscleSubGroupModel(2, "Triceps", true)
        )

        // When
        useCase.clearSelectedMuscleSubGroups(subGroups)

        // Then
        subGroups.forEach {
            val updated = it.copy(selected = false)
            coVerify { repository.updateSubGroup(updated) }
        }
    }

    @Test
    fun testGetSubGroupsGroupedByMuscleGroups() = runBlocking {
        // Given
        val mapExpected = mapOf(
            MuscleGroupModel(1, "Arms", BodyPart.ARM) to listOf(
                MuscleSubGroupModel(1, "Biceps", false)
            )
        )
        coEvery { repository.getSubGroupsGroupedByMuscleGroups() } returns mapExpected

        // When
        val result = useCase.getSubGroupsGroupedByMuscleGroups()

        // Then
        assertEquals(mapExpected, result)
    }

    @Test
    fun testInsertMuscleSubGroup() = runBlocking {
        // Given
        val subGroup = MuscleSubGroupModel(1, "Chest", false)

        // When
        useCase.insertMuscleSubGroup(subGroup)

        // Then
        coVerify { repository.insertMuscleSubGroup(subGroup) }
    }

    @Test
    fun testInsertTrainingMuscleGroup() = runBlocking {
        // Given
        val trainingGroup = TrainingMuscleGroupModel(1, 1)

        // When
        useCase.insertTrainingMuscleGroup(trainingGroup)

        // Then
        coVerify { repository.insertTrainingMuscleGroup(trainingGroup) }
    }

    @Test
    fun testInsertMuscleGroupMuscleSubGroup() = runBlocking {
        // Given
        val relation = MuscleGroupMuscleSubGroupModel(1, 2)

        // When
        useCase.insertMuscleGroupMuscleSubGroup(relation)

        // Then
        coVerify { repository.insertMuscleGroupMuscleSubGroup(relation) }
    }

    @Test
    fun testGetMuscleGroups() = runBlocking {
        // Given
        val expected = listOf(
            MuscleGroupModel(1, "Back", BodyPart.BACK)
        )
        coEvery { repository.getMuscleGroups() } returns expected

        // When
        val result = useCase.getMuscleGroups()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun testGetMuscleSubGroups() = runBlocking {
        // Given
        val expected = listOf(
            MuscleSubGroupModel(1, "Abs", false)
        )
        coEvery { repository.getMuscleSubGroups() } returns expected

        // When
        val result = useCase.getMuscleSubGroups()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun testUpdateSubGroup() = runBlocking {
        // Given
        val subGroup = MuscleSubGroupModel(1, "Chest", true)

        // When
        useCase.updateSubGroup(subGroup)

        // Then
        coVerify { repository.updateSubGroup(subGroup) }
    }

    @Test
    fun testGetRelationById() = runBlocking {
        // Given
        val expected = listOf(MuscleGroupMuscleSubGroupEntity(1, 2))
        coEvery { repository.getRelationById(1) } returns expected

        // When
        val result = useCase.getRelationById(1)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun testGetAllRelations() = runBlocking {
        // Given
        val expected = listOf(MuscleGroupMuscleSubGroupModel(1, 2))
        coEvery { repository.getAllRelations() } returns expected

        // When
        val result = useCase.getAllRelations()

        // Then
        assertEquals(expected, result)
    }
}
