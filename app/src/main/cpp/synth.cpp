#include <jni.h>
#include <memory>
#include "include/Log.h"
#include "include/WavetableSynth.h"


// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("synth");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("synth")
//      }
//    }
extern "C" {
JNIEXPORT jlong JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_create(JNIEnv *env, jobject thiz) {
    auto synth = std::make_unique<::WavetableSynth>();
    if (not synth) {
        //LOGD("Failed to create WavetableSynth");
        synth.reset(nullptr);
    }
    return reinterpret_cast<jlong>(synth.release());
}
extern "C"
JNIEXPORT void JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_destroy(JNIEnv *env, jobject thiz, jlong handle) {
    auto *synth = reinterpret_cast<wavetablesynth::WavetableSynth>(handle);

    if (not synth) {
        // LOGD("WavetableSynth is null");
        return;
    }
    delete synth;
}
extern "C"
JNIEXPORT void JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_play(JNIEnv *env, jobject thiz, jlong handle) {
    auto *synth = reinterpret_cast<wavetablesynth::WavetableSynth>(handle);

    if (not synth) {
        // LOGD("Synth not created");
        return;
    }
    synth->play();

}
extern "C"
JNIEXPORT void JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_stop(JNIEnv *env, jobject thiz, jlong handle) {
    auto *synth = reinterpret_cast<wavetablesynth::WavetableSynth>(handle);

    if (not synth) {
        // LOGD("Synth not created");
        return;
    }
    synth->stop();
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_isPlaying(JNIEnv *env, jobject thiz, jlong handle) {
    auto *synth = reinterpret_cast<wavetablesynth::WavetableSynth>(handle);

    if (not synth) {
        // LOGD("Synth not created");
        return false;
    }
    return synth->isPlaying();
}
extern "C"
JNIEXPORT void JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_setFrequency(JNIEnv *env, jobject thiz, jlong handle,
                                                         jfloat frequency) {
    auto *synth = reinterpret_cast<wavetablesynth::WavetableSynth>(handle);

    if (not synth) {
        // LOGD("Synth not created");
        return;
    }
    synth->setFrequency(static_cast<float>(frequency));
}
extern "C"
JNIEXPORT void JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_setVolume(JNIEnv *env, jobject thiz, jlong handle,
                                                      jfloat volume) {
    auto *synth = reinterpret_cast<wavetablesynth::WavetableSynth>(handle);


    if (not synth) {
        // LOGD("Synth not created");
        return;
    }
    synth->setVolume(static_cast<float>(volume));
}
extern "C"
JNIEXPORT void JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_setWavetable(JNIEnv *env, jobject thiz, jlong handle,
                                                         jint wavetable) {

    auto *synth = reinterpret_cast<wavetablesynth::WavetableSynth>(handle);
    const auto nativeWaveTable = static_cast<wavetablesynth::Wavetable>(wavetable);


    if (not synth) {
        // LOGD("Synth not created");
        return;
    }
    synth->setWavetable(static_cast<wavetablesynth::Wavetable>(wavetable));
}
}