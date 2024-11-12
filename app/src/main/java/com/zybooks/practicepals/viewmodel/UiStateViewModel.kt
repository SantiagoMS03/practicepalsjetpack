package com.zybooks.practicepals.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UiStateViewModel @Inject constructor() : ViewModel() {
    private val _isPracticeBarVisible = mutableStateOf(false)
    val isPracticeBarVisible: State<Boolean> = _isPracticeBarVisible

    private val _isHiddenInCurrentScreen = mutableStateOf(false)
    val isHiddenInCurrentScreen: State<Boolean> = _isHiddenInCurrentScreen


    fun setPracticeBarVisibility(visible: Boolean) {
        _isPracticeBarVisible.value = visible
    }

    fun hidePracticeBarInScreen() {
        _isHiddenInCurrentScreen.value = true
    }

    fun stopHidingPracticeBar() {
        _isHiddenInCurrentScreen.value = false
    }
}
