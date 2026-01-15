package com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.viewmodelfake

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.CalorieGoalViewModel

@RequiresApi(Build.VERSION_CODES.O)
class CalorieGoalViewModelFake : CalorieGoalViewModel(
    tmbUseCase = CalculateTmbUseCaseFake(),
    calorieGoalUseCase = CalculateCalorieGoalUseCaseFake(),
    calculateMacrosUseCase = CalculateMacrosUseCaseFake()
)