package com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrition.mynutrition.domain.mappers.toState
import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.usecase.GetUserInfoUseCase
import com.example.nutrition.mynutrition.domain.usecase.SaveUserInfoUseCase
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.state.UserInfoState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Loading : UiState()
    data class Error(val message: String) : UiState()
    object SuccessFetch : UiState()
    data class SuccessSave(val userInfo: UserInfo) : UiState()
}

open class UserInfoViewModel(
    private val saveUserInfoUseCase: SaveUserInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _userInfoState = MutableStateFlow(UserInfoState())
    val userInfoState: StateFlow<UserInfoState> = _userInfoState

    fun fetchUserinfo() =
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UiState.Loading
            try {
                getUserInfoUseCase.getUserInfo()?.let { userInfo ->
                    _userInfoState.value = userInfo.toState()
                }
                _uiState.value = UiState.SuccessFetch
            } catch (t: Throwable) {
                Log.e(ERROR, t.message.toString())
                _uiState.value = UiState.Error(t.message.toString())
            }
        }

    fun onSave(userInfo: UserInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UiState.Loading
            try {
                saveUserInfoUseCase.saveUser(userInfo)

                val userInfo = getUserInfoUseCase.getUserInfo()
                _userInfoState.value = userInfo!!.toState()
                _uiState.value = UiState.SuccessSave(userInfo)
            } catch (t: Throwable) {
                Log.e(ERROR, t.message.toString())
                _uiState.value = UiState.Error(t.message.toString())
            }
        }
    }

    companion object {
        const val ERROR = "Error"
    }
}