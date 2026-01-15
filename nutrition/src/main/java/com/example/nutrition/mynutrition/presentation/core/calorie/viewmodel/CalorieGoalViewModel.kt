package com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.usecase.CalculateCalorieGoalUseCase
import com.example.nutrition.mynutrition.domain.usecase.CalculateMacrosUseCase
import com.example.nutrition.mynutrition.domain.usecase.CalculateTmbUseCase
import com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.state.CalorieGoalState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Loading : UiState()
    data class Error(val message: String) : UiState()
    object SuccessFetch : UiState()
    object CalculateCalorieGoalSuccess : UiState()
}

open class CalorieGoalViewModel(
    private val tmbUseCase: CalculateTmbUseCase,
    private val calorieGoalUseCase: CalculateCalorieGoalUseCase,
    private val calculateMacrosUseCase: CalculateMacrosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _calorieGoalState = MutableStateFlow(CalorieGoalState())
    val calorieGoalState: StateFlow<CalorieGoalState> = _calorieGoalState

    fun calculateCalorieGoal(userInfo: UserInfo?) = viewModelScope.launch {
        setLoadingState()
        try {
            userInfo?.let {
                // Calcula taxa de metabolismo basal
                val tmb = tmbUseCase.calculateTmb(it)

                // Calcula objetivo de calorias
                val goalKcal = calorieGoalUseCase.calculateCalorieGoal(
                    tmb = tmb,
                    activityLevel = it.activityLevel,
                    goalType = it.goalType
                )

                // Calcula os macro nutrientes
                val macroResult = calculateMacrosUseCase.calculateMacros(
                    totalKcal = goalKcal,
                    goalType = it.goalType
                )

                // Salva estado da tela
                _calorieGoalState.value = CalorieGoalState(
                    tmb = tmb,
                    calorieGoal = goalKcal,
                    macros = macroResult,
                )

                // Todo - Persistir no banco de dados
                // CalorieGoalEntity
                _uiState.value = UiState.CalculateCalorieGoalSuccess
            }
        } catch (t: Throwable) {
            setErrorState(t.message.toString())
        }
    }

    private fun setLoadingState() {
        _uiState.value = UiState.Loading
    }

    private fun setErrorState(message: String) {
        _uiState.value = UiState.Error(message)
    }
}