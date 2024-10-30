// Metronome.kt (Updated)
package com.zybooks.practicepals.utilities

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.zybooks.practicepals.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class Metronome @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named("MetronomeTempo") private var tempo: Int,
    @Named("MetronomeNumerator") private var numerator: Int
) {

    private var isPlaying = false
    private val soundPool: SoundPool
    private var metronomeSoundId: Int = 0
    private var denominator = 4
    val signatureString: String
        get() = "$numerator/$denominator"

    private var isSoundLoaded = false

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()

        soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
            if (status == 0 && sampleId == metronomeSoundId) {
                isSoundLoaded = true
                // Log
            } else {
                // Handle error
            }
        }

        metronomeSoundId = soundPool.load(context, R.raw.audio1, 1)
    }

    fun start() {
        if (isPlaying || !isSoundLoaded) return
        isPlaying = true
    }

    fun isPlaying(): Boolean {
        return isPlaying
    }

    fun stop() {
        isPlaying = false
    }

    fun setTempo(newTempo: Int) {
        tempo = newTempo
    }

    fun getTempo(): Int {
        return tempo
    }

    fun setNumerator(newNumerator: Int) {
        numerator = newNumerator
    }

    fun release() {
        soundPool.release()
    }

    fun playTick() {
        if (isSoundLoaded) {
            soundPool.play(metronomeSoundId, 1f, 1f, 0, 0, 1f)
        }
    }

    companion object {
        const val MAX_METRONOME_BPM = 300
        const val MIN_METRONOME_BPM = 10
    }
}
