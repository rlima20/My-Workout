package com.example.myworkout.presentation.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home

object HomeScreen : DestinationInterface {
    override val icon = Icons.Filled.Home
    override val route = "home_screen"
}

object NewTraining : DestinationInterface {
    override val icon = Icons.Filled.Add
    override val route = "new_training"
}

object New : DestinationInterface {
    override val icon = Icons.Filled.Add
    override val route = "new"
}