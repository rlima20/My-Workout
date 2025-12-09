package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R

@Composable()
fun TextIcon(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 16.sp,
    contentDescription: String? = null,
    icon: Painter,
    iconSize: Dp = 15.dp,
    textColor: Color = colorResource(R.color.title_color),
    tintColor: Color = colorResource(R.color.title_color)
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize),
            tint = tintColor,
        )
        Text(
            color = textColor,
            fontSize = fontSize,
            text = text
        )
    }
}

@Composable
@Preview
private fun TextIconPreview() {
    TextIcon(
        modifier = Modifier,
        text = "Ordenar",
        icon = painterResource(R.drawable.sort)
    )
}
