package com.example.myworkout.presentation.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myworkout.R

@Composable
fun BottomAppBar(
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToAddTrainingScreen: () -> Unit,
) {
    BottomAppBar(
        modifier = Modifier.height(70.dp),
        containerColor = colorResource(R.color.top_bar_color),
        actions = {
            Row(horizontalArrangement = Arrangement.spacedBy(50.dp)) {
                IconButton(
                    modifier = Modifier
                        .padding(start = 80.dp)
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
                            androidx.compose.material3.Text(text = "Home")
                        }

                    },
                    onClick = { onNavigateToHomeScreen() }
                )
                IconButton(
                    modifier = Modifier
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
                            androidx.compose.material3.Text(text = "Novo treino")
                        }
                    },
                    onClick = { onNavigateToAddTrainingScreen() })
            }
        }
    )
}

@Composable
@Preview
private fun BottomBarPreview() {
    BottomAppBar({}, {})
}