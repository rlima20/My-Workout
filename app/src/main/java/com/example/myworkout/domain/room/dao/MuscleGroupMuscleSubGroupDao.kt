package com.example.myworkout.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity

@Dao
interface MuscleGroupMuscleSubGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupEntity)

    @Query("SELECT * FROM muscle_group_muscle_sub_group")
    fun getAllMuscleGroupMuscleSubGroups(): List<MuscleGroupMuscleSubGroupEntity>

    @Query("SELECT * FROM muscle_group_muscle_sub_group WHERE muscleGroupId = :muscleGroupId")
    fun getRelationById(muscleGroupId: Int): List<MuscleGroupMuscleSubGroupEntity>

    @Query("SELECT muscleSubGroupId FROM muscle_group_muscle_sub_group WHERE muscleGroupId = :muscleGroupId")
    fun getSubGroupsIdFromRelation(muscleGroupId: Int): List<Int>

    @Query("DELETE FROM muscle_group_muscle_sub_group WHERE muscleGroupId = :muscleGroupId")
    suspend fun deleteByGroupId(muscleGroupId: Int)

    @Query("DELETE FROM muscle_group_muscle_sub_group WHERE muscleSubGroupId = :muscleSubGroupId")
    suspend fun deleteRelationMuscleSubGroup(muscleSubGroupId: Int)

    @Query("DELETE FROM group_sub_group WHERE muscleSubGroupId = :muscleSubGroupId")
    suspend fun deleteRelationSubGroup(muscleSubGroupId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(relations: List<MuscleGroupMuscleSubGroupEntity>)

    /**
     * Atualiza os relacionamentos de um grupo muscular de forma atômica.
     * A transação garante que, se uma parte falhar, tudo será revertido.
     */
    @Transaction
    suspend fun replaceRelationsForGroup(
        muscleGroupId: Int,
        newRelations: List<MuscleGroupMuscleSubGroupEntity>
    ) {
        deleteByGroupId(muscleGroupId)
        insertAll(newRelations)
    }

    @Transaction
    suspend fun deleteSingleRelation( muscleSubGroupId: Int) {
        deleteRelationMuscleSubGroup(muscleSubGroupId)
        deleteRelationSubGroup(muscleSubGroupId)
    }
}