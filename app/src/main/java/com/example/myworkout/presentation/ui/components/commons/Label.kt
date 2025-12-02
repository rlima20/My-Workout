package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.myworkout.R
import androidx.compose.ui.graphics.Color as LabelColor

@Composable
fun Label(
    modifier: Modifier,
    text: String,
    fontSize: TextUnit,
    style: TextStyle = TextStyle.Default,
    textColor: LabelColor = colorResource(R.color.title_color)
) {
    Text(
        fontSize = fontSize,
        modifier = modifier,
        text = text,
        color = textColor,
        style = style
    )
}