package com.example.myworkout.presentation.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToMuscleConfigScreen: () -> Unit,
    onNavigateToNewTrainingScreen: () -> Unit,
) {
    BottomAppBar(
        modifier = Modifier
            .height(76.dp)
            .fillMaxWidth(),
        containerColor = colorResource(R.color.bottom_bar_color),
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
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
                                modifier = Modifier.size(25.dp),
                                imageVector = Icons.Filled.Home,
                                contentDescription = "",
                            )
                            Text(text = stringResource(R.string.home))
                        }

                    },
                    onClick = { onNavigateToHomeScreen() }
                )
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
                                modifier = Modifier.size(25.dp),
                                imageVector = Icons.Filled.Build,
                                contentDescription = "",
                            )
                            Text(
                                textAlign = TextAlign.Center,
                                text = stringResource(R.string.muscle_config),
                                style = TextStyle(
                                    lineHeight = 12.sp
                                )
                            )
                        }
                    },
                    onClick = { onNavigateToMuscleConfigScreen() }
                )

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
                                modifier = Modifier.size(25.dp),
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "",
                            )
                            Text(text = stringResource(R.string.new_training))
                        }
                    },
                    onClick = { onNavigateToNewTrainingScreen() }
                )
            }
        }
    )
}

@Composable
@Preview
private fun BottomBarPreview() {
    BottomAppBar({}, {}, {})
}