package com.example.myworkout.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscle_sub_group")
data class MuscleSubGroup(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "selected") var selected: Boolean = false
)