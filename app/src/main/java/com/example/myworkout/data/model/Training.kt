package com.example.myworkout.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "training")
data class Training(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "muscle_groups") val muscleGroups: List<MuscleGroup>,
    @ColumnInfo(name = "status") var status: Status,
    @ColumnInfo(name = "day_of_week") val dayOfWeek: Int
)