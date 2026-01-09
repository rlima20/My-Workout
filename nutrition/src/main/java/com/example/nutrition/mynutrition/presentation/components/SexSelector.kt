package com.example.nutrition.mynutrition.presentation.components

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nutrition.mynutrition.domain.model.enums.Sex
import com.example.nutrition.mynutrition.utils.selectedChipColor
import com.example.nutrition.mynutrition.utils.setColor
import com.example.nutrition.mynutrition.utils.setText

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SexSelector(
    selected: Sex,
    onSelect: (sex: Sex) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Sex.entries.forEach { sex ->
            val isSelected = sex == selected
            FilterChip(
                selected = isSelected,
                onClick = { onSelect(sex) },
                colors = selectedChipColor()
            ) {
                Text(
                    text = setText(sex),
                    color = setColor(isSelected)
                )
            }
        }
    }
}

@Preview
@Composable
private fun SexSelectorPreview(){
    SexSelector(
        selected = Sex.MALE,
        onSelect = {}
    )
}