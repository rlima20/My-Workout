package com.example.myworkout.presentation.ui.components.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun BottomAppBar(
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToAddTrainingScreen: () -> Unit,
) {
    BottomAppBar(
        actions = {
            IconButton(
                modifier = Modifier.padding(start = 8.dp),
                content = {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = "Localized description",
                    )
                },
                onClick = { onNavigateToHomeScreen() }
            )
            IconButton(
                content = {
                    Icon(
                        Icons.Filled.Face,
                        contentDescription = "Training Icon",
                    )
                },
                onClick = { onNavigateToAddTrainingScreen() })
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                content = { Icon(Icons.Filled.Add, "Add Training Icon") },
                onClick = { onNavigateToAddTrainingScreen() },
            )
        })

    // Todo - Strings
}