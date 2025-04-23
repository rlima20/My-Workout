package com.example.myworkout.presentation.viewmodel

import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.TrainingModel

data class TrainingViewState(
    val trainings: List<TrainingModel> = emptyList(),
    val isEmpty: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class MuscleGroupViewState(
    val muscleGroups: List<MuscleGroupModel> = emptyList(),
    val isEmpty: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

enum class DatabaseState {
    SUCCESS,
    EMPTY,
    LOADING,
    ERROR
}