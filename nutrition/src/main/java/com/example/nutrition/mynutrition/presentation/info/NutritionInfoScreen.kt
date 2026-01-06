package com.example.nutrition.mynutrition.presentation.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nutrition.R
import com.example.nutrition.mynutrition.constants.getNutritionInfoState
import com.example.nutrition.mynutrition.presentation.info.components.ActivityLevelDropdown
import com.example.nutrition.mynutrition.presentation.info.components.SexSelector
import com.example.nutrition.mynutrition.presentation.info.state.NutritionInfoState
import com.example.nutrition.mynutrition.utils.colors
import com.example.nutrition.mynutrition.utils.setButtonColor
import com.example.nutrition.mynutrition.utils.setButtonTextColor

@Composable
fun NutritionInfoScreen(
    state: NutritionInfoState,
    onSave: (nutritionInfoState: NutritionInfoState) -> Unit
) {
    var name by remember { mutableStateOf(state.name) }
    LaunchedEffect(state.name) { name = state.name }

    var age by remember { mutableStateOf(state.age) }
    LaunchedEffect(state.age) { age = state.age }

    var height by remember { mutableStateOf(state.height) }
    LaunchedEffect(state.height) { height = state.height }

    var weight by remember { mutableStateOf(state.weight) }
    LaunchedEffect(state.weight) { weight = state.weight }

    var sex by remember { mutableStateOf(state.sex) }
    LaunchedEffect(state.sex) { sex = state.sex }

    var activity by remember { mutableStateOf(state.activity) }
    LaunchedEffect(state.activity) { activity = state.activity }

    var buttonEnabled by remember { mutableStateOf( false ) }
    LaunchedEffect(name, age, weight, height) {
        buttonEnabled =
            name.isNotEmpty() && age.isNotEmpty() &&
            height.isNotEmpty() && weight.isNotEmpty()
    }

    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            singleLine = true,
            label = { Text(stringResource(R.string.user_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = colors()
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            singleLine = true,
            label = { Text(stringResource(R.string.user_age)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = colors()
        )

        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            singleLine = true,
            label = { Text(stringResource(R.string.user_height)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = colors()
        )

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            singleLine = true,
            label = { Text(stringResource(R.string.user_weight)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = colors()
        )

        SexSelector(
            selected = sex,
            onSelect = { sex = it },
            modifier = Modifier.padding(top = 8.dp)
        )

        ActivityLevelDropdown(
            selected = activity,
            onSelect = { activity = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )


        Button(
            onClick = {
                /* todo */
                // Atualiza o state (UserInfoState)
                // Faz um onSave(). O estado já vai estar atualizado.
            },
            enabled = buttonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = setButtonColor(buttonEnabled)
        ) {
            Text(
                text = stringResource(R.string.save),
                color = setButtonTextColor(buttonEnabled)
            )
        }
    }
}

private fun setButtonEnabled(isEnabled: Boolean) {

}

@Preview(showBackground = true)
@Composable
fun NutritionInfoScreenPreview() {
    MaterialTheme {
        NutritionInfoScreen(
            state = getNutritionInfoState(),
            onSave = {}
        )
    }
}