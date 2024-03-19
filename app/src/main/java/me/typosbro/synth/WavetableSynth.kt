package me.typosbro.synth

import androidx.annotation.StringRes

enum class Wavetable {
    SINE {
        override fun toResourceString(): Int {
            return R.string.sine
        }
    },
    TRIANGLE {
        override fun toResourceString(): Int {
            return R.string.triangle
        }
    },
    SQUARE {
        override fun toResourceString(): Int {
            return R.string.square
        }
    },
    SAWTOOTH {
        override fun toResourceString(): Int {
            return R.string.sawtooth
        }
    };

    @StringRes
    abstract fun toResourceString(): Int
}

interface WavetableSynth {
    suspend fun play()
    suspend fun stop()
    suspend fun isPlaying(): Boolean
    suspend fun setFrequency(frequency: Float)
    suspend fun setVolume(volume: Float)
    suspend fun setWavetable(wavetable: Wavetable)

}