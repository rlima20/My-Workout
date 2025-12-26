package com.example.mynutrition.presentation.nutrition.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mynutrition.domain.model.enums.ActivityLevel
import com.example.mynutrition.domain.model.enums.CalorieGoalType
import com.example.mynutrition.domain.model.enums.Sex

// SexSelector (horizontal buttons)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SexSelector(
    selected: Sex,
    onSelect: (Sex) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Sex.entries.forEach { sex ->
            val isSelected = sex == selected
            FilterChip(
                selected = isSelected,
                onClick = { onSelect(sex) }
            ) {
                Text(text = if (sex == Sex.MALE) "Masculino" else "Feminino")
            }
        }
    }
}

// ActivityLevelDropdown
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityLevelDropdown(
    selected: ActivityLevel,
    onSelect: (ActivityLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded = false
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth()
    ) {
        TextField(
            readOnly = true,
            value = when (selected) {
                ActivityLevel.SEDENTARY -> "Sedentário"
                ActivityLevel.LIGHT -> "Leve"
                ActivityLevel.MODERATE -> "Moderado"
                ActivityLevel.HIGH -> "Alto"
                ActivityLevel.EXTREME -> "Extremo"
            },
            onValueChange = {},
            label = { Text("Nível de atividade") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            ActivityLevel.values().forEach { level ->
                DropdownMenuItem(
                    text = {
                        Text(
                            when (level) {
                                ActivityLevel.SEDENTARY -> "Sedentário"
                                ActivityLevel.LIGHT -> "Leve"
                                ActivityLevel.MODERATE -> "Moderado"
                                ActivityLevel.HIGH -> "Alto"
                                ActivityLevel.EXTREME -> "Extremo"
                            }
                        )
                    },
                    onClick = {
                        onSelect(level)
                        expanded = false
                    }
                )
            }
        }
    }
}

// GoalToggle (3 options)
@Composable
fun GoalToggle(
    selected: CalorieGoalType,
    onSelect: (CalorieGoalType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        CalorieGoalType.values().forEach { goal ->
            val label = when (goal) {
                CalorieGoalType.GAIN -> "Ganhar"
                CalorieGoalType.MAINTAIN -> "Manutenção"
                CalorieGoalType.LOSE -> "Perder"
            }
            val isSelected = goal == selected
            Button(
                onClick = { onSelect(goal) },
                colors = if (isSelected) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(label)
            }
        }
    }
}
