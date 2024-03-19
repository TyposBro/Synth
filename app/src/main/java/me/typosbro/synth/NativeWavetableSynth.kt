package me.typosbro.synth

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NativeWavetableSynth : WavetableSynth, DefaultLifecycleObserver {
    // Handle to native wavetable synth class that holds memory address of native object
    private var nativeWavetableSynthHandle: Long = 0

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
    private external fun setWavetable(handle: Long, wavetable: Int)

    companion object {
        init {
            System.loadLibrary("wavetablesynthesizer")
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        synchronized(synthMutex) {
            createNativeHandle()
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        synchronized(synthMutex) {
            if (nativeWavetableSynthHandle == 0L) {
                return
            }
            destroy(nativeWavetableSynthHandle)
            nativeWavetableSynthHandle = 0L
        }
    }


    override suspend fun play() = withContext(Dispatchers.Default) {
        synchronized(synthMutex) {
            createNativeHandle()
            play(nativeWavetableSynthHandle)
        }
    }

    override suspend fun stop() {
        synchronized(synthMutex) {
            createNativeHandle()
            stop(nativeWavetableSynthHandle)
        }
    }

    override suspend fun isPlaying(): Boolean = withContext(Dispatchers.Default) {
        synchronized(synthMutex) {
            createNativeHandle()
            return@withContext isPlaying(nativeWavetableSynthHandle)
        }
    }

    override suspend fun setFrequency(frequency: Float) = withContext(Dispatchers.Default) {
        synchronized(synthMutex) {
            createNativeHandle()
            setFrequency(nativeWavetableSynthHandle, frequency)
        }
    }

    override suspend fun setVolume(volume: Float) = withContext(Dispatchers.Default) {
        synchronized(synthMutex) {
            createNativeHandle()
            setVolume(nativeWavetableSynthHandle, volume)
        }
    }

    override suspend fun setWavetable(wavetable: Wavetable) = withContext(Dispatchers.Default) {
        synchronized(synthMutex) {
            createNativeHandle()
            setWavetable(nativeWavetableSynthHandle, wavetable.ordinal)
        }
    }

    private fun createNativeHandle() {
        if (nativeWavetableSynthHandle != 0L) {
            return
        }
        // Create native wavetable synth object and store its memory address in nativeWavetableSynthHandle
        nativeWavetableSynthHandle = create()
    }
}