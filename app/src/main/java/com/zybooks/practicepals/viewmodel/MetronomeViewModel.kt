package com.zybooks.practicepals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.zybooks.practicepals.utilities.Metronome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MetronomeViewModel(application: Application) : AndroidViewModel(application) {
    private val metronome = Metronome.getInstance(application, 60, 4)

    private val _tempo = MutableStateFlow(metronome.getTempo())
    val tempo = _tempo.asStateFlow()

    private val _timeSignature = MutableStateFlow(metronome.signatureString)
    val timeSignature = _timeSignature.asStateFlow()

    private val _isPlaying = MutableStateFlow(metronome.isPlaying())
    val isPlaying = _isPlaying.asStateFlow()

    fun increaseTempo() {
        val newTempo = (tempo.value + 1).coerceAtMost(Metronome.MAX_METRONOME_BPM)
        setTempo(newTempo)
    }

    fun decreaseTempo() {
        val newTempo = (tempo.value - 1).coerceAtLeast(Metronome.MIN_METRONOME_BPM)
        setTempo(newTempo)
    }

    private fun setTempo(newTempo: Int) {
        metronome.setTempo(newTempo)
        _tempo.value = newTempo
    }

    fun setNumerator(newNumerator: Int) {
        metronome.setNumerator(newNumerator)
        _timeSignature.value = metronome.signatureString
    }

    fun togglePlayPause() {
        if (metronome.isPlaying()) {
            metronome.stop()
        } else {
            metronome.start()
        }
        _isPlaying.value = metronome.isPlaying()
    }

    override fun onCleared() {
        super.onCleared()
        metronome.release()
    }
}
