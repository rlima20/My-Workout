package com.example.mynutrition.presentation.nutrition.calorie.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynutrition.domain.model.enums.CalorieGoalType
import com.example.mynutrition.domain.usecase.CalculateCalorieGoalUseCase
import com.example.mynutrition.domain.usecase.CalculateMacrosUseCase
import com.example.mynutrition.domain.usecase.CalculateTmbUseCase
import com.example.mynutrition.domain.usecase.GetUserInfoUseCase
import com.example.mynutrition.presentation.nutrition.calorie.state.CalorieGoalState
import kotlinx.coroutines.launch

class CalorieGoalViewModel(
    private val getUserInfo: GetUserInfoUseCase,
    private val tmbUseCase: CalculateTmbUseCase,
    private val calorieGoalUseCase: CalculateCalorieGoalUseCase,
    private val macrosUseCase: CalculateMacrosUseCase
) : ViewModel() {

    var state by mutableStateOf(CalorieGoalState())
        private set

    init {
        refresh()
    }

    fun refresh(goal: CalorieGoalType = state.goalType) = viewModelScope.launch {
        val info = getUserInfo() ?: return@launch

        val tmb = tmbUseCase(info)
        val goalKcal = calorieGoalUseCase(tmb, info.activityLevel, goal)
        val macros = macrosUseCase(goalKcal)

        state = CalorieGoalState(
            goalType = goal,
            tmb = tmb,
            calorieGoal = goalKcal,
            macros = macros,
            isLoading = false
        )
    }

    fun setGoal(goal: CalorieGoalType) = refresh(goal)
}