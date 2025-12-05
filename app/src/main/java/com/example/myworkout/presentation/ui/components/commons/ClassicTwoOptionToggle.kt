package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R
import com.example.myworkout.enums.Sort

@Composable
fun TwoOptionToggle(
    optionA: String,
    optionB: String,
    selectedSort: String,
    modifier: Modifier = Modifier,
    onSelected: (String) -> Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ToggleItem(
            label = optionA,
            selected = selectedSort == optionA,
            onClick = { onSelected(optionA) }
        )

        ToggleItem(
            label = optionB,
            selected = selectedSort == optionB,
            onClick = { onSelected(optionB) }
        )
    }
}

@Composable
private fun ToggleItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .border(
                    width = 2.dp,
                    color = if (selected) colorResource(R.color.button_color)
                    else Color.Gray,
                    shape = CircleShape
                )
                .background(
                    color = if (selected) colorResource(R.color.button_color)
                    else Color.Transparent,
                    shape = CircleShape
                )
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            fontSize = 12.sp,
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
        )
    }
}

@Composable
@Preview
private fun ClassicTwoOptionTogglePreview() {
    TwoOptionToggle(
        selectedSort = Sort().sortAZ,
        optionA = "Masculino",
        optionB = "Feminino",
        onSelected = { selected ->
            println("Selecionado: $selected")
        }
    )
}