//package com.example.myworkout.presentation.viewmodel
//
//import androidx.lifecycle.ViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//
//abstract class BaseViewModel : ViewModel() {
//
//
//    private val _loadingText: MutableStateFlow<String> = MutableStateFlow(EMPTY_STRING)
//    val loadingText: StateFlow<String>
//        get() = _loadingText
//
//    private val _viewState: MutableStateFlow<ViewState> =
//        MutableStateFlow(ViewState.Loading)
//    val viewState: StateFlow<ViewState>
//        get() = _viewState
//
//    fun setState(state: ViewState) {
//        _viewState.value = state
//    }
//
//    fun setLoadingText(loadingText: String){
//        _loadingText.value = loadingText
//    }
//
//    companion object {
//        const val EMPTY_STRING = ""
//    }
//}