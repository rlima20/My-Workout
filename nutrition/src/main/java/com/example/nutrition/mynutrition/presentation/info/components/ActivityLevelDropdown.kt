package com.example.nutrition.mynutrition.presentation.info.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.nutrition.R
import com.example.nutrition.mynutrition.domain.model.enums.ActivityLevel
import com.example.nutrition.mynutrition.utils.UiFieldDefaults
import com.example.nutrition.mynutrition.utils.textFieldText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityLevelDropdown(
    selected: ActivityLevel,
    onSelect: (activityLevel: ActivityLevel) -> Unit,
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
            value = textFieldText(selected),
            onValueChange = {},
            label = { Text(stringResource(R.string.user_activity)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = UiFieldDefaults.colors(),
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.text_field_background_color2))
                .menuAnchor(),
            shape = UiFieldDefaults.Shape
        )

        ExposedDropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {
            ActivityLevel.entries.forEach { level ->
                DropdownMenuItem(
                    text = {
                        Text(textFieldText(level))
                    }, onClick = {
                        onSelect(level)
                        expanded = false
                    })
            }
        }
    }
}
