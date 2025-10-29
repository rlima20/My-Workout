package com.example.myworkout.domain.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myworkout.enums.Status
import com.example.myworkout.enums.DayOfWeek

@Entity(tableName = "training")
data class TrainingEntity(
    @PrimaryKey(autoGenerate = true)
    val trainingId: Int = 0,
    var status: Status,
    val dayOfWeek: DayOfWeek,
    val trainingName: String = "",
    val isChecked: Boolean = false
)