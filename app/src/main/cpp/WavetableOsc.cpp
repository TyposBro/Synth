#define _USE_MATH_DEFINES

#include "include/WavetableOsc.h"
#include <cmath>
//#include "MathConstants.h"

namespace wavetablesynth {
    A4Osc::A4Osc(float sampleRate) : _phaseIncrement(440.f / sampleRate * 2.f * M_PI) {
    }

    float A4Osc::getSample() {
        const auto sample = 0.5f * std::sin(_phase);
        _phase = std::fmod(_phase + _phaseIncrement, 2 * M_PI);
        return sample;
    }

    void A4Osc::onPlaybackStopped() {
        _phase = 0.f;
    }
}