package com.example.myworkout.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscle_group")
data class MuscleGroup(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "muscle_sub_groups") val muscleSubGroups: List<MuscleSubGroup>
)