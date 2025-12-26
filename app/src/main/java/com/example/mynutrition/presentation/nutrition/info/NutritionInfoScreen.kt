package com.example.mynutrition.presentation.nutrition.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynutrition.domain.model.enums.ActivityLevel
import com.example.mynutrition.domain.model.enums.Sex
import com.example.mynutrition.presentation.nutrition.components.ActivityLevelDropdown
import com.example.mynutrition.presentation.nutrition.components.SexSelector
import com.example.mynutrition.presentation.nutrition.info.state.NutritionInfoState

@Composable
fun NutritionInfoScreen(
    state: NutritionInfoState,
    onNameChanged: (String) -> Unit,
    onAgeChanged: (String) -> Unit,
    onSexChanged: (Sex) -> Unit,
    onHeightChanged: (String) -> Unit,
    onWeightChanged: (String) -> Unit,
    onActivityChanged: (ActivityLevel) -> Unit,
    onSave: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = state.name,
            onValueChange = onNameChanged,
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.age,
            onValueChange = onAgeChanged,
            label = { Text("Idade") },
            modifier = Modifier.fillMaxWidth()
        )
        SexSelector(
            selected = state.sex,
            onSelect = onSexChanged,
            modifier = Modifier.padding(top = 8.dp)
        )
        OutlinedTextField(
            value = state.height,
            onValueChange = onHeightChanged,
            label = { Text("Altura (cm)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.weight,
            onValueChange = onWeightChanged,
            label = { Text("Peso (kg)") },
            modifier = Modifier.fillMaxWidth()
        )
        ActivityLevelDropdown(
            selected = state.activity,
            onSelect = onActivityChanged,
            modifier = Modifier.padding(top = 8.dp)
        )
        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            enabled = !state.isLoading
        ) {
            Text("Salvar")
        }
        if (state.success) {
            Text("Salvo com sucesso!", modifier = Modifier.padding(top = 8.dp))
        }
        state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
    }
}


@Preview(showBackground = true)
@Composable
fun NutritionInfoScreenPreview() {
    MaterialTheme {
        NutritionInfoScreen(
            state = NutritionInfoState(
                name = "Rafael",
                age = "30",
                sex = Sex.MALE,
                height = "178",
                weight = "82",
                activity = ActivityLevel.MODERATE,
                isLoading = false,
                success = false
            ),
            onNameChanged = {},
            onAgeChanged = {},
            onSexChanged = {},
            onHeightChanged = {},
            onWeightChanged = {},
            onActivityChanged = {},
            onSave = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NutritionInfoScreenSavedPreview() {
    MaterialTheme {
        NutritionInfoScreen(
            state = NutritionInfoState(
                name = "Rafael",
                age = "30",
                sex = Sex.MALE,
                height = "178",
                weight = "82",
                activity = ActivityLevel.MODERATE,
                isLoading = false,
                success = true
            ),
            onNameChanged = {},
            onAgeChanged = {},
            onSexChanged = {},
            onHeightChanged = {},
            onWeightChanged = {},
            onActivityChanged = {},
            onSave = {}
        )
    }
}