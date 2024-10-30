package com.zybooks.practicepals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor() : ViewModel() {

    private val _elapsedTime = MutableStateFlow(0L) // Time in milliseconds
    val elapsedTime: StateFlow<Long> = _elapsedTime

    private var isRunning = false
    private var startTime = 0L

    fun startStopwatch() {
        if (!isRunning) {
            isRunning = true
            startTime = System.currentTimeMillis() - _elapsedTime.value
            viewModelScope.launch {
                while (isRunning) {
                    _elapsedTime.value = System.currentTimeMillis() - startTime
                    delay(10L) // Update every 10 milliseconds
                }
            }
        }
    }

    fun pauseStopwatch() {
        isRunning = false
    }

    fun resetStopwatch() {
        isRunning = false
        _elapsedTime.value = 0L
    }

    fun isRunning(): Boolean {
        return isRunning
    }

    fun hasStarted(): Boolean {
        return elapsedTime.value != 0L
    }

}
