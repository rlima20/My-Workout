package com.example.mynutrition.presentation.nutrition.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R

@Composable
fun TextInfo(
    text: String,
    icon: Painter,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = text,
            color = colorResource(R.color.title_color),
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyMedium,
        )
        Icon(
            painter = icon,
            contentDescription = null,
            tint = colorResource(R.color.text_color),
            modifier = Modifier
                .size(15.dp)
                .clickable(onClick = { onIconClick() })
        )
    }
}


@Preview(showBackground = true, widthDp = 460)
@Composable
fun TextInfoPreview() {
    TextInfo(
        text = "TBM",
        icon = painterResource(R.drawable.baseline_info_24),
        onIconClick = {},
        modifier = Modifier,
    )
}