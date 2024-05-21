#include "OboeAudioPlayer.h"

#include <utility>
#include "AudioSource.h"
#include "Log.h"


using namespace oboe;

namespace wavetablesynth {
    OboeAudioPlayer::OboeAudioPlayer(std::shared_ptr <AudioSource> source, int samplingRate)
            : _source(std::move(source)),
              _samplingRate(samplingRate) {

    }

    OboeAudioPlayer::~OboeAudioPlayer() {
        OboeAudioPlayer::stop();
    };

    int32_t OboeAudioPlayer::play() {
        AudioStreamBuilder builder;
        const auto result = builder.setPerformanceMode(PerformanceMode::LowLatency)
                ->setDirection(Direction::Output)
                ->setSampleRate(_samplingRate)
                ->setDataCallback(this)
                ->setSharingMode(SharingMode::Exclusive)
                ->setFormat(AudioFormat::Float)
                ->setChannelCount(channelCount)
                ->setSampleRateConversionQuality(SampleRateConversionQuality::Best)
                ->openStream(_stream);
        if (result != Result::OK) {
            return static_cast<int32_t>(result);
        }

        const auto playResult = _stream->requestStart();

        return static_cast<int32_t>(playResult);
    }


    void OboeAudioPlayer::stop() {
        LOGD("OboeAudioPlayer::stop()");
        if (_stream) {
            _stream->stop();
            _stream->close();
            _stream.reset();
        }
        _source->onPlaybackStopped();
    }


    oboe::DataCallbackResult
    OboeAudioPlayer::onAudioReady(oboe::AudioStream *audioStream, void *audioData,
                                  int32_t framesCount) {
        auto *floatData = reinterpret_cast<float *>(audioData);

        for (auto i = 0; i < framesCount; ++i) {
            const auto sample = _source->getSample();
            for (auto j = 0; j < channelCount; ++j) {
                floatData[i * channelCount + j] = sample;
            }
        }

        return oboe::DataCallbackResult::Continue;
    }


}