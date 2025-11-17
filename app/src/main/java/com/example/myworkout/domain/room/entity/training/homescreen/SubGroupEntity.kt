package com.example.myworkout.domain.room.entity.training.homescreen

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub_group")
data class SubGroupEntity(
    @PrimaryKey(autoGenerate = false)
    val muscleSubGroupId: Int = 0,
    val name: String,
    val selected: Boolean = false
)