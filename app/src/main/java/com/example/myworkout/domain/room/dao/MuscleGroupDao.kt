package com.example.myworkout.domain.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.myworkout.domain.room.entity.MuscleGroupEntity

@Dao
interface MuscleGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroup(muscleGroup: MuscleGroupEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGroup(group: MuscleGroupEntity)

    @Delete
    fun deleteGroup(group: MuscleGroupEntity)

    @Query("SELECT * FROM muscle_group")
    fun getAllMuscleGroups(): List<MuscleGroupEntity>

    @Query("SELECT * FROM muscle_group WHERE muscleGroupId = :id")
    fun getMuscleGroupById(id: Int): MuscleGroupEntity?

    @Transaction
    suspend fun deleteMuscleGroupCascade(muscleGroupId: Int) {
        // 1. Deleta os relacionamentos com subgrupos
        deleteMuscleGroupMuscleSubGroupsByGroupId(muscleGroupId)

        // 2. Busca todos os treinos relacionados a esse grupo
        val trainingIds = getTrainingIdsByMuscleGroupId(muscleGroupId)

        // 3. Deleta os relacionamentos entre treino e grupo
        deleteTrainingMuscleGroupsByGroupId(muscleGroupId)

        // 4. Deleta os treinos associados (se essa for a regra de negócio)
        deleteTrainingsByIds(trainingIds)

        // 5. Por fim, deleta o grupo em si
        deleteMuscleGroupById(muscleGroupId)
    }

    @Query("DELETE FROM muscle_group_muscle_sub_group WHERE muscleGroupId = :muscleGroupId")
    suspend fun deleteMuscleGroupMuscleSubGroupsByGroupId(muscleGroupId: Int)

    @Query("DELETE FROM training_muscle_group WHERE muscleGroupId = :muscleGroupId")
    suspend fun deleteTrainingMuscleGroupsByGroupId(muscleGroupId: Int)

    @Query("SELECT DISTINCT trainingId FROM training_muscle_group WHERE muscleGroupId = :muscleGroupId")
    suspend fun getTrainingIdsByMuscleGroupId(muscleGroupId: Int): List<Int>

    @Query("DELETE FROM training WHERE trainingId IN (:trainingIds)")
    suspend fun deleteTrainingsByIds(trainingIds: List<Int>)

    @Query("DELETE FROM muscle_group WHERE muscleGroupId = :muscleGroupId")
    suspend fun deleteMuscleGroupById(muscleGroupId: Int)
}