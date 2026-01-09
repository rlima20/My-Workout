package com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.viewmodelfake

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.UserInfoViewModel

@RequiresApi(Build.VERSION_CODES.O)
class UserInfoViewModelFake : UserInfoViewModel(
    saveUserInfoUseCase = SaveUserInfoUseCaseFake(),
    getUserInfoUseCase = GetUserInfoUseCaseFake()
)