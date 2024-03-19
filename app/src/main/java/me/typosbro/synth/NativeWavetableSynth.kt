package me.typosbro.synth

import androidx.lifecycle.DefaultLifecycleObserver

class NativeWavetableSynth: WavetableSynth, DefaultLifecycleObserver {
    override suspend fun play(wavetable: Wavetable, frequency: Float, duration: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun stop() {
        TODO("Not yet implemented")
    }

    override suspend fun isPlaying(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun setFrequency(frequency: Float) {
        TODO("Not yet implemented")
    }

    override suspend fun setVolume(volume: Float) {
        TODO("Not yet implemented")
    }

    override suspend fun setWavetable(wavetable: Wavetable) {
        TODO("Not yet implemented")
    }
}