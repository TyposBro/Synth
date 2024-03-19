#include <jni.h>

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
extern "C"
JNIEXPORT jlong JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_create(JNIEnv *env, jobject thiz) {
    // TODO: implement create()
}
extern "C"
JNIEXPORT void JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_destroy(JNIEnv *env, jobject thiz, jlong handle) {
    // TODO: implement destroy()
}
extern "C"
JNIEXPORT void JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_play(JNIEnv *env, jobject thiz, jlong handle) {
    // TODO: implement play()
}
extern "C"
JNIEXPORT void JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_stop(JNIEnv *env, jobject thiz, jlong handle) {
    // TODO: implement stop()
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_isPlaying(JNIEnv *env, jobject thiz, jlong handle) {
    // TODO: implement isPlaying()
}
extern "C"
JNIEXPORT void JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_setFrequency(JNIEnv *env, jobject thiz, jlong handle,
                                                         jfloat frequency) {
    // TODO: implement setFrequency()
}
extern "C"
JNIEXPORT void JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_setVolume(JNIEnv *env, jobject thiz, jlong handle,
                                                      jfloat volume) {
    // TODO: implement setVolume()
}
extern "C"
JNIEXPORT void JNICALL
Java_me_typosbro_synth_NativeWavetableSynth_setWavetable(JNIEnv *env, jobject thiz, jlong handle,
                                                         jint wavetable) {
    // TODO: implement setWavetable()
}