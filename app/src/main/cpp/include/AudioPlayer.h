//
// Created by ched54 on 5/21/2024.
//


#pragma once



namespace wavetable_synth {

    class AudioPlayer {
    public:
        virtual ~AudioPlayer() = default;

        virtual int32_t play() = 0;

        virtual void stop() = 0;

        virtual bool isPlaying() = 0;

        virtual void setFrequency(float frequency) = 0;

        virtual void setVolume(float volume) = 0;

        virtual void setWavetable(int wavetable) = 0;
    };
}