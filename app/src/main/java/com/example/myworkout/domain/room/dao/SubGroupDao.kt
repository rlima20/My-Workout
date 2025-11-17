package com.example.myworkout.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.training.homescreen.GroupSubGroupEntity
import com.example.myworkout.domain.room.entity.training.homescreen.SubGroupEntity

@Dao
interface SubGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(muscleSubGroup: SubGroupEntity)

    @Query("SELECT * FROM sub_group")
    fun getAllMuscleSubGroups(): List<SubGroupEntity>

    @Query("SELECT * FROM sub_group WHERE muscleSubGroupId = :id")
    fun getSubgroupById(id: Int): SubGroupEntity?

    @Query("SELECT * FROM sub_group WHERE muscleSubGroupId = :id")
    fun getSubgroupsById(id: Int): List<SubGroupEntity>?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSubGroup(subGroup: SubGroupEntity)

    @Query("DELETE FROM group_sub_group WHERE muscleGroupId = :muscleGroupId")
    suspend fun deleteByGroupId(muscleGroupId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(relations: List<GroupSubGroupEntity>)


    /**
     * Atualiza os relacionamentos de um grupo muscular de forma atômica.
     * A transação garante que, se uma parte falhar, tudo será revertido.
     */
    @Transaction
    suspend fun replaceRelationsForGroup(
        muscleGroupId: Int,
        newRelations: List<GroupSubGroupEntity>
    ) {
        deleteByGroupId(muscleGroupId)
        insertAll(newRelations)
    }
}