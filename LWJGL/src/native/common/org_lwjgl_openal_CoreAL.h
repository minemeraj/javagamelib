/* 
 * Copyright (c) 2002 Lightweight Java Game Library Project
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are 
 * met:
 * 
 * * Redistributions of source code must retain the above copyright 
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'Lightweight Java Game Library' nor the names of 
 *   its contributors may be used to endorse or promote products derived 
 *   from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_lwjgl_openal_CoreAL */

#ifndef _Included_org_lwjgl_openal_CoreAL
#define _Included_org_lwjgl_openal_CoreAL
#ifdef __cplusplus
extern "C" {
#endif
/* Inaccessible static: created */
/* Inaccessible static: class_000240 */
/* Inaccessible static: class_000241 */
/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alEnable
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alEnable
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alDisable
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alDisable
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alIsEnabled
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_org_lwjgl_openal_CoreAL_alIsEnabled
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alHint
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alHint
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetBoolean
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_org_lwjgl_openal_CoreAL_alGetBoolean
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetInteger
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_lwjgl_openal_CoreAL_alGetInteger
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetFloat
 * Signature: (I)F
 */
JNIEXPORT jfloat JNICALL Java_org_lwjgl_openal_CoreAL_alGetFloat
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetDouble
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_org_lwjgl_openal_CoreAL_alGetDouble
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetBooleanv
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alGetBooleanv
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetIntegerv
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alGetIntegerv
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetFloatv
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alGetFloatv
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetDoublev
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alGetDoublev
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetString
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_lwjgl_openal_CoreAL_alGetString
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetError
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_lwjgl_openal_CoreAL_alGetError
  (JNIEnv *, jclass);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alIsExtensionPresent
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_lwjgl_openal_CoreAL_alIsExtensionPresent
  (JNIEnv *, jclass, jstring);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetEnumValue
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_lwjgl_openal_CoreAL_alGetEnumValue
  (JNIEnv *, jclass, jstring);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alListeneri
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alListeneri
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alListenerf
 * Signature: (IF)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alListenerf
  (JNIEnv *, jclass, jint, jfloat);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    listener3f
 * Signature: (IFFF)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alListener3f
  (JNIEnv *, jclass, jint, jfloat, jfloat, jfloat);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alListenerfv
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alListenerfv
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetListeneri
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alGetListeneri
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetListenerf
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alGetListenerf
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetListenerfv
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alGetListenerfv
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGenSources
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alGenSources
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alDeleteSources
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alDeleteSources
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alIsSource
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_org_lwjgl_openal_CoreAL_alIsSource
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alSourcei
 * Signature: (III)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alSourcei
  (JNIEnv *, jclass, jint, jint, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alSourcef
 * Signature: (IIF)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alSourcef
  (JNIEnv *, jclass, jint, jint, jfloat);
  
/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    source3f
 * Signature: (IIFFF)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alSource3f
  (JNIEnv *, jclass, jint, jint, jfloat, jfloat, jfloat);  

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alSourcefv
 * Signature: (IILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alSourcefv
  (JNIEnv *, jclass, jint, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetSourcei
 * Signature: (IILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alGetSourcei
  (JNIEnv *, jclass, jint, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetSourcef
 * Signature: (IILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alGetSourcef
  (JNIEnv *, jclass, jint, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetSourcefv
 * Signature: (IILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alGetSourcefv
  (JNIEnv *, jclass, jint, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alSourcePlayv
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alSourcePlayv
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alSourcePausev
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alSourcePausev
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alSourceStopv
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alSourceStopv
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alSourceRewindv
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alSourceRewindv
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alSourcePlay
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alSourcePlay
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alSourcePause
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alSourcePause
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alSourceStop
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alSourceStop
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alSourceRewind
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alSourceRewind
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGenBuffers
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alGenBuffers
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alDeleteBuffers
 * Signature: (ILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alDeleteBuffers
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alIsBuffer
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_org_lwjgl_openal_CoreAL_alIsBuffer
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alBufferData
 * Signature: (IILjava/nio/Buffer;II)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alBufferData
  (JNIEnv *, jclass, jint, jint, jobject, jint, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetBufferi
 * Signature: (IILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alGetBufferi
  (JNIEnv *, jclass, jint, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alGetBufferf
 * Signature: (IILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alGetBufferf
  (JNIEnv *, jclass, jint, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alSourceQueueBuffers
 * Signature: (IILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alSourceQueueBuffers
  (JNIEnv *, jclass, jint, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alSourceUnqueueBuffers
 * Signature: (IILjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alSourceUnqueueBuffers
  (JNIEnv *, jclass, jint, jint, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alDistanceModel
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alDistanceModel
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alDopplerFactor
 * Signature: (F)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alDopplerFactor
  (JNIEnv *, jclass, jfloat);

/*
 * Class:     org_lwjgl_openal_CoreAL
 * Method:    alDopplerVelocity
 * Signature: (F)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL_alDopplerVelocity
  (JNIEnv *, jclass, jfloat);

#ifdef __cplusplus
}
#endif
#endif
