/*
 * Copyright (c) 2002-2004 LWJGL Project
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
 * * Neither the name of 'LWJGL' nor the names of
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

// ----------------------------------
// IMPLEMENTATION OF NATIVE METHODS FOR CLASS: org.lwjgl.opengl.ARBTextureCompression
// ----------------------------------

#include "extgl.h"

typedef void (APIENTRY * glCompressedTexImage1DARBPROC) (GLenum target, GLint level, GLenum internalformat, GLsizei width, GLint border, GLsizei imageSize, const GLvoid *data );
typedef void (APIENTRY * glCompressedTexImage2DARBPROC) (GLenum target, GLint level, GLenum internalformat, GLsizei width, GLsizei height, GLint border, GLsizei imageSize, const GLvoid *data );
typedef void (APIENTRY * glCompressedTexImage3DARBPROC) (GLenum target, GLint level, GLenum internalformat, GLsizei width, GLsizei height, GLsizei depth, GLint border, GLsizei imageSize, const GLvoid *data );
typedef void (APIENTRY * glCompressedTexSubImage1DARBPROC) (GLenum target, GLint level, GLint xoffset, GLsizei width, GLenum format, GLsizei imageSize, const GLvoid *data );
typedef void (APIENTRY * glCompressedTexSubImage2DARBPROC) (GLenum target, GLint level, GLint xoffset, GLint yoffset, GLsizei width, GLsizei height, GLenum format, GLsizei imageSize, const GLvoid *data );
typedef void (APIENTRY * glCompressedTexSubImage3DARBPROC) (GLenum target, GLint level, GLint xoffset, GLint yoffset, GLint zoffset, GLsizei width, GLsizei height, GLsizei depth, GLenum format, GLsizei imageSize, const GLvoid *data );
typedef void (APIENTRY * glGetCompressedTexImageARBPROC) (GLenum target, GLint lod, GLvoid *img );

static glCompressedTexImage3DARBPROC glCompressedTexImage3DARB;
static glCompressedTexImage2DARBPROC glCompressedTexImage2DARB;
static glCompressedTexImage1DARBPROC glCompressedTexImage1DARB;
static glCompressedTexSubImage3DARBPROC glCompressedTexSubImage3DARB;
static glCompressedTexSubImage2DARBPROC glCompressedTexSubImage2DARB;
static glCompressedTexSubImage1DARBPROC glCompressedTexSubImage1DARB;
static glGetCompressedTexImageARBPROC glGetCompressedTexImageARB;

/*
 * Class:	org.lwjgl.opengl.ARBTextureCompression
 * Method:	nglCompressedTexImage1DARB
 */
static void JNICALL Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexImage1DARB
	(JNIEnv * env, jclass clazz, jint target, jint level, jint internalformat, jint width, jint border, jint imageSize, jobject pData, jint pData_offset)
{
	GLvoid *pData_ptr = (GLvoid *)((GLubyte *)(*env)->GetDirectBufferAddress(env, pData) + pData_offset);
	glCompressedTexImage1DARB(target, level, internalformat, width, border, imageSize, pData_ptr);
}

/*
 * Class:	org.lwjgl.opengl.ARBTextureCompression
 * Method:	nglCompressedTexImage1DARBBO
 */
static void JNICALL Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexImage1DARBBO
	(JNIEnv * env, jclass clazz, jint target, jint level, jint internalformat, jint width, jint border, jint imageSize, jint buffer_offset)
{
	glCompressedTexImage1DARB(target, level, internalformat, width, border, imageSize, offsetToPointer(buffer_offset));
}

/*
 * Class:	org.lwjgl.opengl.ARBTextureCompression
 * Method:	nglCompressedTexImage2DARB
 */
static void JNICALL Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexImage2DARB
	(JNIEnv * env, jclass clazz, jint target, jint level, jint internalformat, jint width, jint height, jint border, jint imageSize, jobject pData, jint pData_offset)
{
	GLvoid *pData_ptr = (GLvoid *)((GLubyte *)(*env)->GetDirectBufferAddress(env, pData) + pData_offset);
	glCompressedTexImage2DARB(target, level, internalformat, width, height, border, imageSize, pData_ptr);
}

/*
 * Class:	org.lwjgl.opengl.ARBTextureCompression
 * Method:	nglCompressedTexImage2DARBBO
 */
static void JNICALL Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexImage2DARBBO
	(JNIEnv * env, jclass clazz, jint target, jint level, jint internalformat, jint width, jint height, jint border, jint imageSize, jint buffer_offset)
{
	glCompressedTexImage2DARB(target, level, internalformat, width, height, border, imageSize, offsetToPointer(buffer_offset));
}

