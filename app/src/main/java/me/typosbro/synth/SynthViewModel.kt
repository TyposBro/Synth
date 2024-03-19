package me.typosbro.synth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.math.exp
import kotlin.math.ln

class SynthViewModel : ViewModel() {
    var wavetableSynth: WavetableSynth? = null
        set(value) {
            field = value
           applyParameters()
        }



    private var _frequency = MutableLiveData<Float>(400f)

    val frequency: LiveData<Float>
        get() = _frequency

    fun setFrequency(sliderPosition: Float) {
        val frequencyInHz = getFrequencyInHzFromPosition(sliderPosition)
        _frequency.value = frequencyInHz

        viewModelScope.launch {
            wavetableSynth?.setFrequency(frequencyInHz)
        }
    }

    private val frequencyRange = 40f..3000f

    private fun getFrequencyInHzFromPosition(sliderPosition: Float): Float {
        val rangePosition = linearToExponential(sliderPosition)
        return valueFromRangePosition(frequencyRange, rangePosition)
    }


    private fun getPositionFromFrequencyInHz(frequencyInHz: Float): Float {
        val rangePosition = rangePositionFromValue(frequencyRange, frequencyInHz)
        return exponentialToLinear(rangePosition)
    }


    companion object LinearToExponentialConverter {
        private const val MIN_VALUE = 0.001f

        fun linearToExponential(value: Float): Float {

            assert(value in 0f..1f)

            return if (value < MIN_VALUE) {
                0f
            } else {
                exp(ln(MIN_VALUE) - ln(MIN_VALUE) * value)
            }
        }


        fun valueFromRangePosition(
            range: ClosedFloatingPointRange<Float>,
            rangePosition: Float
        ): Float = range.start + rangePosition * (range.endInclusive - range.start)


        fun rangePositionFromValue(
            range: ClosedFloatingPointRange<Float>,
            frequencyInHz: Float
        ): Float {

            assert(frequencyInHz in range)

            return (frequencyInHz - range.start) / (range.endInclusive - range.start)
        }

        fun exponentialToLinear(rangePosition: Float): Float {

            assert(rangePosition in 0f..1f)

            return if (rangePosition < MIN_VALUE) {
                rangePosition
            } else {
                (ln(rangePosition) - ln(MIN_VALUE)) / (-ln(MIN_VALUE))
            }
        }
    }

    private val _volume = MutableLiveData<Float>(-24f)
    val volume: LiveData<Float>
        get() = _volume

    val volumeRange = -60f..0f

    fun setVolume(volumeInDb: Float) {
        _volume.value = volumeInDb

        viewModelScope.launch {
            wavetableSynth?.setVolume(volumeInDb)
        }
    }


    private var wavetable = Wavetable.SINE

    fun setWavetable(newWavetable: Wavetable) {

        wavetable = newWavetable

        viewModelScope.launch {
            wavetableSynth?.setWavetable(newWavetable)
        }
    }

    private val _playButtonLabel = MutableLiveData(R.string.play)

    val playButtonLabel: LiveData<Int>
        get() = _playButtonLabel


    fun onPlayButtonClicked() {
        viewModelScope.launch {
            if (wavetableSynth?.isPlaying() == true) {
                wavetableSynth?.stop()
//                _playButtonLabel.value = R.string.play
            } else {
                wavetableSynth?.play(wavetable, _frequency.value!!, 1000)
//                _playButtonLabel.value = R.string.stop
            }

            updatePlayButtonLabel()
        }
    }

    private fun updatePlayButtonLabel() {
        viewModelScope.launch {
            if (wavetableSynth?.isPlaying() == true) {
                _playButtonLabel.value = R.string.stop
            } else {
                _playButtonLabel.value = R.string.play
            }
        }
    }


    fun applyParameters() {
        viewModelScope.launch {
            wavetableSynth?.setFrequency(_frequency.value!!)
            wavetableSynth?.setVolume(_volume.value!!)
            wavetableSynth?.setWavetable(wavetable)
            
            updatePlayButtonLabel()
        }
    }


}