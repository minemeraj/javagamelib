/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glMultTransposeMatrixfARBPROC) (GLfloat * pfMtx);
typedef void (APIENTRY *glLoadTransposeMatrixfARBPROC) (GLfloat * pfMtx);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTransposeMatrix_nglMultTransposeMatrixfARB(JNIEnv *env, jclass clazz, jobject pfMtx, jint pfMtx_position, jlong function_pointer) {
	GLfloat *pfMtx_address = ((GLfloat *)(*env)->GetDirectBufferAddress(env, pfMtx)) + pfMtx_position;
	glMultTransposeMatrixfARBPROC glMultTransposeMatrixfARB = (glMultTransposeMatrixfARBPROC)((intptr_t)function_pointer);
	glMultTransposeMatrixfARB(pfMtx_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTransposeMatrix_nglLoadTransposeMatrixfARB(JNIEnv *env, jclass clazz, jobject pfMtx, jint pfMtx_position, jlong function_pointer) {
	GLfloat *pfMtx_address = ((GLfloat *)(*env)->GetDirectBufferAddress(env, pfMtx)) + pfMtx_position;
	glLoadTransposeMatrixfARBPROC glLoadTransposeMatrixfARB = (glLoadTransposeMatrixfARBPROC)((intptr_t)function_pointer);
	glLoadTransposeMatrixfARB(pfMtx_address);
}

