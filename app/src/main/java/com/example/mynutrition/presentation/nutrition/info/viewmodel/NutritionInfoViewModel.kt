package com.example.mynutrition.presentation.nutrition.info.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynutrition.domain.model.UserInfo
import com.example.mynutrition.domain.usecase.GetUserInfoUseCase
import com.example.mynutrition.domain.usecase.SaveUserInfoUseCase
import com.example.mynutrition.presentation.nutrition.info.state.NutritionInfoState
import kotlinx.coroutines.launch

class NutritionInfoViewModel(
    private val saveUserInfo: SaveUserInfoUseCase,
    private val getUserInfo: GetUserInfoUseCase
) : ViewModel() {

    var state by mutableStateOf(NutritionInfoState())
        private set

    init {
        loadInfo()
    }

    private fun loadInfo() = viewModelScope.launch {
        val info = getUserInfo() ?: return@launch
        state = state.copy(
            name = info.name,
            age = info.age.toString(),
            sex = info.sex,
            height = info.heightCm.toString(),
            weight = info.weightKg.toString(),
            activity = info.activityLevel,
            isEditing = true
        )
    }

    fun save() = viewModelScope.launch {
        state = state.copy(isLoading = true)

        saveUserInfo(
            UserInfo(
                name = state.name,
                age = state.age.toInt(),
                sex = state.sex,
                heightCm = state.height.toInt(),
                weightKg = state.weight.toFloat(),
                activityLevel = state.activity
            )
        )

        state = state.copy(isLoading = false, success = true)
    }
}
