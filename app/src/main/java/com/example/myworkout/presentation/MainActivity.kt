package com.example.myworkout.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myworkout.presentation.ui.theme.MyWorkoutTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyWorkoutTheme {
                Scaffold(
                    topBar = { TopBar() },
                    modifier = Modifier.fillMaxSize(),
                    content = { HomeScreen() },
                    bottomBar = { BottomAppBar() })
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun TopBar() {
        CenterAlignedTopAppBar(
            modifier = Modifier.padding(top = 8.dp),
            title = { Text("Home Screen") })
    }


    @Composable
    private fun BottomAppBar() {
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
                    onClick = { navigateToHomeScreen() }
                )
                IconButton(
                    content = {
                        Icon(
                            Icons.Filled.Face,
                            contentDescription = "Training Icon",
                        )
                    },
                    onClick = { navigateToAddTrainingScreen() })
            },
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                    content = { Icon(Icons.Filled.Add, "Add Training Icon") },
                    onClick = { navigateToAddTrainingScreen() },
                )
            })
    }
}

private fun navigateToAddTrainingScreen() {}

private fun navigateToHomeScreen() {}

@Composable
fun HomeScreen() {
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyWorkoutTheme {
        HomeScreen()
    }
}