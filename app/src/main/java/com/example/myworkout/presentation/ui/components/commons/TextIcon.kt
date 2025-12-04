package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R

@Composable()
fun TextIcon(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.sort),
            contentDescription = null,
            modifier = Modifier.size(15.dp),
            tint = colorResource(R.color.text_color)
        )
        Text(
            color = colorResource(R.color.text_color),
            fontSize = 16.sp,
            text = "Ordenar"
        )
    }
}

@Composable
@Preview
private fun TextIconPreview() {
    TextIcon(Modifier)
}
