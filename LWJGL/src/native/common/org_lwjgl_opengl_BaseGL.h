/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_lwjgl_opengl_BaseGL */

#ifndef _Included_org_lwjgl_opengl_BaseGL
#define _Included_org_lwjgl_opengl_BaseGL
#ifdef __cplusplus
extern "C" {
#endif
/* Inaccessible static: _00024assertionsDisabled */
/* Inaccessible static: currentContext */
/* Inaccessible static: class_00024org_00024lwjgl_00024opengl_00024BaseGL */
/*
 * Class:     org_lwjgl_opengl_BaseGL
 * Method:    nCreate
 * Signature: (IIII)Z
 */
JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_BaseGL_nCreate
  (JNIEnv *, jobject, jint, jint, jint, jint);

/*
 * Class:     org_lwjgl_opengl_BaseGL
 * Method:    nDestroy
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_BaseGL_nDestroy
  (JNIEnv *, jobject);

/*
 * Class:     org_lwjgl_opengl_BaseGL
 * Method:    swapBuffers
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_BaseGL_swapBuffers
  (JNIEnv *, jobject);

/*
 * Class:     org_lwjgl_opengl_BaseGL
 * Method:    nReleaseContext
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_BaseGL_nReleaseContext
  (JNIEnv *, jobject);

/*
 * Class:     org_lwjgl_opengl_BaseGL
 * Method:    nMakeCurrent
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_BaseGL_nMakeCurrent
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
