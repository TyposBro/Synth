//
// Created by ched54 on 3/19/2024.
//
#pragma once

namespace wavetablesynth {
    enum class Waveform {
        SINE,
        TRIANGLE,
        SQUARE,
        SAWTOOTH
    };

    class WavetableSynth {
    public:
//        WavetableSynth();
//        ~WavetableSynth();

        void play();
        void stop();
        bool isPlaying();
        void setFrequency(float frequency);
        void setVolume(float volume);
        void setWaveform(Waveform waveform);
    private:
        bool _isPlaying = false;
    };
}