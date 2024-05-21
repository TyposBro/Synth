//
// Created by ched54 on 5/21/2024.
//

#pragma once



namespace wavetablesynth {

    class AudioSource {
    public:
        virtual ~AudioSource() = default;
        virtual float getSample() = 0;
        virtual void onPlaybackStopped() = 0;
    };
}
