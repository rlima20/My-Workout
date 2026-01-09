package com.example.nutrition.mynutrition.presentation.core.userinfo

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.nutrition.mynutrition.constants.getUserInfoState
import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.presentation.components.ActivityLevelDropdown
import com.example.nutrition.mynutrition.presentation.components.NumericOutlinedTextField
import com.example.nutrition.mynutrition.presentation.components.SexSelector
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.state.UserInfoState
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.UserInfoViewModel
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.viewmodelfake.UserInfoViewModelFake
import com.example.nutrition.mynutrition.utils.colors
import com.example.nutrition.mynutrition.utils.setButtonColor
import com.example.nutrition.mynutrition.utils.setButtonTextColor

@Composable
fun UserInfoScreen(
    userInfoState: UserInfoState,
    nutritionInfoViewModel: UserInfoViewModel,
    onUserInfoSaved: () -> Unit
) {
    nutritionInfoViewModel.fetchUserinfo()

    var name by remember { mutableStateOf(userInfoState.name) }
    LaunchedEffect(userInfoState.name) { name = userInfoState.name }

    var age by remember { mutableStateOf(userInfoState.age) }
    LaunchedEffect(userInfoState.age) { age = userInfoState.age }

    var height by remember { mutableStateOf(userInfoState.height) }
    LaunchedEffect(userInfoState.height) { height = userInfoState.height }

    var weight by remember { mutableStateOf(userInfoState.weight) }
    LaunchedEffect(userInfoState.weight) { weight = userInfoState.weight }

    var sex by remember { mutableStateOf(userInfoState.sex) }
    LaunchedEffect(userInfoState.sex) { sex = userInfoState.sex }

    var activity by remember { mutableStateOf(userInfoState.activity) }
    LaunchedEffect(userInfoState.activity) { activity = userInfoState.activity }

    var buttonEnabled by remember { mutableStateOf(false) }
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

        NumericOutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = stringResource(R.string.user_weight),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            allowDecimal = true,
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
                nutritionInfoViewModel.onSave(
                    UserInfo(
                        name = name,
                        age = age.toInt(),
                        sex = sex,
                        heightCm = height.toInt(),
                        weightKg = weight.toFloat(),
                        activityLevel = activity
                    )
                )
                onUserInfoSaved()
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun NutritionInfoScreenPreview() {
    MaterialTheme {
        UserInfoScreen(
            userInfoState = getUserInfoState(),
            nutritionInfoViewModel = UserInfoViewModelFake(),
            onUserInfoSaved = {}
        )
    }
}