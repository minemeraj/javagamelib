/*
	checkGLerror.h

	Author:		C. Prince
	Created:	8 November 2001

	Error checking for OpenGL bindings
*/

#ifndef _CHECKGLERROR_H_INCLUDED_
#define _CHECKGLERROR_H_INCLUDED_

#ifdef _DEBUG

#include <jni.h>
#include <al.h>

#define CHECK_AL_ERROR \
	{ \
		int err = alGetError(); \
		if (err != AL_NO_ERROR) { \
			jclass cls = env->FindClass("org/lwjgl/openal/OpenALException"); \
			env->ThrowNew(cls, (const char*) alGetString(err)); \
			env->DeleteLocalRef(cls); \
		} \
	}
/* only available if deviceaddress is specified in method */
#define CHECK_ALC_ERROR \
	{ \
		int err = alcGetError((ALCdevice*) deviceaddress); \
		if (err != AL_NO_ERROR) { \
			jclass cls = env->FindClass("org/lwjgl/openal/OpenALException"); \
			env->ThrowNew(cls, (const char*) alcGetString((ALCdevice*) deviceaddress, err)); \
			env->DeleteLocalRef(cls); \
		} \
	}

#else

#define CHECK_GL_ERROR

#endif /* _DEBUG */

#endif /* _CHECKGLERROR_H_INCLUDED_ */