/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_lwjgl_Sys */

#ifndef _Included_org_lwjgl_Sys
#define _Included_org_lwjgl_Sys
#ifdef __cplusplus
extern "C" {
#endif
#undef org_lwjgl_Sys_LOW_PRIORITY
#define org_lwjgl_Sys_LOW_PRIORITY -1L
#undef org_lwjgl_Sys_NORMAL_PRIORITY
#define org_lwjgl_Sys_NORMAL_PRIORITY 0L
#undef org_lwjgl_Sys_HIGH_PRIORITY
#define org_lwjgl_Sys_HIGH_PRIORITY 1L
#undef org_lwjgl_Sys_REALTIME_PRIORITY
#define org_lwjgl_Sys_REALTIME_PRIORITY 2L
/* Inaccessible static: LIBRARY_NAME */
/* Inaccessible static: debug */
/*
 * Class:     org_lwjgl_Sys
 * Method:    setDebug
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_Sys_setDebug
  (JNIEnv *, jclass, jboolean);

/*
 * Class:     org_lwjgl_Sys
 * Method:    getTimerResolution
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_lwjgl_Sys_getTimerResolution
  (JNIEnv *, jclass);

/*
 * Class:     org_lwjgl_Sys
 * Method:    getTime
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_lwjgl_Sys_getTime
  (JNIEnv *, jclass);

/*
 * Class:     org_lwjgl_Sys
 * Method:    setTime
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_Sys_setTime
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_lwjgl_Sys
 * Method:    setProcessPriority
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_Sys_setProcessPriority
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_Sys
 * Method:    alert
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_Sys_alert
  (JNIEnv *, jclass, jstring, jstring);

/*
 * Class:     org_lwjgl_Sys
 * Method:    nOpenURL
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_Sys_nOpenURL
  (JNIEnv *, jclass, jstring);

#ifdef __cplusplus
}
#endif
#endif
