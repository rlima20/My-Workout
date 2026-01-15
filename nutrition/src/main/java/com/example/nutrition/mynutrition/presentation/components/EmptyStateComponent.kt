package com.example.nutrition.mynutrition.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrition.R
import com.example.nutrition.mynutrition.utils.getCardColors

@Composable
fun EmptyStateComponent(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter,
    onClick: () -> Unit,
    backgroundColor: Color = colorResource(R.color.training_section_card_color)
) {
    val paddingToAdjustPositionToCenter = 78.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingToAdjustPositionToCenter),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            colors = getCardColors(),
            modifier = modifier,
            shape = CardDefaults.elevatedShape,
            elevation = CardDefaults.cardElevation()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor),
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
                    textAlign = TextAlign.Center,
                    text = text,
                    fontSize = 12.sp,
                    color = colorResource(R.color.text_color)
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
        text = stringResource(R.string.carbs),
        painter = painterResource(R.drawable.baseline_warning_24),
        onClick = {}
    )
}