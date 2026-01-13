package com.example.nutrition.mynutrition.presentation.core.userinfo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
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
import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.presentation.components.ActivityLevelDropdown
import com.example.nutrition.mynutrition.presentation.components.NumericOutlinedTextField
import com.example.nutrition.mynutrition.presentation.components.SexSelector
import com.example.nutrition.mynutrition.presentation.core.userinfo.props.UserInfoProps
import com.example.nutrition.mynutrition.presentation.core.userinfo.props.getUserInfoProps
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.UiState
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.UserInfoViewModel
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.viewmodelfake.UserInfoViewModelFake
import com.example.nutrition.mynutrition.utils.colors
import com.example.nutrition.mynutrition.utils.setButtonColor
import com.example.nutrition.mynutrition.utils.setButtonTextColor

@Composable
fun UserInfoScreen(
    userInfoProps: UserInfoProps,
    nutritionInfoViewModel: UserInfoViewModel,
    onUserInfoSaved: (userInfo: UserInfo) -> Unit
) {
    nutritionInfoViewModel.fetchUserinfo()
    with(userInfoProps) {

        var name by remember { mutableStateOf(userInfo.name) }
        LaunchedEffect(userInfo.name) {
            name = userInfo.name
        }

        var age by remember { mutableStateOf(userInfo.age) }
        LaunchedEffect(userInfo.age) {
            age = userInfo.age
        }

        var height by remember { mutableStateOf(userInfo.height) }
        LaunchedEffect(userInfo.height) {
            height = userInfo.height
        }

        var weight by remember { mutableStateOf(userInfo.weight) }
        LaunchedEffect(userInfo.weight) {
            weight = userInfo.weight
        }

        var sex by remember { mutableStateOf(userInfo.sex) }
        LaunchedEffect(userInfo.sex) {
            sex = userInfo.sex
        }

        var activity by remember { mutableStateOf(userInfo.activity) }
        LaunchedEffect(userInfo.activity) {
            activity = userInfo.activity
        }


        var buttonEnabled by remember { mutableStateOf(false) }
        LaunchedEffect(name, age, weight, height) {
            buttonEnabled =
                name.isNotEmpty() &&
                        age.isNotEmpty() &&
                        height.isNotEmpty() &&
                        weight.isNotEmpty()
        }

        when (uiState) {
            is UiState.SuccessFetch -> {
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

            is UiState.Error -> {
                // todo - TelaDeErro
            }

            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            is UiState.SuccessSave -> {
                onUserInfoSaved(uiState.userInfo)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun NutritionInfoScreenPreview() {
    val viewModel = UserInfoViewModelFake()
    MaterialTheme {
        UserInfoScreen(
            userInfoProps = getUserInfoProps(viewModel),
            nutritionInfoViewModel = viewModel,
            onUserInfoSaved = {}
        )
    }
}