package com.example.mynutrition.presentation.nutrition.info.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynutrition.domain.model.UserInfo
import com.example.mynutrition.domain.model.enums.ActivityLevel
import com.example.mynutrition.domain.model.enums.Sex
import com.example.mynutrition.domain.usecase.GetUserInfoUseCase
import com.example.mynutrition.domain.usecase.SaveUserInfoUseCase
import com.example.mynutrition.presentation.nutrition.info.state.NutritionInfoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NutritionInfoViewModel(
    private val saveUserInfo: SaveUserInfoUseCase,
    private val getUserInfo: GetUserInfoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NutritionInfoState())
    val uiState: StateFlow<NutritionInfoState> = _uiState

    init { load() }

    private fun load() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isLoading = true)
        try {
            val info = getUserInfo()
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

    fun onNameChanged(v: String) { _uiState.value = _uiState.value.copy(name = v) }
    fun onAgeChanged(v: String) { _uiState.value = _uiState.value.copy(age = v) }
    fun onSexChanged(v: Sex) { _uiState.value = _uiState.value.copy(sex = v) }
    fun onHeightChanged(v: String) { _uiState.value = _uiState.value.copy(height = v) }
    fun onWeightChanged(v: String) { _uiState.value = _uiState.value.copy(weight = v) }
    fun onActivityChanged(v: ActivityLevel) { _uiState.value = _uiState.value.copy(activity = v) }

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
            saveUserInfo(userInfo)
            _uiState.value = state.copy(isLoading = false, success = true)
        } catch (t: Throwable) {
            _uiState.value = state.copy(isLoading = false, error = t.message)
        }
    }
}