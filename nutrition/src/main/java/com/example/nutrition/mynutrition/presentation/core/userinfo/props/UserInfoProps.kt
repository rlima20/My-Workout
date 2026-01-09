package com.example.nutrition.mynutrition.presentation.core.userinfo.props

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nutrition.mynutrition.preferences.NutritionPrefs
import com.example.nutrition.mynutrition.presentation.core.baseprops.BaseProps
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.state.UserInfoState
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.UserInfoViewModel

data class UserInfoProps(
    val userInfo: UserInfoState,
    override val navController: NavHostController,
    override val prefs: NutritionPrefs,
) : BaseProps

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun getUserInfoProps(userInfoViewModel: UserInfoViewModel): UserInfoProps {
    val navController = rememberNavController()
    val nutritionPrefs = NutritionPrefs()
    val userInfoState by userInfoViewModel.userInfoState.collectAsState()

    return UserInfoProps(
        navController = navController,
        prefs = nutritionPrefs,
        userInfo = userInfoState,
    )
}