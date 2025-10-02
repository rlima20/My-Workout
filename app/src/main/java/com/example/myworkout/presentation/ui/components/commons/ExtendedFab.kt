package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myworkout.R

@Composable
fun ExtendedFab(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    pressedColor: Int = R.color.text_color,
    defaultColor: Int = R.color.button_color,
    contentColor: Int = R.color.empty_state_color,
    contentDescription: String = stringResource(R.string.extended_floating_action_button),
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    ExtendedFloatingActionButton(
        onClick = { onClick() },
        modifier = modifier,
        icon = { Icon(icon, contentDescription) },
        text = { Text(text = text) },
        containerColor = color(isPressed, pressedColor, defaultColor),
        contentColor = colorResource(contentColor),
        interactionSource = interactionSource
    )
}

@Composable
private fun color(
    isPressed: Boolean,
    pressedColor: Int,
    defaultColor: Int
): Color =
    if (isPressed) colorResource(pressedColor)
    else colorResource(defaultColor)

@Preview
@Composable
private fun ExtendedFabPreview() {
    ExtendedFab(
        modifier = Modifier.fillMaxWidth(),
        icon = Icons.Default.ArrowForward,
        text = stringResource(R.string.next),
        onClick = {},
    )
}