package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myworkout.enums.Status

@Composable
internal fun CheckBox(
    status: Status,
    isTrainingChecked: Boolean,
    onChecked: () -> Unit,
) {
    if (status != Status.EMPTY) {
        Checkbox(
            enabled = status != Status.MISSED,
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(y = (-24).dp),
            checked = isTrainingChecked,
            onCheckedChange = { onChecked() },
        )
    }
}

@Composable
@Preview
fun CheckBoxPreview() {
    CheckBox(
        status = Status.MISSED,
        isTrainingChecked = false,
        onChecked = {}
    )
}