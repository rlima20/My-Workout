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
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status
import org.junit.Assert.assertEquals
import org.junit.Test


class MapperTest {

    @Test
    fun `test TrainingModel toEntity`() {
        val model = TrainingModel(
            trainingId = 1,
            status = Status.ACHIEVED,
            dayOfWeek = DayOfWeek.MONDAY,
            trainingName = "Chest Day"
        )

        val entity = model.toEntity()

        assertEquals(model.trainingId, entity.trainingId)
        assertEquals(model.status, entity.status)
        assertEquals(model.dayOfWeek, entity.dayOfWeek)
        assertEquals(model.trainingName, entity.trainingName)
    }

    @Test
    fun `test TrainingEntity toModel`() {
        val entity = TrainingEntity(
            trainingId = 2,
            status = Status.PENDING,
            dayOfWeek = DayOfWeek.FRIDAY,
            trainingName = "Leg Day"
        )

        val model = entity.toModel()

        assertEquals(entity.trainingId, model.trainingId)
        assertEquals(entity.status, model.status)
        assertEquals(entity.dayOfWeek, model.dayOfWeek)
        assertEquals(entity.trainingName, model.trainingName)
    }

    @Test
    fun `test MuscleGroupModel toEntity`() {
        val model = MuscleGroupModel(1, "Back", BodyPart.BACK)

        val entity = model.toEntity()

        assertEquals(model.muscleGroupId, entity.muscleGroupId)
        assertEquals(model.name, entity.name)
        assertEquals(model.image, entity.image)
    }

    @Test
    fun `test MuscleSubGroupEntity toModel`() {
        val entity = MuscleSubGroupEntity(5, "Biceps", true)

        val model = entity.toModel()

        assertEquals(entity.muscleSubGroupId, model.id)
        assertEquals(entity.name, model.name)
        assertEquals(entity.selected, model.selected)
    }

    @Test
    fun `test MuscleSubGroupModel toEntity`() {
        val model = MuscleSubGroupModel(6, "Triceps", false)

        val entity = model.toEntity()

        assertEquals(model.id, entity.muscleSubGroupId)
        assertEquals(model.name, entity.name)
        assertEquals(model.selected, entity.selected)
    }

    @Test
    fun `test TrainingMuscleGroupModel toEntity`() {
        val model = TrainingMuscleGroupModel(1, 10)

        val entity = model.toEntity()

        assertEquals(model.trainingId, entity.trainingId)
        assertEquals(model.muscleGroupId, entity.muscleGroupId)
    }

    @Test
    fun `test MuscleGroupMuscleSubGroupEntity toModel`() {
        val entity = MuscleGroupMuscleSubGroupEntity(2, 20)

        val model = entity.toModel()

        assertEquals(entity.muscleGroupId, model.muscleGroupId)
        assertEquals(entity.muscleSubGroupId, model.muscleSubGroupId)
    }

    @Test
    fun `test MuscleGroupMuscleSubGroupModel toEntity`() {
        val model = MuscleGroupMuscleSubGroupModel(3, 30)

        val entity = model.toEntity()

        assertEquals(model.muscleGroupId, entity.muscleGroupId)
        assertEquals(model.muscleSubGroupId, entity.muscleSubGroupId)
    }

    @Test
    fun `test List TrainingModel toEntityList`() {
        val list = listOf(
            TrainingModel(1, Status.ACHIEVED, DayOfWeek.SUNDAY, "Workout A"),
            TrainingModel(2, Status.PENDING, DayOfWeek.TUESDAY, "Workout B")
        )

        val entities = list.toEntityList()

        assertEquals(2, entities.size)
        assertEquals(list[0].trainingId, entities[0].trainingId)
        assertEquals(list[1].dayOfWeek, entities[1].dayOfWeek)
    }

    @Test
    fun `test List TrainingEntity toModelTrainingList`() {
        val list = listOf(
            TrainingEntity(1, Status.ACHIEVED, DayOfWeek.THURSDAY, "Workout C"),
            TrainingEntity(2, Status.PENDING, DayOfWeek.SATURDAY, "Workout D")
        )

        val models = list.toModelTrainingList()

        assertEquals(2, models.size)
        assertEquals(list[0].trainingId, models[0].trainingId)
        assertEquals(list[1].trainingName, models[1].trainingName)
    }

    @Test
    fun `test List MuscleGroupEntity toModelMuscleGroupList`() {
        val list = listOf(
            MuscleGroupEntity(1, "Chest", BodyPart.CHEST),
            MuscleGroupEntity(2, "Legs", BodyPart.LEG)
        )

        val models = list.toModelMuscleGroupList()

        assertEquals(2, models.size)
        assertEquals(list[0].name, models[0].name)
        assertEquals(list[1].image, models[1].image)
    }

    @Test
    fun `test List MuscleSubGroupEntity toModelMuscleSubGroupList`() {
        val list = listOf(
            MuscleSubGroupEntity(1, "Biceps", true),
            MuscleSubGroupEntity(2, "Triceps", false)
        )

        val models = list.toModelMuscleSubGroupList()

        assertEquals(2, models.size)
        assertEquals(list[0].name, models[0].name)
        assertEquals(list[1].selected, models[1].selected)
    }

    @Test
    fun `test List MuscleGroupMuscleSubGroupEntity toMuscleGroupMuscleSubGroupModel`() {
        val list = listOf(
            MuscleGroupMuscleSubGroupEntity(1, 100),
            MuscleGroupMuscleSubGroupEntity(2, 200)
        )

        val models = list.toMuscleGroupMuscleSubGroupModel()

        assertEquals(2, models.size)
        assertEquals(list[0].muscleGroupId, models[0].muscleGroupId)
        assertEquals(list[1].muscleSubGroupId, models[1].muscleSubGroupId)
    }
}
