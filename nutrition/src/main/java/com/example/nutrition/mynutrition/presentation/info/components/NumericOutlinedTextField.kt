package com.example.nutrition.mynutrition.presentation.info.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun NumericOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    allowDecimal: Boolean = false,
    shape: Shape = RoundedCornerShape(12.dp),
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            val filteredValue = if (allowDecimal) {
                newValue
                    .replace(",", ".")
                    .filterIndexed { index, char ->
                        char.isDigit() || (char == '.' && newValue.indexOf('.') == index)
                    }
            } else {
                newValue.filter { it.isDigit() }
            }

            onValueChange(filteredValue)
        },
        singleLine = true,
        label = { Text(label) },
        modifier = modifier,
        shape = shape,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (allowDecimal)
                KeyboardType.Decimal
            else
                KeyboardType.Number
        ),
        colors = colors
    )
}