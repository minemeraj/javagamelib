/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glCurrentPaletteMatrixARBPROC) (GLint index);
typedef void (APIENTRY *glMatrixIndexPointerARBPROC) (GLint size, GLenum type, GLsizei stride, GLvoid * pPointer);
typedef void (APIENTRY *glMatrixIndexubvARBPROC) (GLint size, GLubyte * pIndices);
typedef void (APIENTRY *glMatrixIndexusvARBPROC) (GLint size, GLushort * pIndices);
typedef void (APIENTRY *glMatrixIndexuivARBPROC) (GLint size, GLuint * pIndices);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBMatrixPalette_nglCurrentPaletteMatrixARB(JNIEnv *env, jclass clazz, jint index, jlong function_pointer) {
	glCurrentPaletteMatrixARBPROC glCurrentPaletteMatrixARB = (glCurrentPaletteMatrixARBPROC)((intptr_t)function_pointer);
	glCurrentPaletteMatrixARB(index);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBMatrixPalette_nglMatrixIndexPointerARB(JNIEnv *env, jclass clazz, jint size, jint type, jint stride, jobject pPointer, jint pPointer_position, jlong function_pointer) {
	GLvoid *pPointer_address = ((GLvoid *)(((char *)(*env)->GetDirectBufferAddress(env, pPointer)) + pPointer_position));
	glMatrixIndexPointerARBPROC glMatrixIndexPointerARB = (glMatrixIndexPointerARBPROC)((intptr_t)function_pointer);
	glMatrixIndexPointerARB(size, type, stride, pPointer_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBMatrixPalette_nglMatrixIndexPointerARBBO(JNIEnv *env, jclass clazz, jint size, jint type, jint stride, jint pPointer_buffer_offset, jlong function_pointer) {
	GLvoid *pPointer_address = ((GLvoid *)offsetToPointer(pPointer_buffer_offset));
	glMatrixIndexPointerARBPROC glMatrixIndexPointerARB = (glMatrixIndexPointerARBPROC)((intptr_t)function_pointer);
	glMatrixIndexPointerARB(size, type, stride, pPointer_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBMatrixPalette_nglMatrixIndexubvARB(JNIEnv *env, jclass clazz, jint size, jobject pIndices, jint pIndices_position, jlong function_pointer) {
	GLubyte *pIndices_address = ((GLubyte *)(*env)->GetDirectBufferAddress(env, pIndices)) + pIndices_position;
	glMatrixIndexubvARBPROC glMatrixIndexubvARB = (glMatrixIndexubvARBPROC)((intptr_t)function_pointer);
	glMatrixIndexubvARB(size, pIndices_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBMatrixPalette_nglMatrixIndexusvARB(JNIEnv *env, jclass clazz, jint size, jobject pIndices, jint pIndices_position, jlong function_pointer) {
	GLushort *pIndices_address = ((GLushort *)(*env)->GetDirectBufferAddress(env, pIndices)) + pIndices_position;
	glMatrixIndexusvARBPROC glMatrixIndexusvARB = (glMatrixIndexusvARBPROC)((intptr_t)function_pointer);
	glMatrixIndexusvARB(size, pIndices_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBMatrixPalette_nglMatrixIndexuivARB(JNIEnv *env, jclass clazz, jint size, jobject pIndices, jint pIndices_position, jlong function_pointer) {
	GLuint *pIndices_address = ((GLuint *)(*env)->GetDirectBufferAddress(env, pIndices)) + pIndices_position;
	glMatrixIndexuivARBPROC glMatrixIndexuivARB = (glMatrixIndexuivARBPROC)((intptr_t)function_pointer);
	glMatrixIndexuivARB(size, pIndices_address);
}

