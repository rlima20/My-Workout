package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R
import com.example.myworkout.enums.Sort

@Composable
fun DropdownMenuSort(
    enabled: Boolean,
    onItemClick: (Sort) -> Unit
) {
    val items = listOf(Sort.A_TO_Z, Sort.Z_TO_A)
    var item by remember { mutableStateOf(items.first()) }

    var expanded by remember { mutableStateOf(false) }

    if (enabled) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = item.name,
                color = colorResource(R.color.text_color),
                fontSize = 14.sp
            )
            IconButton(
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 16.dp),
                onClick = {
                    if (enabled) {
                        expanded = !expanded
                    }
                }
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.baseline_filter_alt_24),
                    contentDescription = null,
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach {
                val item = it
                DropdownMenuItem(
                    text = {
                        Text(
                            modifier = Modifier.padding(start = 32.dp, end = 32.dp),
                            text = item.name,
                            color = colorResource(R.color.text_color)
                        )
                    },
                    onClick = {
                        onItemClick(item)
                        expanded = !expanded
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun SortComponentPreview() {
    DropdownMenuSort(
        enabled = true,
        onItemClick = { }
    )
}