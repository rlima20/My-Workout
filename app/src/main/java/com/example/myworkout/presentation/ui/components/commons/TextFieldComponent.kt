package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.example.myworkout.utils.Utils

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    focusRequester: FocusRequester,
    colors: TextFieldColors = Utils().getTextFieldColors(),
    isSingleLine: Boolean = true,
    label: @Composable () -> Unit,
    onValueChange: (value: String) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = text,
        enabled = enabled,
        singleLine = isSingleLine,
        onValueChange = { onValueChange(it) },
        label = { label() },
        colors = colors
    )
}