package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myworkout.Constants
import com.example.myworkout.R
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status

@Composable
fun CustomDialog(
    modifier: Modifier = Modifier,
    title: Int,
    message: Int? = null,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(250.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextSection(title, message)
                content()
                ButtonsSection(onDismissRequest, onConfirmation)
            }
        }
    }
}

@Composable
private fun TextSection(title: Int, message: Int?) {
    Text(
        modifier = Modifier.padding(8.dp),
        fontSize = 18.sp,
        text = stringResource(title)
    )

    message?.let {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(message)
        )
    }
}

@Composable
private fun ButtonsSection(onDismissRequest: () -> Unit, onConfirmation: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = { onDismissRequest() },
            modifier = Modifier.padding(12.dp),
            content = { Text(stringResource(R.string.cancell)) },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.button_color))
        )
        TextButton(
            onClick = { onConfirmation() },
            modifier = Modifier.padding(12.dp),
            content = { Text(stringResource(R.string.confirm)) },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.button_color))
        )
    }
}

@Composable
@Preview
fun CustomDialogPreview() {
    val training = Constants().getTrainingMock(Status.PENDING, "Ombro", DayOfWeek.FRIDAY)
    val focusRequester = remember { FocusRequester() }
    var trainingName by remember { mutableStateOf(training.trainingName) }
    val listOfDays = Constants().getListOfDays()

    CustomDialog(
        title = R.string.edit_training,
        content = {
            TextFieldComponent(
                modifier = Modifier.padding(bottom = 8.dp),
                text = trainingName,
                isSingleLine = true,
                focusRequester = focusRequester,
                onValueChange = { trainingName = it },
                enabled = true,
                label = {
                    Text(
                        text = "Label",
                        color = colorResource(R.color.title_color),
                        fontSize = 16.sp
                    )
                }
            )

            DropdownItem(
                modifier = Modifier.fillMaxWidth(),
                items = listOfDays,
                text = "dayOfWeek",
                enabled = true,
                onItemClick = { }
            )
        },
        onDismissRequest = {},
        onConfirmation = {},
    )
}