package com.example.myworkout.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myworkout.R
import com.example.myworkout.presentation.ui.components.commons.ButtonSection

@Composable
fun NewMuscleGroupAndSubgroup(
    onCreateMuscleGroup: (name: String) -> Unit,
    onCreateMuscleSubGroup: () -> Unit
) {
    // Todo Faz o fetch dos grupos musculares

    SetScreenVisibility(
        onAddButtonClicked = {
            onCreateMuscleGroup(it)
        }
    )
}

@Composable
fun SetScreenVisibility(
    // muscleGroupSection: Boolean,
    onAddButtonClicked: (name: String) -> Unit
) {
    SetMuscleGroupSection { onAddButtonClicked(it) }
    // else SetMuscleSubGroupSection()
}

@Composable
fun SetMuscleGroupSection(onAddButtonClicked: (name: String) -> Unit) {

    var muscleGroupName by remember { mutableStateOf(String()) }
    var buttonEnabled by remember { mutableStateOf(false) }
    var enableTextField by remember { mutableStateOf(true) }

    Column(modifier = Modifier.padding(top = 90.dp)) {
        ButtonSection(
            modifier = Modifier,
            buttonName = stringResource(R.string.button_section_add_button),
            buttonEnabled = buttonEnabled,
            onButtonClick = {
                buttonEnabled = false
                enableTextField = false
                onAddButtonClicked(muscleGroupName)
            },
            content = {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = muscleGroupName,
                    onValueChange = {
                        muscleGroupName = it
                        buttonEnabled = it.isNotEmpty()
                    },
                    enabled = enableTextField,
                    label = { Text(stringResource(R.string.new_training_input_text_label)) }
                )
            }
        )
    }
}

@Composable
fun SetMuscleSubGroupSection() {
    Column(modifier = Modifier.padding(top = 90.dp)) {
        Text("Sub grupo - Tela")
    }
}


