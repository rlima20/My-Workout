package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R
import com.example.myworkout.extensions.emptyString
import com.example.myworkout.utils.Utils

@Composable
fun ButtonSection(
    modifier: Modifier,
    titleSection: String,
    buttonName: String = String().emptyString(),
    buttonVisibility: Boolean = true,
    buttonEnabled: Boolean = false,
    onButtonClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = Utils().buttonSectionCardsColors(),
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.cardElevation()
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 8.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.title_color),
            maxLines = 1,
            text = titleSection
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            content()
        }
        if (buttonVisibility) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                onClick = { onButtonClick() },
                enabled = buttonEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.button_color)
                )
            ) {
                Text(text = buttonName)
            }
        }
    }
}

@Composable
@Preview
private fun NewMuscleGroupPreview() {
    var text by remember { mutableStateOf(String().emptyString()) }
    var buttonEnabled by remember { mutableStateOf(false) }

    ButtonSection(
        modifier = Modifier.height(300.dp),
        titleSection = stringResource(R.string.new_muscle_group),
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