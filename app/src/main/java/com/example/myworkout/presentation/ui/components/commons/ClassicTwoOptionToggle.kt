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
import androidx.compose.ui.Alignment
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
private fun ToggleItem(
    label: String,
    selected: Boolean,
    fontSize: TextUnit,
    textStyle: TextStyle,
    selectedColor: Color,
    unselectedColor: Color,
    roundBorderSelectedColor: Color,
    roundBorderUnselectedColor: Color,
    roundBackgroundSelectedColor: Color,
    roundBackgroundUnselectedColor: Color,
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
                    width = 1.dp,
                    color = setBorderColor(
                        selected,
                        roundBorderSelectedColor,
                        roundBorderUnselectedColor
                    ),
                    shape = CircleShape
                )
                .background(
                    color =
                        setBackGroundColor(
                            selected,
                            roundBackgroundSelectedColor,
                            roundBackgroundUnselectedColor
                        ),
                    shape = CircleShape
                )
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            fontSize = fontSize,
            text = label,
            style = textStyle,
            color = setColor(selected, selectedColor, unselectedColor)
        )
    }
}

@Composable
private fun setColor(
    selected: Boolean,
    selectedColor: Color,
    unselectedColor: Color
): Color = setBorderColor(
    selected,
    selectedColor,
    unselectedColor
)

@Composable
private fun setBorderColor(
    selected: Boolean,
    roundBorderSelectedColor: Color,
    roundBorderUnselectedColor: Color
): Color = setBackGroundColor(
    selected,
    roundBorderSelectedColor,
    roundBorderUnselectedColor
)

@Composable
private fun setBackGroundColor(
    selected: Boolean,
    roundBackgroundSelectedColor: Color,
    roundBackgroundUnselectedColor: Color
): Color = if (selected) roundBackgroundSelectedColor
else roundBackgroundUnselectedColor

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