package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.TextUnit
import com.example.myworkout.R
import androidx.compose.ui.graphics.Color as LabelColor

@Composable
fun Label(
    modifier: Modifier,
    text: String,
    fontSize: TextUnit,
    textColor: LabelColor = colorResource(R.color.title_color)
) {
    Text(
        fontSize = fontSize,
        modifier = modifier,
        text = text,
        color = textColor
    )
}