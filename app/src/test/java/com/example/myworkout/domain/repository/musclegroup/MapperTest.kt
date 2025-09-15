package com.example.myworkout.domain.repository.musclegroup

import com.example.myworkout.domain.mapper.toEntity
import com.example.myworkout.domain.mapper.toEntityList
import com.example.myworkout.domain.mapper.toModel
import com.example.myworkout.domain.mapper.toModelMuscleGroupList
import com.example.myworkout.domain.mapper.toModelMuscleSubGroupList
import com.example.myworkout.domain.mapper.toModelTrainingList
import com.example.myworkout.domain.mapper.toMuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.room.entity.MuscleGroupEntity
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.MuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.TrainingEntity
import com.example.myworkout.domain.room.entity.TrainingMuscleGroupEntity
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MapperTest {

    @Test
    fun `should map TrainingModel to TrainingEntity`() {
        // given
        val model = TestDataFactory.trainingModel()

        // when
        val entity = model.toEntity()

        // then
        assertEquals(model.trainingId, entity.trainingId)
        assertEquals(model.status, entity.status)
        assertEquals(model.dayOfWeek, entity.dayOfWeek)
        assertEquals(model.trainingName, entity.trainingName)
    }

    @Test
    fun `should map TrainingEntity to TrainingModel`() {
        // given
        val entity = TestDataFactory.trainingEntity(2, Status.PENDING, DayOfWeek.FRIDAY, "Leg Day")

        // when
        val model = entity.toModel()

        // then
        assertEquals(entity.trainingId, model.trainingId)
        assertEquals(entity.status, model.status)
        assertEquals(entity.dayOfWeek, model.dayOfWeek)
        assertEquals(entity.trainingName, model.trainingName)
    }

    @Test
    fun `should map MuscleGroupModel to MuscleGroupEntity`() {
        // given
        val model = TestDataFactory.muscleGroupModel()

        // when
        val entity = model.toEntity()

        // then
        assertEquals(model.muscleGroupId, entity.muscleGroupId)
        assertEquals(model.name, entity.name)
        assertEquals(model.image, entity.image)
    }

    @Test
    fun `should map MuscleSubGroupEntity to MuscleSubGroupModel`() {
        // given
        val entity = TestDataFactory.muscleSubGroupEntity(5, "Biceps", true)

        // when
        val model = entity.toModel()

        // then
        assertEquals(entity.muscleSubGroupId, model.id)
        assertEquals(entity.name, model.name)
        assertEquals(entity.selected, model.selected)
    }

    @Test
    fun `should map MuscleSubGroupModel to MuscleSubGroupEntity`() {
        // given
        val model = TestDataFactory.muscleSubGroupModel(6, "Triceps", false)

        // when
        val entity = model.toEntity()

        // then
        assertEquals(model.id, entity.muscleSubGroupId)
        assertEquals(model.name, entity.name)
        assertEquals(model.selected, entity.selected)
    }

    @Test
    fun `should map TrainingMuscleGroupModel to TrainingMuscleGroupEntity`() {
        // given
        val model = TestDataFactory.trainingMuscleGroupModel()

        // when
        val entity = model.toEntity()

        // then
        assertEquals(model.trainingId, entity.trainingId)
        assertEquals(model.muscleGroupId, entity.muscleGroupId)
    }

    @Test
    fun `should map MuscleGroupMuscleSubGroupEntity to MuscleGroupMuscleSubGroupModel`() {
        // given
        val entity = TestDataFactory.muscleGroupMuscleSubGroupEntity(2, 20)

        // when
        val model = entity.toModel()

        // then
        assertEquals(entity.muscleGroupId, model.muscleGroupId)
        assertEquals(entity.muscleSubGroupId, model.muscleSubGroupId)
    }

    @Test
    fun `should map MuscleGroupMuscleSubGroupModel to MuscleGroupMuscleSubGroupEntity`() {
        // given
        val model = TestDataFactory.muscleGroupMuscleSubGroupModel(3, 30)

        // when
        val entity = model.toEntity()

        // then
        assertEquals(model.muscleGroupId, entity.muscleGroupId)
        assertEquals(model.muscleSubGroupId, entity.muscleSubGroupId)
    }

    @Test
    fun `should map TrainingModel list to TrainingEntity list`() {
        // given
        val models = listOf(
            TestDataFactory.trainingModel(1, Status.ACHIEVED, DayOfWeek.SUNDAY, "Workout A"),
            TestDataFactory.trainingModel(2, Status.PENDING, DayOfWeek.TUESDAY, "Workout B")
        )

        // when
        val entities = models.toEntityList()

        // then
        assertEquals(2, entities.size)
        assertEquals(models[0].trainingId, entities[0].trainingId)
        assertEquals(models[1].dayOfWeek, entities[1].dayOfWeek)
    }

    @Test
    fun `should map TrainingEntity list to TrainingModel list`() {
        // given
        val entities = listOf(
            TestDataFactory.trainingEntity(1, Status.ACHIEVED, DayOfWeek.THURSDAY, "Workout C"),
            TestDataFactory.trainingEntity(2, Status.PENDING, DayOfWeek.SATURDAY, "Workout D")
        )

        // when
        val models = entities.toModelTrainingList()

        // then
        assertEquals(2, models.size)
        assertEquals(entities[0].trainingId, models[0].trainingId)
        assertEquals(entities[1].trainingName, models[1].trainingName)
    }

    @Test
    fun `should map MuscleGroupEntity list to MuscleGroupModel list`() {
        // given
        val entities = listOf(
            TestDataFactory.muscleGroupEntity(1, "Chest", BodyPart.CHEST),
            TestDataFactory.muscleGroupEntity(2, "Legs", BodyPart.LEG)
        )

        // when
        val models = entities.toModelMuscleGroupList()

        // then
        assertEquals(2, models.size)
        assertEquals(entities[0].name, models[0].name)
        assertEquals(entities[1].image, models[1].image)
    }

    @Test
    fun `should map MuscleSubGroupEntity list to MuscleSubGroupModel list`() {
        // given
        val entities = listOf(
            TestDataFactory.muscleSubGroupEntity(1, "Biceps", true),
            TestDataFactory.muscleSubGroupEntity(2, "Triceps", false)
        )

        // when
        val models = entities.toModelMuscleSubGroupList()

        // then
        assertEquals(2, models.size)
        assertEquals(entities[0].name, models[0].name)
        assertEquals(entities[1].selected, models[1].selected)
    }

    @Test
    fun `should map MuscleGroupMuscleSubGroupEntity list to MuscleGroupMuscleSubGroupModel list`() {
        // given
        val entities = listOf(
            TestDataFactory.muscleGroupMuscleSubGroupEntity(1, 100),
            TestDataFactory.muscleGroupMuscleSubGroupEntity(2, 200)
        )

        // when
        val models = entities.toMuscleGroupMuscleSubGroupModel()

        // then
        assertEquals(2, models.size)
        assertEquals(entities[0].muscleGroupId, models[0].muscleGroupId)
        assertEquals(entities[1].muscleSubGroupId, models[1].muscleSubGroupId)
    }

    // Edge case
    @Test
    fun `should return empty list when mapping empty TrainingEntity list`() {
        // given
        val entities = emptyList<TrainingEntity>()

        // when
        val models = entities.toModelTrainingList()

        // then
        assertTrue(models.isEmpty())
    }

    /**
     * Factories auxiliares para reduzir repetição de código nos testes
     */
    object TestDataFactory {
        fun trainingModel(
            id: Int = 1,
            status: Status = Status.ACHIEVED,
            day: DayOfWeek = DayOfWeek.MONDAY,
            name: String = "Chest Day"
        ) = TrainingModel(id, status, day, name)

        fun trainingEntity(
            id: Int = 1,
            status: Status = Status.ACHIEVED,
            day: DayOfWeek = DayOfWeek.MONDAY,
            name: String = "Chest Day"
        ) = TrainingEntity(id, status, day, name)

        fun muscleGroupModel(
            id: Int = 1,
            name: String = "Back",
            bodyPart: BodyPart = BodyPart.BACK
        ) = MuscleGroupModel(id, name, bodyPart)

        fun muscleGroupEntity(
            id: Int = 1,
            name: String = "Chest",
            bodyPart: BodyPart = BodyPart.CHEST
        ) = MuscleGroupEntity(id, name, bodyPart)

        fun muscleSubGroupModel(
            id: Int = 1,
            name: String = "Biceps",
            selected: Boolean = true
        ) = MuscleSubGroupModel(id, name, selected)

        fun muscleSubGroupEntity(
            id: Int = 1,
            name: String = "Biceps",
            selected: Boolean = true
        ) = MuscleSubGroupEntity(id, name, selected)

        fun trainingMuscleGroupModel(
            trainingId: Int = 1,
            muscleGroupId: Int = 10
        ) = TrainingMuscleGroupModel(trainingId, muscleGroupId)

        fun trainingMuscleGroupEntity(
            trainingId: Int = 1,
            muscleGroupId: Int = 10
        ) = TrainingMuscleGroupEntity(trainingId, muscleGroupId)

        fun muscleGroupMuscleSubGroupModel(
            groupId: Int = 1,
            subGroupId: Int = 100
        ) = MuscleGroupMuscleSubGroupModel(groupId, subGroupId)

        fun muscleGroupMuscleSubGroupEntity(
            groupId: Int = 1,
            subGroupId: Int = 100
        ) = MuscleGroupMuscleSubGroupEntity(groupId, subGroupId)
    }
}
