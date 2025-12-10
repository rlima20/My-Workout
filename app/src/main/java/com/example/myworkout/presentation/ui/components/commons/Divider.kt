package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Divider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = Color.LightGray,
    startIndent: Dp = 1.dp
) {
    Divider(
        modifier = modifier.padding(horizontal = 12.dp),
        thickness = thickness,
        color = color,
        startIndent = startIndent
    )
}