/*
 * Class:	org.lwjgl.opengl.ARBTextureCompression
 * Method:	nglCompressedTexImage3DARB
 */
static void JNICALL Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexImage3DARB
	(JNIEnv * env, jclass clazz, jint target, jint level, jint internalformat, jint width, jint height, jint depth, jint border, jint imageSize, jobject pData, jint pData_offset)
{
	GLvoid *pData_ptr = (GLvoid *)((GLubyte *)(*env)->GetDirectBufferAddress(env, pData) + pData_offset);
	glCompressedTexImage3DARB(target, level, internalformat, width, height, depth, border, imageSize, pData_ptr);
}

/*
 * Class:	org.lwjgl.opengl.ARBTextureCompression
 * Method:	nglCompressedTexImage3DARBBO
 */
static void JNICALL Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexImage3DARBBO
	(JNIEnv * env, jclass clazz, jint target, jint level, jint internalformat, jint width, jint height, jint depth, jint border, jint imageSize, jint buffer_offset)
{
	glCompressedTexImage3DARB(target, level, internalformat, width, height, depth, border, imageSize, offsetToPointer(buffer_offset));
}

/*
 * Class:	org.lwjgl.opengl.ARBTextureCompression
 * Method:	nglCompressedTexSubImage1DARB
 */
static void JNICALL Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexSubImage1DARB
	(JNIEnv * env, jclass clazz, jint target, jint level, jint xoffset, jint width, jint border, jint imageSize, jobject pData, jint pData_offset)
{
	GLvoid *pData_ptr = (GLvoid *)((GLubyte *)(*env)->GetDirectBufferAddress(env, pData) + pData_offset);
	glCompressedTexSubImage1DARB(target, level, xoffset, width, border, imageSize, pData_ptr);
}

/*
 * Class:	org.lwjgl.opengl.ARBTextureCompression
 * Method:	nglCompressedTexSubImage1DARBBO
 */
static void JNICALL Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexSubImage1DARBBO
	(JNIEnv * env, jclass clazz, jint target, jint level, jint xoffset, jint width, jint border, jint imageSize, jint buffer_offset)
{
	glCompressedTexSubImage1DARB(target, level, xoffset, width, border, imageSize, offsetToPointer(buffer_offset));
}

/*
 * Class:	org.lwjgl.opengl.ARBTextureCompression
 * Method:	nglCompressedTexSubImage2DARB
 */
static void JNICALL Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexSubImage2DARB
	(JNIEnv * env, jclass clazz, jint target, jint level, jint xoffset, jint yoffset, jint width, jint height, jint border, jint imageSize, jobject pData, jint pData_offset)
{
	GLvoid *pData_ptr = (GLvoid *)((GLubyte *)(*env)->GetDirectBufferAddress(env, pData) + pData_offset);
	glCompressedTexSubImage2DARB(target, level, xoffset, yoffset, width, height, border, imageSize, pData_ptr);
}

/*
 * Class:	org.lwjgl.opengl.ARBTextureCompression
 * Method:	nglCompressedTexSubImage2DARBBO
 */
static void JNICALL Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexSubImage2DARBBO
	(JNIEnv * env, jclass clazz, jint target, jint level, jint xoffset, jint yoffset, jint width, jint height, jint border, jint imageSize, jint buffer_offset)
{
	glCompressedTexSubImage2DARB(target, level, xoffset, yoffset, width, height, border, imageSize, offsetToPointer(buffer_offset));
}

/*
 * Class:	org.lwjgl.opengl.ARBTextureCompression
 * Method:	nglCompressedTexSubImage3DARB
 */
static void JNICALL Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexSubImage3DARB
	(JNIEnv * env, jclass clazz, jint target, jint level, jint xoffset, jint yoffset, jint zoffset, jint width, jint height, jint depth, jint border, jint imageSize, jobject pData, jint pData_offset)
{
	GLvoid *pData_ptr = (GLvoid *)((GLubyte *)(*env)->GetDirectBufferAddress(env, pData) + pData_offset);
	glCompressedTexSubImage3DARB(target, level, xoffset, yoffset, zoffset, width, height, depth, border, imageSize, pData_ptr);
}

/*
 * Class:	org.lwjgl.opengl.ARBTextureCompression
 * Method:	nglCompressedTexSubImage3DARBBO
 */
