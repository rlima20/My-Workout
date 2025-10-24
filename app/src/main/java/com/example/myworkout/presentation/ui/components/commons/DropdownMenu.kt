package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.Constants
import com.example.myworkout.R
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.extensions.toPortugueseString
import com.example.myworkout.utils.Utils

@Composable
fun DropdownItem(
    modifier: Modifier = Modifier,
    items: List<Pair<DayOfWeek, Boolean>>,
    text: String?,
    onItemClick: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    text?.let {
        Card(
            modifier = modifier.height(50.dp),
            colors = Utils().buttonSectionCardsColors(),
            shape = CardDefaults.elevatedShape,
            border = BorderStroke(1.dp, colorResource(R.color.border_color)),
            elevation = CardDefaults.cardElevation()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = text,
                    color = colorResource(R.color.text_color),
                    fontSize = 14.sp
                )
                IconButton(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 16.dp),
                    onClick = {
                        if (text.isNotBlank()) {
                            expanded = !expanded
                        }
                    }
                ) {
                    Image(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(R.drawable.baseline_arrow_drop_down_24),
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
                        enabled = !item.second,
                        text = {
                            Text(
                                modifier = Modifier.padding(start = 32.dp, end = 32.dp),
                                text = item.first.toPortugueseString(),
                                textDecoration = if (item.second) TextDecoration.LineThrough else null,
                                color = colorResource(R.color.text_color)
                            )
                        },
                        onClick = {
                            onItemClick(item.first.toPortugueseString())
                            expanded = !expanded
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun DropdownItemPreview() {
    val constants = Constants()
    DropdownItem(
        items = constants.daysOfWeek,
        text = "",
        onItemClick = {}
    )
}