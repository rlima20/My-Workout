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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myworkout.R

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
                        contentDescription = stringResource(R.string.home_icon),
                    )
                },
                onClick = { onNavigateToHomeScreen() }
            )
            IconButton(
                content = {
                    Icon(
                        Icons.Filled.Face,
                        contentDescription = stringResource(R.string.training_icon),
                    )
                },
                onClick = { onNavigateToAddTrainingScreen() })
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                content = { Icon(Icons.Filled.Add, stringResource(R.string.add_training_icon)) },
                onClick = { onNavigateToAddTrainingScreen() },
            )
        })
}