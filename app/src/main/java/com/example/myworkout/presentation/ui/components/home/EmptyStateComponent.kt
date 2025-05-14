package com.example.myworkout.presentation.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R

@Composable
fun EmptyStateComponent(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = modifier,
            shape = CardDefaults.elevatedShape,
            elevation = CardDefaults.cardElevation()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(
                    modifier = Modifier.size(60.dp),
                    onClick = { onClick() }
                ) {
                    Image(
                        modifier = Modifier.size(500.dp),
                        painter = painter,
                        contentDescription = null,
                    )
                }
                Text(
                    text = text,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
@Preview
fun EmptyStateComponentPreview() {
    EmptyStateComponent(
        modifier = Modifier.size(150.dp, 180.dp),
        text = stringResource(R.string.new_training),
        painter = painterResource(R.drawable.add_icon),
        onClick = {}
    )
}