package com.example.myworkout.domain.repository.training

import com.example.myworkout.domain.mapper.toEntity
import com.example.myworkout.domain.mapper.toModelTrainingList
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.room.dao.TrainingDao
import com.example.myworkout.domain.room.dao.TrainingMuscleGroupDao
import com.example.myworkout.domain.room.entity.TrainingEntity
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TrainingRepositoryImplTest {

    private lateinit var trainingDao: TrainingDao
    private lateinit var trainingMuscleGroupDao: TrainingMuscleGroupDao
    private lateinit var trainingRepository: TrainingRepositoryImpl

    @Before
    fun setUp() {
        trainingDao = mockk(relaxed = true)
        trainingMuscleGroupDao = mockk(relaxed = true)
        trainingRepository = TrainingRepositoryImpl(trainingDao, trainingMuscleGroupDao)
    }

    @Test
    fun `test getTrainings returns mapped list`() = runBlocking {
        // Given
        val entityList = listOf(
            TrainingEntity(1, Status.ACHIEVED, DayOfWeek.MONDAY, "Chest Day"),
            TrainingEntity(2, Status.PENDING, DayOfWeek.FRIDAY, "Leg Day")
        )

        every { trainingDao.getAllTrainings() } returns entityList

        // When
        val result = trainingRepository.getTrainings()

        // Then
        assertEquals(entityList.toModelTrainingList(), result)
    }

    @Test
    fun `test insertTraining calls dao insert`() {
        // Given
        val trainingModel = TrainingModel(
            trainingId = 1,
            status = Status.PENDING,
            dayOfWeek = DayOfWeek.SUNDAY,
            trainingName = "Arm Day"
        )

        // When
        trainingRepository.insertTraining(trainingModel)

        // Then
        verify { trainingDao.insert(trainingModel.toEntity()) }
    }

    @Test
    fun `test insertTrainingMuscleGroup calls dao insert`() {
        // Given
        val trainingMuscleGroupModel = TrainingMuscleGroupModel(1, 10)

        // When
        trainingRepository.insertTrainingMuscleGroup(trainingMuscleGroupModel)

        // Then
        verify { trainingMuscleGroupDao.insert(trainingMuscleGroupModel.toEntity()) }
    }
}
