//
// Created by ched54 on 3/19/2024.
//
#include "include/Log.h"
#include "WavetableSynth.h"
#include "OboeAudioPlayer.h"
#include "WavetableOsc.h"


namespace wavetablesynth {

    WavetableSynth::WavetableSynth() : _oscillator{std::make_shared<A4Osc>(sampleRate)},
                                       _audioPlayer{std::make_unique<OboeAudioPlayer>(_oscillator,
                                                                                      sampleRate)} {
        LOGD("WavetableSynth::WavetableSynth()");

    }
    WavetableSynth::~WavetableSynth() = default;


    void WavetableSynth::play() {
        LOGD("WavetableSynth::play()");
        const auto result = _audioPlayer->play();
        if (result == 0){
            _isPlaying = true;
        } else {
            LOGD("Failed to play audio");
        }
    }

    void WavetableSynth::stop() {
        LOGD("WavetableSynth::stop()");
        _audioPlayer->stop();
        _isPlaying = false;
    }

    bool WavetableSynth::isPlaying() const{
        LOGD("WavetableSynth::isPlaying()");
        return _isPlaying;
    }

    void WavetableSynth::setFrequency(float frequency) {
        LOGD("WavetableSynth::setFrequency() called with %.2f", frequency);
    }

    void WavetableSynth::setVolume(float volume) {
        LOGD("WavetableSynth::setVolume() called with %.2f", volume);
    }

    void WavetableSynth::setWavetable(wavetablesynth::Wavetable wavetable) {
        LOGD("WavetableSynth::setWaveform() called with %d", static_cast<int>(wavetable));
    }



}
