package com.example.myworkout.presentation.ui.components.training

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.myworkout.extensions.emptyString


@Composable
fun ButtonSection(
    modifier: Modifier,
    buttonName: String,
    buttonEnabled: Boolean,
    onButtonClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(8.dp),
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.cardElevation()
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            content()
        }
        Button(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            onClick = { onButtonClick() },
            enabled = buttonEnabled
        ) {
            Text(text = buttonName)
        }
    }
}

@Composable
@Preview
private fun NewMuscleGroupPreview() {
    var text by remember { mutableStateOf(String().emptyString()) }
    var buttonEnabled by remember { mutableStateOf(false) }

    ButtonSection(
        modifier = Modifier,
        buttonName = stringResource(R.string.button_section_add_button),
        buttonEnabled = buttonEnabled,
        onButtonClick = { },
        content = {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    text = it
                    buttonEnabled = it.isNotEmpty()
                },
                label = { Text(stringResource(R.string.new_training_input_text_label)) }
            )
        }
    )
}