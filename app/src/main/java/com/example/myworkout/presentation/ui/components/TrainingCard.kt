package com.example.myworkout.presentation.ui.components

import androidx.compose.runtime.Composable
import com.example.myworkout.data.model.MuscleGroup
import com.example.myworkout.data.model.Status
import com.example.myworkout.data.model.Training
import com.example.myworkout.presentation.ui.TrainingTabs

@Composable
fun TrainingCard(
    state: Status,
    training: Training,
    emptyState: @Composable () -> Unit,
    trainingTabs: TrainingTabs,
    listOfMuscleGroup: List<MuscleGroup>,
    screenName: String,
) {
}