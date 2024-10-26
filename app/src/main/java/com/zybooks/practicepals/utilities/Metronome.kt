package com.zybooks.practicepals.utilities

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.zybooks.practicepals.R
import kotlinx.coroutines.*

class Metronome private constructor(
    context: Context,
    private var tempo: Int,
    private var numerator: Int
) {

    private var isPlaying = false
    private val soundPool: SoundPool
    private val metronomeSoundId: Int
    private var job: Job? = null
    private var denominator = 4
    val signatureString: String
        get() = "$numerator/$denominator"

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()
        metronomeSoundId = soundPool.load(context, R.raw.audio1, 1)
    }

    fun start() {
        if (isPlaying) return
        isPlaying = true
        job = CoroutineScope(Dispatchers.IO).launch {
            while (isPlaying) {
                soundPool.play(metronomeSoundId, 1f, 1f, 0, 0, 1f)
                delay((60_000 / tempo).toLong())
            }
        }
    }

    fun isPlaying(): Boolean {
        return isPlaying
    }

    fun stop() {
        isPlaying = false
        job?.cancel()
    }

    fun setTempo(newTempo: Int) {
        tempo = newTempo
        if (isPlaying) restart()
    }

    fun getTempo(): Int {
        return tempo
    }

    fun setNumerator(newNumerator: Int) {
        numerator = newNumerator
        if (isPlaying) restart()
    }

    private fun restart() {
        stop()
        start()
    }

    fun release() {
        soundPool.release()
        job?.cancel()
    }

    companion object {
        private var instance: Metronome? = null

        fun getInstance(context: Context, initialTempo: Int = 60, initialNumerator: Int = 4): Metronome {
            if (instance == null) {
                instance = Metronome(context, initialTempo, initialNumerator)
            }
            return instance!!
        }

        const val MAX_METRONOME_BPM = 300
        const val MIN_METRONOME_BPM = 10
    }
}
