package com.zybooks.practicepals.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UiStateViewModel @Inject constructor() : ViewModel() {
    private val _isPracticeBarVisible = mutableStateOf(true)
    val isPracticeBarVisible: State<Boolean> = _isPracticeBarVisible

    fun setPracticeBarVisibility(visible: Boolean) {
        _isPracticeBarVisible.value = visible
    }
}
