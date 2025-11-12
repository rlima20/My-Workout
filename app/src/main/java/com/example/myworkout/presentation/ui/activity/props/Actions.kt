package com.example.myworkout.presentation.ui.activity.props

import androidx.compose.runtime.Composable

data class Actions(
    val onChangeRouteToHomeScreen: (Boolean) -> Unit,
    val onChangeTopBarTitle: (String) -> Unit,
    val onDatabaseCreated: @Composable (() -> Unit),
    val onNavigateToGroupSubgroup: () -> Unit,
    val onNavigateToNewTraining: () -> Unit,
    val onNavigateToHomeScreen: () -> Unit
)