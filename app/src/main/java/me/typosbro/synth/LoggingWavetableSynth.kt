package me.typosbro.synth

import android.util.Log

class LoggingWavetableSynth : WavetableSynth {

    private var isPlaying = false
    private val TAG = "LoggingWavetableSynth"
    override suspend fun play(wavetable: Wavetable, frequency: Float, duration: Long) {
        Log.d(TAG, "play() called")
        isPlaying = true
    }

    override suspend fun stop() {
        Log.d(TAG, "stop() called")
        isPlaying = false
    }

    override suspend fun isPlaying(): Boolean {
        Log.d(TAG, "isPlaying() called")
        return isPlaying
    }

    override suspend fun setFrequency(frequency: Float) {
        Log.d(TAG, "setFrequency() called $frequency")
    }

    override suspend fun setVolume(volume: Float) {
        Log.d(TAG, "setVolume() called $volume")
    }

    override suspend fun setWavetable(wavetable: Wavetable) {
        Log.d(TAG, "setWavetable() called $wavetable")
    }

}