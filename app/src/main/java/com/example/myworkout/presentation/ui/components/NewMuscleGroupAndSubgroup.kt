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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myworkout.R
import com.example.myworkout.presentation.ui.components.commons.ButtonSection

@Composable
fun NewMuscleGroupAndSubgroup(
    onCreateMuscleGroup: (name: String) -> Unit,
    enableSubGroupSection: Boolean
) {
    Column(modifier = Modifier.padding(top = 70.dp)) {
        SetMuscleGroupSection { onCreateMuscleGroup(it) }
        SetMuscleSubGroupSection(enableSubGroupSection)
    }
}

@Composable
fun SetMuscleGroupSection(onAddButtonClicked: (name: String) -> Unit) {

    var muscleGroupName by remember { mutableStateOf(String()) }
    var buttonEnabled by remember { mutableStateOf(false) }
    var enableTextField by remember { mutableStateOf(true) }

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

@Composable
fun SetMuscleSubGroupSection(enableSubGroupSection: Boolean) {
    ButtonSection(
        modifier = Modifier,
        buttonName = stringResource(R.string.button_section_save_button),
        buttonEnabled = enableSubGroupSection,
        onButtonClick = { },
        content = { Text(text = "Exemplo") }
    )
}


@Composable
@Preview
fun NewMuscleGroupAndSubgroupPreview() {
    NewMuscleGroupAndSubgroup({}, true)
}


