/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_lwjgl_opengl_Pbuffer */

#ifndef _Included_org_lwjgl_opengl_Pbuffer
#define _Included_org_lwjgl_opengl_Pbuffer
#ifdef __cplusplus
extern "C" {
#endif
#undef org_lwjgl_opengl_Pbuffer_PBUFFER_SUPPORTED
#define org_lwjgl_opengl_Pbuffer_PBUFFER_SUPPORTED 1L
/* Inaccessible static: currentBuffer */
/*
 * Class:     org_lwjgl_opengl_Pbuffer
 * Method:    nIsBufferLost
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_Pbuffer_nIsBufferLost
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_opengl_Pbuffer
 * Method:    nReleaseContext
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_Pbuffer_nReleaseContext
  (JNIEnv *, jclass);

/*
 * Class:     org_lwjgl_opengl_Pbuffer
 * Method:    nMakeCurrent
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_Pbuffer_nMakeCurrent
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_opengl_Pbuffer
 * Method:    getPbufferCaps
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_Pbuffer_getPbufferCaps
  (JNIEnv *, jclass);

/*
 * Class:     org_lwjgl_opengl_Pbuffer
 * Method:    nCreate
 * Signature: (IIIIIII)I
 */
JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_Pbuffer_nCreate
  (JNIEnv *, jclass, jint, jint, jint, jint, jint, jint, jint);

/*
 * Class:     org_lwjgl_opengl_Pbuffer
 * Method:    nDestroy
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_Pbuffer_nDestroy
  (JNIEnv *, jclass, jint);

#ifdef __cplusplus
}
#endif
#endif
