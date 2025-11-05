package com.example.myworkout.domain.room.database

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myworkout.domain.room.dao.MuscleGroupDao
import com.example.myworkout.domain.room.dao.MuscleGroupMuscleSubGroupDao
import com.example.myworkout.domain.room.dao.MuscleSubGroupDao
import com.example.myworkout.domain.room.dao.TrainingDao
import com.example.myworkout.domain.room.dao.TrainingMuscleGroupDao
import com.example.myworkout.domain.room.entity.MuscleGroupEntity
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.MuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.TrainingEntity
import com.example.myworkout.domain.room.entity.TrainingMuscleGroupEntity
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var trainingDao: TrainingDao
    private lateinit var muscleGroupDao: MuscleGroupDao
    private lateinit var muscleSubGroupDao: MuscleSubGroupDao
    private lateinit var muscleGroupMuscleSubGroupDao: MuscleGroupMuscleSubGroupDao
    private lateinit var trainingMuscleGroupDao: TrainingMuscleGroupDao

    @Before
    fun createDb() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        trainingDao = db.trainingDao()
        muscleGroupDao = db.muscleGroupDao()
        muscleSubGroupDao = db.muscleSubGroupDao()
        muscleGroupMuscleSubGroupDao = db.muscleGroupMuscleSubGroupDao()
        trainingMuscleGroupDao = db.trainingMuscleGroupDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertAndGetTraining() {
        val training = TrainingEntity(
            trainingId = 1,
            status = Status.PENDING,
            dayOfWeek = DayOfWeek.MONDAY,
            trainingName = "Chest Day"
        )
        trainingDao.insert(training)

        val result = trainingDao.getTrainingById(1)
        assertNotNull(result)
        assertEquals("Chest Day", result?.trainingName)
    }

    @Test
    fun testInsertAndGetMuscleGroup() {
        val muscleGroup =
            MuscleGroupEntity(
                muscleGroupId = 10,
                name = "Upper Body",
                image = BodyPart.OTHER
            )
        muscleGroupDao.insertGroup(muscleGroup)

        val result = muscleGroupDao.getMuscleGroupById(10)
        assertNotNull(result)
        assertEquals("Upper Body", result?.name)
    }

    @Test
    fun testInsertAndGetMuscleSubGroup() {
        val subGroup = MuscleSubGroupEntity(muscleSubGroupId = 20, name = "Triceps")
        muscleSubGroupDao.insert(subGroup)

        val result = muscleSubGroupDao.getSubgroupById(20)
        assertNotNull(result)
        assertEquals("Triceps", result?.name)
    }

    @Test
    fun testInsertAndGetRelation() {
        val relation = MuscleGroupMuscleSubGroupEntity(muscleGroupId = 10, muscleSubGroupId = 20)
        muscleGroupMuscleSubGroupDao.insert(relation)

        val result = muscleGroupMuscleSubGroupDao.getRelationById(10)
        assertTrue(result.isNotEmpty())
        assertEquals(20, result.first().muscleSubGroupId)
    }

    @Test
    fun testInsertAndGetTrainingMuscleGroup() {
        val relation = TrainingMuscleGroupEntity(trainingId = 1, muscleGroupId = 10)
        trainingMuscleGroupDao.insert(relation)

        val result = trainingMuscleGroupDao.getMuscleGroupsForTraining(1)
        assertTrue(result.isNotEmpty())
        assertEquals(10, result.first().muscleGroupId)
    }

    @Test
    fun testSingletonGetInstance() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val instance1 = AppDatabase.getInstance(context)
        val instance2 = AppDatabase.getInstance(context)

        assertSame(instance1, instance2) // deve retornar a mesma instância
    }
}
