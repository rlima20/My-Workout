package com.example.nutrition.mynutrition.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrition.R

@Composable
fun Tooltip(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    fontSize: TextUnit = 12.sp,
    backgroundColor: Int = R.color.warning,
    icon: Painter? = painterResource(R.drawable.baseline_warning_24),
    onClick: () -> Unit
) {
    if (enabled) {
        Box(
            modifier = modifier
                .padding(top = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
                .background(colorResource(backgroundColor))
                .clickable(
                    interactionSource = null,
                    indication = null,
                    enabled = true,
                    onClick = { onClick() }
                )
        ) {
            Row(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon?.let {
                    Icon(
                        contentDescription = "",
                        painter = icon
                    )
                }

                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = text,
                    fontSize = fontSize,
                    color = colorResource(R.color.text_color)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ToolTipPreview() {
    Tooltip(
        text = stringResource(R.string.all_days_used),
        onClick = {}
    )
}

@Preview
@Composable
private fun ToolTipHomePreview() {
    Tooltip(
        backgroundColor = R.color.warning_home,
        icon = painterResource(R.drawable.baseline_info_24),
        text = stringResource(R.string.nutrition_info_call),
        onClick = {}
    )
}

@Preview
@Composable
private fun ToolTipHomeFontSpacingPreview() {
    Tooltip(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        backgroundColor = R.color.warning_home,
        fontSize = 12.sp,
        icon = painterResource(R.drawable.baseline_info_24),
        text = stringResource(R.string.nutrition_info_call),
        onClick = {}
    )
}