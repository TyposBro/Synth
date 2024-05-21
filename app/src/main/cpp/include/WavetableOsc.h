#pragma once

#include "AudioSource.h"

namespace wavetablesynth {
    class A4Osc : public AudioSource {
    public:
        explicit A4Osc(float sampleRate);
//        ~A4Osc() = default;

        float getSample() override;

        void onPlaybackStopped() override;


    private:
        float _phase{0.f};
        float _phaseIncrement{0.f};

    };
};