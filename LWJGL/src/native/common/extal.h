/* 
 * Copyright (c) 2002 Light Weight Java Game Library Project
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
 * * Neither the name of 'Light Weight Java Game Library' nor the names of 
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

#ifndef _AL_TEST_H
#define _AL_TEST_H

#ifdef _WIN32
#include <windows.h>
#endif

#ifdef _X11
#include <AL/altypes.h>
#include <AL/alctypes.h>
#endif
#ifdef _WIN32
#include <altypes.h>
#include <alctypes.h>
#endif
#ifdef _AGL
#include <OpenAL/alctypes.h>
#include <OpenAL/altypes.h>
#endif

#include <jni.h>
#include "common_tools.h"

bool extal_InitializeClass(JNIEnv *env, jclass clazz, jobject ext_set, const char *ext_name, int num_functions, JavaMethodAndExtFunction *functions);

#ifdef __cplusplus
extern "C" {
#endif

#ifdef _WIN32
 #ifdef _OPENAL32LIB
  #define ALCAPI __declspec(dllexport)
 #else
  #define ALCAPI __declspec(dllimport)
 #endif

 typedef struct ALCdevice_struct ALCdevice;
 typedef struct ALCcontext_struct ALCcontext;

 #define ALCAPIENTRY __cdecl
#else
 #ifdef _AGL
  #if _AGL
 typedef struct ALCdevice_struct ALCdevice;
 typedef struct ALCcontext_struct ALCcontext;
 
  #endif
 #endif
 #define ALCAPI
 #define ALCAPIENTRY

/** ALC boolean type. */
typedef char ALCboolean;

/** ALC 8bit signed byte. */
typedef char ALCbyte;

/** ALC 8bit unsigned byte. */
typedef unsigned char ALCubyte;

/** ALC 16bit signed short integer type. */
typedef short ALCshort;

/** ALC 16bit unsigned short integer type. */
typedef unsigned short ALCushort;

/** ALC 32bit unsigned integer type. */
typedef unsigned ALCuint;

/** ALC 32bit signed integer type. */
typedef int ALCint;

/** ALC 32bit floating point type. */
typedef float ALCfloat;

/** ALC 64bit double point type. */
typedef double ALCdouble;

/** ALC 32bit type. */
typedef unsigned int ALCsizei;

/** ALC void type */
typedef void ALCvoid;

#endif

#ifdef _WIN32
 #ifdef _OPENAL32LIB
  #define ALAPI __declspec(dllexport)
 #else
  #define ALAPI __declspec(dllimport)
 #endif
 #define ALAPIENTRY __cdecl
 #define AL_CALLBACK
#else
 #define ALAPI
 #define ALAPIENTRY
 #define AL_CALLBACK
#endif

#ifdef _WIN32
// EAX 2.0 GUIDs
const GUID DSPROPSETID_EAX20_ListenerProperties
				= { 0x306a6a8, 0xb224, 0x11d2, { 0x99, 0xe5, 0x0, 0x0, 0xe8, 0xd8, 0xc7, 0x22 } };

const GUID DSPROPSETID_EAX20_BufferProperties
				= { 0x306a6a7, 0xb224, 0x11d2, {0x99, 0xe5, 0x0, 0x0, 0xe8, 0xd8, 0xc7, 0x22 } };
#endif

#define INITGUID
#define OPENAL

void InitializeOpenAL(JNIEnv *env, jobjectArray oalPaths);
void DeInitializeOpenAL();

#ifdef _WIN32
typedef ALenum (*EAXSet)(const GUID*, ALuint, ALuint, ALvoid*, ALuint);
typedef ALenum (*EAXGet)(const GUID*, ALuint, ALuint, ALvoid*, ALuint);

extern EAXSet  eaxSet;
extern EAXGet  eaxGet;
#endif

typedef ALvoid      (ALAPIENTRY *alEnablePROC)( ALenum capability );
extern alEnablePROC alEnable;
#ifdef __cplusplus
}
#endif

#endif
