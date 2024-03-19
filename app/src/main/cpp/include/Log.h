//
// Created by ched54 on 3/19/2024.
//
#pragma once
#include <android/log.h>

#ifndef NDEBUG
#define LOG_TAG "WavetableSynth"
#define LOGD(...) __android_log_print(android_LogPriority::ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

#else
#define LOGD(...)
#endif
