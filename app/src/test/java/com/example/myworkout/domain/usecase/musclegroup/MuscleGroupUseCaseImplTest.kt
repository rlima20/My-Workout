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
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MuscleGroupUseCaseImplTest : BaseTest() {

    private lateinit var repository: MuscleGroupRepository
    private lateinit var useCase: MuscleGroupUseCaseImpl

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = MuscleGroupUseCaseImpl(repository)
    }

    @Test
    fun `should get MuscleSubGroups by trainingId`() = runTest {
        // given
        val expected = listOf(
            MuscleGroupTestDataFactory.muscleSubGroupModel(1, "Biceps", true),
            MuscleGroupTestDataFactory.muscleSubGroupModel(2, "Triceps", false)
        )
        coEvery { repository.getMuscleSubGroupsByTrainingId(1) } returns expected

        // when
        val result = useCase.getMuscleSubGroupsByTrainingId(1)

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `should get MuscleGroups with relations`() = runTest {
        // given
        val groups = listOf(
            MuscleGroupTestDataFactory.muscleGroupModel(1, "Arms", BodyPart.ARM),
            MuscleGroupTestDataFactory.muscleGroupModel(2, "Legs", BodyPart.LEG)
        )
        val relations = listOf(MuscleGroupTestDataFactory.muscleGroupMuscleSubGroupModel(1, 10))
        coEvery { repository.getMuscleGroups() } returns groups
        coEvery { repository.getAllRelations() } returns relations

        // when
        val result = useCase.getMuscleGroupsWithRelations()

        // then
        assertEquals(listOf(groups[0]), result)
    }

    @Test
    fun `should insert new MuscleGroup`() = runTest {
        // given
        val existingGroups =
            listOf(MuscleGroupTestDataFactory.muscleGroupModel(1, "Arms", BodyPart.ARM))
        coEvery { repository.getMuscleGroups() } returns existingGroups

        // when
        val result = useCase.insertMuscleGroup("Legs", BodyPart.LEG)

        // then
        assertEquals(2, result.muscleGroupId)
        assertEquals("Legs", result.name)
        assertEquals(BodyPart.LEG, result.image)
        coVerify { repository.insertMuscleGroup(result) }
    }

    @Test
    fun `should clear selected MuscleSubGroups`() = runTest {
        // given
        val subGroups = listOf(
            MuscleGroupTestDataFactory.muscleSubGroupModel(1, "Biceps", true),
            MuscleGroupTestDataFactory.muscleSubGroupModel(2, "Triceps", true)
        )

        // when
        useCase.clearSelectedMuscleSubGroups(subGroups)

        // then
        subGroups.forEach {
            val updated = it.copy(selected = false)
            coVerify { repository.updateSubGroup(updated) }
        }
    }

    @Test
    fun `should get SubGroups grouped by MuscleGroups`() = runTest {
        // given
        val mapExpected = mapOf(
            MuscleGroupTestDataFactory.muscleGroupModel(1, "Arms", BodyPart.ARM) to listOf(
                MuscleGroupTestDataFactory.muscleSubGroupModel(1, "Biceps", false)
            )
        )
        coEvery { repository.getSubGroupsGroupedByMuscleGroups() } returns mapExpected

        // when
        val result = useCase.getSubGroupsGroupedByMuscleGroups()

        // then
        assertEquals(mapExpected, result)
    }

    @Test
    fun `should insert MuscleSubGroup`() = runTest {
        // given
        val subGroup = MuscleGroupTestDataFactory.muscleSubGroupModel(1, "Chest", false)

        // when
        useCase.insertMuscleSubGroup(subGroup)

        // then
        coVerify { repository.insertMuscleSubGroup(subGroup) }
    }

    @Test
    fun `should insert TrainingMuscleGroup`() = runTest {
        // given
        val trainingGroup = MuscleGroupTestDataFactory.trainingMuscleGroupModel(1, 1)

        // when
        useCase.insertTrainingMuscleGroup(trainingGroup)

        // then
        coVerify { repository.insertTrainingMuscleGroup(trainingGroup) }
    }

    @Test
    fun `should insert MuscleGroupMuscleSubGroup`() = runTest {
        // given
        val relation = MuscleGroupTestDataFactory.muscleGroupMuscleSubGroupModel(1, 2)

        // when
        useCase.insertMuscleGroupMuscleSubGroup(relation)

        // then
        coVerify { repository.insertMuscleGroupMuscleSubGroup(relation) }
    }

    @Test
    fun `should get MuscleGroups`() = runTest {
        // given
        val expected = listOf(MuscleGroupTestDataFactory.muscleGroupModel(1, "Back", BodyPart.BACK))
        coEvery { repository.getMuscleGroups() } returns expected

        // when
        val result = useCase.getMuscleGroups()

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `should get MuscleSubGroups`() = runTest {
        // given
        val expected = listOf(MuscleGroupTestDataFactory.muscleSubGroupModel(1, "Abs", false))
        coEvery { repository.getMuscleSubGroups() } returns expected

        // when
        val result = useCase.getMuscleSubGroups()

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `should update SubGroup`() = runTest {
        // given
        val subGroup = MuscleGroupTestDataFactory.muscleSubGroupModel(1, "Chest", true)

        // when
        useCase.updateSubGroup(subGroup)

        // then
        coVerify { repository.updateSubGroup(subGroup) }
    }

    @Test
    fun `should get Relation by id`() = runTest {
        // given
        val expected = listOf(MuscleGroupTestDataFactory.muscleGroupMuscleSubGroupEntity(1, 2))
        coEvery { repository.getRelationById(1) } returns expected

        // when
        val result = useCase.getRelationById(1)

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `should get all Relations`() = runTest {
        // given
        val expected = listOf(MuscleGroupTestDataFactory.muscleGroupMuscleSubGroupModel(1, 2))
        coEvery { repository.getAllRelations() } returns expected

        // when
        val result = useCase.getAllRelations()

        // then
        assertEquals(expected, result)
    }


    /**
     * Fábrica de dados para reduzir repetição nos testes
     */
    object MuscleGroupTestDataFactory {
        fun muscleGroupModel(
            id: Int = 1,
            name: String = "Arms",
            bodyPart: BodyPart = BodyPart.ARM
        ) = MuscleGroupModel(id, name, bodyPart)

        fun muscleSubGroupModel(
            id: Int = 1,
            name: String = "Biceps",
            selected: Boolean = true
        ) = MuscleSubGroupModel(id, name, selected)

        fun trainingMuscleGroupModel(
            trainingId: Int = 1,
            muscleGroupId: Int = 1
        ) = TrainingMuscleGroupModel(trainingId, muscleGroupId)

        fun muscleGroupMuscleSubGroupModel(
            groupId: Int = 1,
            subGroupId: Int = 2
        ) = MuscleGroupMuscleSubGroupModel(groupId, subGroupId)

        fun muscleGroupMuscleSubGroupEntity(
            groupId: Int = 1,
            subGroupId: Int = 2
        ) = MuscleGroupMuscleSubGroupEntity(groupId, subGroupId)
    }
}
