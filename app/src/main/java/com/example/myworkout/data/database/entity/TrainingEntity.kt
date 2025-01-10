package com.example.myworkout.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myworkout.data.model.Status

@Entity(tableName = "training")
data class TrainingEntity(
    @PrimaryKey(autoGenerate = true) val trainingId: Int,
    @ColumnInfo(name = "status") var status: Status,
    @ColumnInfo(name = "day_of_week") val dayOfWeek: Int,
    @ColumnInfo(name = "training_name") val trainingName: String,
)