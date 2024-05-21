//
// Created by ched54 on 3/19/2024.
//
#pragma once
#include "memory"

namespace wavetablesynth {
    enum class Wavetable {
        SINE,
        TRIANGLE,
        SQUARE,
        SAWTOOTH
    };

    constexpr auto sampleRate = 48000;

    class AudioSource;
    class AudioPlayer;

    class WavetableSynth {
    public:
        WavetableSynth();
        ~WavetableSynth();

        void play();
        void stop();
        bool isPlaying() const;
        void setFrequency(float frequency);
        void setVolume(float volume);
        void setWavetable(Wavetable waveform);
    private:
        bool _isPlaying = false;
        std::shared_ptr<AudioSource> _oscillator;
        std::unique_ptr<AudioPlayer> _audioPlayer;
    };
}