static void JNICALL Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexSubImage3DARBBO
	(JNIEnv * env, jclass clazz, jint target, jint level, jint xoffset, jint yoffset, jint zoffset, jint width, jint height, jint depth, jint border, jint imageSize, jint buffer_offset)
{
	glCompressedTexSubImage3DARB(target, level, xoffset, yoffset, zoffset, width, height, depth, border, imageSize, offsetToPointer(buffer_offset));
}

/*
 * Class:	org.lwjgl.opengl.ARBTextureCompression
 * Method:	nglGetCompressedTexImageARB
 */
static void JNICALL Java_org_lwjgl_opengl_ARBTextureCompression_nglGetCompressedTexImageARB
	(JNIEnv * env, jclass clazz, jint target, jint lod, jobject pImg, jint pImg_offset)
{
	GLvoid *pImg_ptr = (GLvoid *)((GLubyte *)(*env)->GetDirectBufferAddress(env, pImg) + pImg_offset);
	glGetCompressedTexImageARB(target, lod, pImg_ptr);
}

/*
 * Class:	org.lwjgl.opengl.ARBTextureCompression
 * Method:	nglGetCompressedTexImageARBBO
 */
static void JNICALL Java_org_lwjgl_opengl_ARBTextureCompression_nglGetCompressedTexImageARBBO
	(JNIEnv * env, jclass clazz, jint target, jint lod, jint buffer_offset)
{
	glGetCompressedTexImageARB(target, lod, offsetToPointer(buffer_offset));
}

#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTextureCompression_initNativeStubs(JNIEnv *env, jclass clazz) {
	JavaMethodAndExtFunction functions[] = {
		{"nglCompressedTexImage1DARB", "(IIIIIILjava/nio/Buffer;I)V", (void*)&Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexImage1DARB, "glCompressedTexImage1DARB", (void*)&glCompressedTexImage1DARB},
		{"nglCompressedTexImage1DARBBO", "(IIIIIII)V", (void*)&Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexImage1DARBBO, NULL, NULL},
		{"nglCompressedTexImage2DARB", "(IIIIIIILjava/nio/Buffer;I)V", (void*)&Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexImage2DARB, "glCompressedTexImage2DARB", (void*)&glCompressedTexImage2DARB},
		{"nglCompressedTexImage2DARBBO", "(IIIIIIII)V", (void*)&Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexImage2DARBBO, NULL, NULL},
		{"nglCompressedTexImage3DARB", "(IIIIIIIILjava/nio/Buffer;I)V", (void*)&Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexImage3DARB, "glCompressedTexImage3DARB", (void*)&glCompressedTexImage3DARB},
		{"nglCompressedTexImage3DARBBO", "(IIIIIIIII)V", (void*)&Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexImage3DARBBO, NULL, NULL},
		{"nglCompressedTexSubImage1DARB", "(IIIIIILjava/nio/Buffer;I)V", (void*)&Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexSubImage1DARB, "glCompressedTexSubImage1DARB", (void*)&glCompressedTexSubImage1DARB},
		{"nglCompressedTexSubImage1DARBBO", "(IIIIIII)V", (void*)&Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexSubImage1DARBBO, NULL, NULL},
		{"nglCompressedTexSubImage2DARB", "(IIIIIIIILjava/nio/Buffer;I)V", (void*)&Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexSubImage2DARB, "glCompressedTexSubImage2DARB", (void*)&glCompressedTexSubImage2DARB},
		{"nglCompressedTexSubImage2DARBBO", "(IIIIIIIII)V", (void*)&Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexSubImage2DARBBO, NULL, NULL},
		{"nglCompressedTexSubImage3DARB", "(IIIIIIIIIILjava/nio/Buffer;I)V", (void*)&Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexSubImage3DARB, "glCompressedTexSubImage3DARB", (void*)&glCompressedTexSubImage3DARB},
		{"nglCompressedTexSubImage3DARBBO", "(IIIIIIIIIII)V", (void*)&Java_org_lwjgl_opengl_ARBTextureCompression_nglCompressedTexSubImage3DARBBO, NULL, NULL},
		{"nglGetCompressedTexImageARB", "(IILjava/nio/Buffer;I)V", (void*)&Java_org_lwjgl_opengl_ARBTextureCompression_nglGetCompressedTexImageARB, "glGetCompressedTexImageARB", (void*)&glGetCompressedTexImageARB},
		{"nglGetCompressedTexImageARBBO", "(III)V", (void*)&Java_org_lwjgl_opengl_ARBTextureCompression_nglGetCompressedTexImageARBBO, NULL, NULL}
	};
	int num_functions = NUMFUNCTIONS(functions);
	extgl_InitializeClass(env, clazz, num_functions, functions);
}
#ifdef __cplusplus
}
#endif