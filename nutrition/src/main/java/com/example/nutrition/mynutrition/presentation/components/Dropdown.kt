package com.example.nutrition.mynutrition.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nutrition.R
import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType
import com.example.nutrition.mynutrition.utils.UiFieldDefaults
import com.example.nutrition.mynutrition.utils.textGoalType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> Dropdown(
    selected: T,
    options: List<T>,
    onSelect: (T) -> Unit,
    label: String,
    textMapper: @Composable (T) -> String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth(),
    ) {
        TextField(
            readOnly = true,
            value = textMapper(selected),
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = UiFieldDefaults.colors(),
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.text_field_background_color2))
                .menuAnchor(),
            shape = UiFieldDefaults.Shape
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(textMapper(item)) },
                    onClick = {
                        onSelect(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun DropdownPreview() {
    Dropdown(
        selected = CalorieGoalType.MAINTAIN,
        onSelect = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        options = CalorieGoalType.entries,
        label = stringResource(R.string.goal_type),
        textMapper = { textGoalType(it) }
    )
}
