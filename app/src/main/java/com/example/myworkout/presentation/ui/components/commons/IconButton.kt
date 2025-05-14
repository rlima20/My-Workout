package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myworkout.R

@Composable
internal fun IconButton(
    painter: Painter,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp, end = 4.dp, bottom = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.size(100.dp),
            onClick = { onClick() }
        ) {
            Image(
                modifier = Modifier.size(60.dp),
                painter = painter,
                contentDescription = null,
            )
        }
    }
}

@Composable
@Preview
private fun IconButtonPreview() {
    IconButton(
        painter = painterResource(R.drawable.add_icon),
        onClick = {}
    )
}