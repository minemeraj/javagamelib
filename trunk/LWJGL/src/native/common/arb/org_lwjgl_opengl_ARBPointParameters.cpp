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

// ----------------------------------
// IMPLEMENTATION OF NATIVE METHODS FOR CLASS: org.lwjgl.opengl.ARBPointParameters
// ----------------------------------

#include "extgl.h"
#include "checkGLerror.h"

typedef void (APIENTRY * glPointParameterfARBPROC) (GLenum pname, GLfloat param);
typedef void (APIENTRY * glPointParameterfvARBPROC) (GLenum pname, GLfloat *params);

static glPointParameterfARBPROC glPointParameterfARB;
static glPointParameterfvARBPROC glPointParameterfvARB;

/*
 * Class:	org.lwjgl.opengl.ARBPointParameters
 * Method:	glPointParameterfARB
 */
static void JNICALL Java_org_lwjgl_opengl_ARBPointParameters_glPointParameterfARB
	(JNIEnv * env, jclass clazz, jint pname, jfloat param)
{
	glPointParameterfARB(pname, param);
	CHECK_GL_ERROR
}

/*
 * Class:	org.lwjgl.opengl.ARBPointParameters
 * Method:	nglPointParameterfvARB
 */
static void JNICALL Java_org_lwjgl_opengl_ARBPointParameters_nglPointParameterfvARB
	(JNIEnv * env, jclass clazz, jint pname, jobject pfParams, jint pfParams_offset)
{
	GLfloat *pfParams_ptr = (GLfloat *)env->GetDirectBufferAddress(pfParams) + pfParams_offset;
	glPointParameterfvARB(pname, pfParams_ptr);
	CHECK_GL_ERROR
}

void extgl_InitARBPointParameters(JNIEnv *env, jobject ext_set)
{
	JavaMethodAndGLFunction functions[] = {
		{"glPointParameterfARB", "(IF)V", (void*)&Java_org_lwjgl_opengl_ARBPointParameters_glPointParameterfARB, "glPointParameterfARB", (void**)&glPointParameterfARB},
		{"nglPointParameterfvARB", "(ILjava/nio/FloatBuffer;I)V", (void*)&Java_org_lwjgl_opengl_ARBPointParameters_nglPointParameterfvARB, "glPointParameterfvARB", (void**)&glPointParameterfvARB}
	};
	int num_functions = NUMFUNCTIONS(functions);
	jclass clazz = extgl_ResetClass(env, "org/lwjgl/opengl/ARBPointParameters");
	if (extgl_Extensions.GL_ARB_point_parameters)
		extgl_InitializeClass(env, clazz, ext_set, "GL_ARB_point_parameters", num_functions, functions);
}

