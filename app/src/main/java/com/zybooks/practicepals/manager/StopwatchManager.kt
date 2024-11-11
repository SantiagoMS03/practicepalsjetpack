package com.zybooks.practicepals.manager

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StopwatchManager @Inject constructor() {

    // StateFlow for elapsed time in milliseconds
    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime: StateFlow<Long> = _elapsedTime.asStateFlow()

    // StateFlow for the running status
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    private var startTime = 0L
    private var stopwatchJob: Job? = null

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    fun startStopwatch() {
        if (!_isRunning.value) {
            _isRunning.value = true
            startTime = System.currentTimeMillis() - _elapsedTime.value

            stopwatchJob = scope.launch {
                while (_isRunning.value) {
                    _elapsedTime.value = System.currentTimeMillis() - startTime
                    delay(10L) // Update every 10 milliseconds
                }
            }
        }
    }

    fun pauseStopwatch() {
        _isRunning.value = false
        stopwatchJob?.cancel()
    }

    fun resetStopwatch() {
        pauseStopwatch()
        _elapsedTime.value = 0L
    }
}
