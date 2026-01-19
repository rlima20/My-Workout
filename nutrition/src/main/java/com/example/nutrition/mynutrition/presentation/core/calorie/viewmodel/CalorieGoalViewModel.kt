package com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrition.mynutrition.domain.mappers.toEntity
import com.example.nutrition.mynutrition.domain.model.calorie.CalorieGoal
import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType
import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.usecase.CalculateCalorieGoalUseCase
import com.example.nutrition.mynutrition.domain.usecase.CalculateMacrosUseCase
import com.example.nutrition.mynutrition.domain.usecase.CalculateTmbUseCase
import com.example.nutrition.mynutrition.domain.usecase.GetCalorieGoalWithMacrosUseCase
import com.example.nutrition.mynutrition.domain.usecase.SaveCalorieGoalUseCase
import com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.state.CalorieGoalState
import kotlinx.coroutines.Dispatchers
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
    private val calculateMacrosUseCase: CalculateMacrosUseCase,
    private val saveCalorieGoalUseCase: SaveCalorieGoalUseCase,
    private val getCalorieGoalWithMacrosUseCase: GetCalorieGoalWithMacrosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _calorieGoalState = MutableStateFlow(CalorieGoalState())
    val calorieGoalState: StateFlow<CalorieGoalState> = _calorieGoalState

    fun calculateCalorieGoal(userInfo: UserInfo?) =
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingState()
            runCatching {
                val validUserInfo = userInfo ?: error("UserInfo inválido")
                calculateAndPersistCalorieGoal(validUserInfo)
                loadCalorieGoalState()
            }.onSuccess {
                _uiState.value = UiState.CalculateCalorieGoalSuccess
            }.onFailure { throwable ->
                setErrorState(throwable.message.orEmpty())
            }
        }

    private suspend fun loadCalorieGoalState() {
        val calorieGoal = getCalorieGoalWithMacrosUseCase.getCalorieGoalWithMacros()

        _calorieGoalState.value = CalorieGoalState(
            tmb = calorieGoal.first().calorieGoal.tmb,
            calorieGoal = calorieGoal.first().calorieGoal.calorieGoal,
            macros = calorieGoal.first().macros
        )
    }

    private suspend fun calculateAndPersistCalorieGoal(userInfo: UserInfo) {
        val tmb = calculateTmb(userInfo)
        val calorieGoal = calculateCalorieGoal(userInfo, tmb)
        val macros = calculateMacros(calorieGoal, userInfo.goalType)

        saveCalorieGoalUseCase.saveCalorieGoal(
            tmb = tmb,
            calorieGoal = calorieGoal,
            macros = macros.toEntity()
        )
    }

    private fun calculateTmb(userInfo: UserInfo): Int = tmbUseCase.calculateTmb(userInfo)

    private fun calculateCalorieGoal(
        userInfo: UserInfo,
        tmb: Int
    ): Int =
        calorieGoalUseCase.calculateCalorieGoal(
            tmb = tmb,
            activityLevel = userInfo.activityLevel,
            goalType = userInfo.goalType
        )

    private fun calculateMacros(
        totalKcal: Int,
        goalType: CalorieGoalType
    ) =
        calculateMacrosUseCase.calculateMacros(
            totalKcal = totalKcal,
            goalType = goalType
        )

//    private fun saveCalorieGoal(calorieGoal: CalorieGoal) {
//        saveCalorieGoalUseCase.saveCalorieGoal(calorieGoal)
//    }

//    fun calculateCalorieGoalOld(userInfo: UserInfo?) = viewModelScope.launch {
//        setLoadingState()
//        try {
//            userInfo?.let {
//                // Calcula taxa de metabolismo basal
//                val tmb = tmbUseCase.calculateTmb(it)
//
//                // Calcula objetivo de calorias
//                val goalKcal = calorieGoalUseCase.calculateCalorieGoal(
//                    tmb = tmb,
//                    activityLevel = it.activityLevel,
//                    goalType = it.goalType
//                )
//
//                // Calcula os macro nutrientes
//                val macroResult = calculateMacrosUseCase.calculateMacros(
//                    totalKcal = goalKcal,
//                    goalType = it.goalType
//                )
//
//                // Persiste no banco de dados
//                saveCalorieGoalUseCase.saveCalorieGoal(
//                    CalorieGoal(
//                        tmb = tmb,
//                        calorieGoal = goalKcal,
//                    )
//                )
//
//                // Salva estado da tela com o que está no banco
//                getCalorieGoalUseCase.getCalorieGoal().run {
//                    _calorieGoalState.value =
//                        CalorieGoalState(
//                            tmb = this.tmb,
//                            calorieGoal = this.calorieGoal,
//                            macros = this.macros,
//                        )
//                }
//
//                _uiState.value = UiState.CalculateCalorieGoalSuccess
//            }
//        } catch (t: Throwable) {
//            setErrorState(t.message.toString())
//        }
//    }

    private fun setLoadingState() {
        _uiState.value = UiState.Loading
    }

    private fun setErrorState(message: String) {
        _uiState.value = UiState.Error(message)
    }
}