package com.example.nutrition.mynutrition.presentation.info.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.usecase.GetUserInfoUseCase
import com.example.nutrition.mynutrition.domain.usecase.SaveUserInfoUseCase
import com.example.nutrition.mynutrition.presentation.info.state.NutritionInfoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NutritionInfoViewModel(
    private val saveUserInfoUseCase: SaveUserInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NutritionInfoState())
    val uiState: StateFlow<NutritionInfoState> = _uiState

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isLoading = true)
        try {
            val info = getUserInfoUseCase.getUserInfo()
            info?.let {
                _uiState.value = _uiState.value.copy(
                    name = it.name,
                    age = it.age.toString(),
                    sex = it.sex,
                    height = it.heightCm.toString(),
                    weight = it.weightKg.toString(),
                    activity = it.activityLevel,
                    isLoading = false
                )
            } ?: run {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        } catch (t: Throwable) {
            _uiState.value = _uiState.value.copy(isLoading = false, error = t.message)
        }
    }

    fun updateUiState(state: NutritionInfoState) {
        _uiState.value = state
    }

    fun onSave() = viewModelScope.launch {
        val state = _uiState.value
        _uiState.value = state.copy(isLoading = true, error = null)
        try {
            val userInfo = UserInfo(
                name = state.name,
                age = state.age.toIntOrNull() ?: 0,
                sex = state.sex,
                heightCm = state.height.toIntOrNull() ?: 0,
                weightKg = state.weight.toFloatOrNull() ?: 0f,
                activityLevel = state.activity
            )
            saveUserInfoUseCase.saveUser(userInfo)
            _uiState.value = state.copy(isLoading = false, success = true)
        } catch (t: Throwable) {
            _uiState.value = state.copy(isLoading = false, error = t.message)
        }
    }
}