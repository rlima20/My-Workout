package com.example.nutrition.mynutrition.presentation.calorie.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType

@Composable
fun GoalToggle(
    selected: CalorieGoalType, onSelect: (CalorieGoalType) -> Unit, modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        CalorieGoalType.entries.forEach { goal ->
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
