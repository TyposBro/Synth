//
// Created by ched54 on 3/19/2024.
//
#include "include/Log.h"
#include "include/WavetableSynth.h"


namespace wavetablesynth {

    void WavetableSynth::play() {
        LOGD("WavetableSynth::play()");
        _isPlaying = true;
    }

    void WavetableSynth::stop() {
        LOGD("WavetableSynth::stop()");
        _isPlaying = false;
    }

    bool WavetableSynth::isPlaying() {
        LOGD("WavetableSynth::isPlaying()");
        return _isPlaying;
    }

    void WavetableSynth::setFrequency(float frequency) {
        LOGD("WavetableSynth::setFrequency() called with %.2f", frequency);
    }

    void WavetableSynth::setVolume(float volume) {
        LOGD("WavetableSynth::setVolume() called with %.2f", volume);
    }

    void WavetableSynth::setWavetable(wavetablesynth::Wavetable wavetable)  {
        LOGD("WavetableSynth::setWaveform() called with %d", static_cast<int>(wavetable));
    }

}
