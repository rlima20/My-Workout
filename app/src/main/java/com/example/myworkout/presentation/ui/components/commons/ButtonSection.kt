package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
import com.example.myworkout.Constants
import com.example.myworkout.R
import com.example.myworkout.utils.Utils

@Composable
fun ButtonSection(
    modifier: Modifier = Modifier,
    titleSection: String,
    firstButtonName: String = Constants().emptyString(),
    secondButtonName: String = Constants().emptyString(),
    isDualButton: Boolean = false,
    buttonVisibility: Boolean = true,
    firstButtonEnabled: Boolean = false,
    secondButtonEnabled: Boolean = false,
    cardColors: CardColors = Utils().buttonSectionCardsColors(),
    hintEnabled: Boolean = false,
    hintText: String = Constants().emptyString(),
    onFirstButtonClick: () -> Unit = {},
    onSecondButtonClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxSize().padding(16.dp),
        colors = cardColors,
        shape = CardDefaults.elevatedShape,
        border = BorderStroke(1.dp, colorResource(R.color.border_color)),
        elevation = CardDefaults.cardElevation()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.title_color),
                maxLines = 1,
                text = titleSection
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column { content() }

            if (buttonVisibility) {
                ButtonRow(
                    isDualButton = isDualButton,
                    firstButtonName = firstButtonName,
                    secondButtonName = secondButtonName,
                    firstButtonEnabled = firstButtonEnabled,
                    secondButtonEnabled = secondButtonEnabled,
//                    hintEnabled = hintEnabled,
//                    hintText = hintText,
                    onFirstClick = onFirstButtonClick,
                    onSecondClick = onSecondButtonClick
                )
            }
        }
    }
}

@Composable
private fun ButtonRow(
    isDualButton: Boolean,
    firstButtonName: String,
    secondButtonName: String,
    firstButtonEnabled: Boolean,
    secondButtonEnabled: Boolean,
//    hintEnabled: Boolean,
//    hintText: String,
    onFirstClick: () -> Unit,
    onSecondClick: () -> Unit
) {
    if (isDualButton) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PrimaryButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp),
                text = firstButtonName,
                enabled = firstButtonEnabled,
//                hintEnabled = hintEnabled,
//                hintText = hintText,
                onClick = onFirstClick
            )

            PrimaryButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp),
                text = secondButtonName,
                enabled = secondButtonEnabled,
//                hintEnabled = hintEnabled,
//                hintText = hintText,
                onClick = { onSecondClick()}
            )
        }
    } else {
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = firstButtonName,
            enabled = firstButtonEnabled,
//            hintEnabled = hintEnabled,
//            hintText = hintText,
            onClick = { onFirstClick()}
        )
    }
}

// -------------------------
// PREVIEWS
// -------------------------

@Composable
@Preview(showBackground = true)
private fun ButtonSectionPreview() {
    var text by remember { mutableStateOf(Constants().emptyString()) }
    var buttonEnabled by remember { mutableStateOf(false) }

    ButtonSection(
        buttonVisibility = true,
        modifier = Modifier.height(300.dp),
        titleSection = stringResource(R.string.new_muscle_group),
        firstButtonName = stringResource(R.string.button_section_add_button),
        firstButtonEnabled = buttonEnabled,
        onFirstButtonClick = { /*TODO*/ }
    ) {
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
}

@Composable
@Preview(showBackground = true)
private fun DualButtonSectionPreview() {
    var text by remember { mutableStateOf(Constants().emptyString()) }
    var buttonEnabled by remember { mutableStateOf(false) }

    ButtonSection(
        isDualButton = true,
        modifier = Modifier.height(300.dp),
        titleSection = stringResource(R.string.new_muscle_group),
        firstButtonName = stringResource(R.string.button_section_add_button),
        secondButtonName = stringResource(R.string.button_section_add_button),
        firstButtonEnabled = buttonEnabled,
        secondButtonEnabled = buttonEnabled,
        onFirstButtonClick = { /*TODO*/ },
        onSecondButtonClick = { /*TODO*/ }
    ) {
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
}