package com.zybooks.practicepals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.practicepals.manager.StopwatchManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor(
    private val stopwatchManager: StopwatchManager
) : ViewModel() {

    val elapsedTime: StateFlow<Long> = stopwatchManager.elapsedTime
    val isRunning: StateFlow<Boolean> = stopwatchManager.isRunning

    fun startStopwatch() {
        stopwatchManager.startStopwatch()
    }

    fun pauseStopwatch() {
        stopwatchManager.pauseStopwatch()
    }

    fun resetStopwatch() {
        stopwatchManager.resetStopwatch()
    }
}
