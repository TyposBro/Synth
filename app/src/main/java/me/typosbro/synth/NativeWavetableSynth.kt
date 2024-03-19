package me.typosbro.synth

import androidx.lifecycle.DefaultLifecycleObserver

class NativeWavetableSynth : WavetableSynth, DefaultLifecycleObserver {
    // Handle to native wavetable synth class that holds memory address of native object
    private val nativeWavetableSynthHandle: Long = 0

    // Make access to native wavetable synth class thread-safe, so we don't use our synth when it's being destroyed
    private val synthMutex = Object()

    // Define external functions that are implemented in C++
    private external fun create(): Long
    private external fun destroy(handle: Long)
    private external fun play(handle: Long)
    private external fun stop(handle: Long)

    private external fun isPlaying(handle: Long): Boolean
    private external fun setFrequency(handle: Long, frequency: Float)
    private external fun setVolume(handle: Long, volume: Float)
    private external fun setWavetable(handle: Long, wavetable: Wavetable)

   


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