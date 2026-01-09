package com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrition.mynutrition.domain.mappers.toUserInfoState
import com.example.nutrition.mynutrition.domain.model.user.UserInfo
import com.example.nutrition.mynutrition.domain.usecase.GetUserInfoUseCase
import com.example.nutrition.mynutrition.domain.usecase.SaveUserInfoUseCase
import com.example.nutrition.mynutrition.presentation.core.userinfo.viewmodel.state.UserInfoState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class UserInfoViewModel(
    private val saveUserInfoUseCase: SaveUserInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _userInfoState = MutableStateFlow(UserInfoState())
    val userInfoState: StateFlow<UserInfoState> = _userInfoState

    fun fetchUserinfo() =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getUserInfoUseCase.getUserInfo()?.let { userInfo ->
                    _userInfoState.value = userInfo.toUserInfoState()
                }
            } catch (t: Throwable) {
                Log.e(ERROR, t.message.toString())
            }
        }

    fun onSave(userInfo: UserInfo) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                saveUserInfoUseCase.saveUser(userInfo)
                getUserInfoUseCase.getUserInfo()?.let { userInfo ->
                    _userInfoState.value = userInfo.toUserInfoState()
                }
            } catch (t: Throwable) {
                Log.e(ERROR, t.message.toString())
            }
        }

    companion object {
        const val ERROR = "Error"
    }
}