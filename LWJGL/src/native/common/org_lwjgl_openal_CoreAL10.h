/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_lwjgl_openal_CoreAL10 */

#ifndef _Included_org_lwjgl_openal_CoreAL10
#define _Included_org_lwjgl_openal_CoreAL10
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_lwjgl_openal_CoreAL10
 * Method:    alGetError
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_lwjgl_openal_CoreAL10_alGetError
  (JNIEnv *, jobject);

/*
 * Class:     org_lwjgl_openal_CoreAL10
 * Method:    alGetString
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_lwjgl_openal_CoreAL10_alGetString
  (JNIEnv *, jobject, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL10
 * Method:    alGenBuffers
 * Signature: (I[I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL10_alGenBuffers
  (JNIEnv *, jobject, jint, jintArray);

/*
 * Class:     org_lwjgl_openal_CoreAL10
 * Method:    alGenSources
 * Signature: (I[I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL10_alGenSources
  (JNIEnv *, jobject, jint, jintArray);

/*
 * Class:     org_lwjgl_openal_CoreAL10
 * Method:    alBufferData
 * Signature: (IIIII)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL10_alBufferData
  (JNIEnv *, jobject, jint, jint, jint, jint, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL10
 * Method:    alSourcei
 * Signature: (III)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL10_alSourcei
  (JNIEnv *, jobject, jint, jint, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL10
 * Method:    alSourcePlay
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL10_alSourcePlay
  (JNIEnv *, jobject, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL10
 * Method:    alSourceStop
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL10_alSourceStop
  (JNIEnv *, jobject, jint);

/*
 * Class:     org_lwjgl_openal_CoreAL10
 * Method:    alDeleteSources
 * Signature: (I[I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL10_alDeleteSources
  (JNIEnv *, jobject, jint, jintArray);

/*
 * Class:     org_lwjgl_openal_CoreAL10
 * Method:    alDeleteBuffers
 * Signature: (I[I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_openal_CoreAL10_alDeleteBuffers
  (JNIEnv *, jobject, jint, jintArray);

#ifdef __cplusplus
}
#endif
#endif
