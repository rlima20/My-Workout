package com.example.myworkout.presentation.ui.components.musclegroup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R
import com.example.myworkout.presentation.ui.components.commons.Label

@Composable
fun ItemCard(
    modifier: Modifier,
    colors: CardColors,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier.clickable(onClick = { onClick() }),
        colors = colors,
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun MuscleGroupItemCardPreview() {
    var text by remember { mutableStateOf("Ombro") }

    val colors = CardDefaults.cardColors(
        containerColor = colorResource(R.color.top_bar_color),
        contentColor = colorResource(R.color.top_bar_color),
        disabledContainerColor = colorResource(R.color.top_bar_color),
        disabledContentColor = colorResource(R.color.top_bar_color)
    )

    ItemCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = colors,
        onClick = { text = "Clicou" },
        content = {
            Label(
                modifier = Modifier.padding(start = 16.dp),
                text = text,
                fontSize = 14.sp,
            )
        }
    )
}
