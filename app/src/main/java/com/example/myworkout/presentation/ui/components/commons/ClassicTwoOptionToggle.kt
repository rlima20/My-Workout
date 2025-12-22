package com.example.myworkout.presentation.ui.components.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
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
    onSelected: (String) -> Unit,
    fontSize: TextUnit = 12.sp,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = Color.Gray,
    roundBorderSelectedColor: Color = colorResource(R.color.button_color),
    roundBorderUnselectedColor: Color = Color.Gray,
    roundBackgroundSelectedColor: Color = colorResource(R.color.button_color),
    roundBackgroundUnselectedColor: Color = Color.Transparent
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ToggleItem(
            label = optionA,
            selected = selectedSort == optionA,
            fontSize = fontSize,
            textStyle = textStyle,
            selectedColor = selectedColor,
            unselectedColor = unselectedColor,
            roundBorderSelectedColor = roundBorderSelectedColor,
            roundBorderUnselectedColor = roundBorderUnselectedColor,
            roundBackgroundSelectedColor = roundBackgroundSelectedColor,
            roundBackgroundUnselectedColor = roundBackgroundUnselectedColor,
            onClick = { onSelected(optionA) }
        )

        ToggleItem(
            label = optionB,
            selected = selectedSort == optionB,
            fontSize = fontSize,
            textStyle = textStyle,
            selectedColor = selectedColor,
            unselectedColor = unselectedColor,
            roundBorderSelectedColor = roundBorderSelectedColor,
            roundBorderUnselectedColor = roundBorderUnselectedColor,
            roundBackgroundSelectedColor = roundBackgroundSelectedColor,
            roundBackgroundUnselectedColor = roundBackgroundUnselectedColor,
            onClick = { onSelected(optionB) }
        )
    }
}

@Composable
@Preview
private fun ClassicTwoOptionTogglePreview() {
    TwoOptionToggle(
        selectedSort = Sort().sortAZ,
        optionA = stringResource(R.string.male),
        optionB = stringResource(R.string.female),
        onSelected = { }
    )
}