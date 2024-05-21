//
// Created by ched54 on 5/21/2024.
//


#pragma once



namespace wavetablesynth {

    class AudioPlayer {
    public:
        virtual ~AudioPlayer() = default;

        virtual int32_t play() = 0;

        virtual void stop() = 0;

    };
}