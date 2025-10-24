package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import com.example.myworkout.R

@Composable
fun Tooltip(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    icon: Painter? = painterResource(R.drawable.baseline_warning_24),
) {
    if (enabled) {
        Box(
            modifier = modifier
                .padding(top = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.warning))
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
        text = stringResource(R.string.all_days_used)
    )
}