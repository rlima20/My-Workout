package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myworkout.enums.Status

@Composable
internal fun CheckBox(
    modifier: Modifier,
    status: Status,
    isTrainingChecked: Boolean,
    onChecked: () -> Unit,
) {
    if (status != Status.EMPTY) {
        Checkbox(
            modifier = modifier,
            enabled = status != Status.MISSED,
            checked = isTrainingChecked,
            onCheckedChange = { onChecked() },
        )
    }
}

@Composable
@Preview
fun CheckBoxPreview() {
    CheckBox(
        modifier = Modifier,
        status = Status.MISSED,
        isTrainingChecked = false,
        onChecked = {}
    )
}