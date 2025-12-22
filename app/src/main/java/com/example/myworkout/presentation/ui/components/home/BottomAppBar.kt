package com.example.myworkout.presentation.ui.components.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R

@Composable
fun BottomAppBar(
    showNewTraining: Boolean = false,
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToMuscleConfigScreen: () -> Unit,
    onNavigateToNewTrainingScreen: () -> Unit,
) {
    Box(
        modifier = Modifier
            .border(
                width = 0.05.dp,
                color = colorResource(R.color.bottom_bar_color),
                shape = RectangleShape
            )
    ) {
        BottomAppBar(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
            containerColor = colorResource(R.color.top_bar_color),
            actions = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    HomeIcon(onNavigateToHomeScreen)
                    MuscleConfigIcon(onNavigateToMuscleConfigScreen)
                    NewTrainingIcon(showNewTraining, onNavigateToNewTrainingScreen)
                }
            }
        )
    }
}

@Composable
private fun RowScope.HomeIcon(onNavigateToHomeScreen: () -> Unit) {
    IconButton(
        modifier = Modifier
            .weight(1f)
            .size(100.dp, 100.dp),
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Filled.Home,
                    contentDescription = "",
                )
                Text(
                    fontSize = 14.sp,
                    text = stringResource(R.string.home),
                    style = TextStyle(
                        lineHeight = 12.sp
                    )
                )
            }

        },
        onClick = { onNavigateToHomeScreen() }
    )
}

@Composable
private fun RowScope.MuscleConfigIcon(onNavigateToMuscleConfigScreen: () -> Unit) {
    IconButton(
        modifier = Modifier
            .weight(1f)
            .size(100.dp, 100.dp),
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Filled.Build,
                    contentDescription = "",
                )
                Text(
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    text = stringResource(R.string.muscle_config),
                    style = TextStyle(
                        lineHeight = 12.sp
                    )
                )
            }
        },
        onClick = { onNavigateToMuscleConfigScreen() }
    )
}

@Composable
private fun RowScope.NewTrainingIcon(
    showNewTraining: Boolean,
    onNavigateToNewTrainingScreen: () -> Unit
) {
    if (showNewTraining) {
        IconButton(
            modifier = Modifier
                .weight(1f)
                .size(100.dp, 100.dp),
            content = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "",
                    )
                    Text(
                        fontSize = 14.sp,
                        text = stringResource(R.string.new_training)
                    )
                }
            },
            onClick = { onNavigateToNewTrainingScreen() }
        )
    }
}

@Composable
@Preview
private fun BottomBarPreview() {
    BottomAppBar(
        false,
        {},
        {},
        {}
    )
}