// MetronomeViewModel.kt
package com.zybooks.practicepals.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.practicepals.utilities.Metronome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MetronomeViewModel @Inject constructor(
    private val metronome: Metronome
) : ViewModel() {

    private val _tempo = MutableStateFlow(metronome.getTempo())
    val tempo: StateFlow<Int> = _tempo.asStateFlow()

    private val _timeSignature = MutableStateFlow(metronome.signatureString)
    val timeSignature: StateFlow<String> = _timeSignature.asStateFlow()

    private val _isPlaying = MutableStateFlow(metronome.isPlaying())
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    fun increaseTempo() {
        val newTempo = (tempo.value + 1).coerceAtMost(Metronome.MAX_METRONOME_BPM)
        setTempo(newTempo)
    }

    fun decreaseTempo() {
        val newTempo = (tempo.value - 1).coerceAtLeast(Metronome.MIN_METRONOME_BPM)
        setTempo(newTempo)
    }

    fun setTempo(newTempo: Int) {
        metronome.setTempo(newTempo)
        _tempo.value = newTempo
    }

    fun setTimeSignature(newNumerator: Int) {
        metronome.setNumerator(newNumerator)
        _timeSignature.value = metronome.signatureString
    }

    fun togglePlayPause() {
        if (metronome.isPlaying()) {
            metronome.stop()
            _isPlaying.value = false
            Log.d(TAG, "Metronome paused.")
        } else {
            metronome.start()
            _isPlaying.value = true
            Log.d(TAG, "Metronome started.")
            viewModelScope.launch(Dispatchers.IO) {
                while (metronome.isPlaying()) {
                    metronome.playTick()
                    Log.d(TAG, "Metronome tick played.")
                    delay(60_000L / metronome.getTempo())
                }
                Log.d(TAG, "Metronome stopped.")
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        metronome.release()
    }

    // Dialog visibility flows
    private val _showBpmDialog = MutableStateFlow(false)
    val showBpmDialog: StateFlow<Boolean> = _showBpmDialog.asStateFlow()

    private val _showTimeSignatureDialog = MutableStateFlow(false)
    val showTimeSignatureDialog: StateFlow<Boolean> = _showTimeSignatureDialog.asStateFlow()

    // Toggle dialog visibility
    fun setShowBpmDialog(show: Boolean) {
        _showBpmDialog.value = show
    }

    fun setShowTimeSignatureDialog(show: Boolean) {
        _showTimeSignatureDialog.value = show
    }
}
