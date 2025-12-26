package com.example.mynutrition.presentation.nutrition.calorie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynutrition.domain.model.enums.CalorieGoalType
import com.example.mynutrition.domain.usecase.CalculateCalorieGoalUseCase
import com.example.mynutrition.domain.usecase.CalculateMacrosUseCase
import com.example.mynutrition.domain.usecase.CalculateTmbUseCase
import com.example.mynutrition.domain.usecase.GetUserInfoUseCase
import com.example.mynutrition.presentation.nutrition.calorie.state.CalorieGoalState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CalorieGoalViewModel(
    private val getUserInfo: GetUserInfoUseCase,
    private val tmbUseCase: CalculateTmbUseCase,
    private val calorieGoalUseCase: CalculateCalorieGoalUseCase,
    private val macrosUseCase: CalculateMacrosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalorieGoalState())
    val uiState: StateFlow<CalorieGoalState> = _uiState

    init {
        load(CalorieGoalType.MAINTAIN)
    }

    fun load(goal: CalorieGoalType) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isLoading = true)
        try {
            val info = getUserInfo() ?: run {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Preencha suas informações primeiro"
                )
                return@launch
            }

            val tmb = tmbUseCase(info)
            val goalKcal = calorieGoalUseCase(tmb, info.activityLevel, goal)
            val macros = macrosUseCase(goalKcal)

            _uiState.value = CalorieGoalState(
                goalType = goal,
                tmb = tmb,
                calorieGoal = goalKcal,
                macros = macros,
                isLoading = false
            )
        } catch (t: Throwable) {
            _uiState.value = _uiState.value.copy(isLoading = false, error = t.message)
        }
    }

    fun setGoal(goal: CalorieGoalType) = load(goal)
}