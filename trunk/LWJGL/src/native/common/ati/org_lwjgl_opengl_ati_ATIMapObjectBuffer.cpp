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
// IMPLEMENTATION OF NATIVE METHODS FOR CLASS: org.lwjgl.opengl.ati.ATIMapObjectBuffer
// ----------------------------------

#include "org_lwjgl_opengl_ati_ATIMapObjectBuffer.h"
#include "extgl.h"
#include "checkGLerror.h"

typedef void * (APIENTRY * glMapObjectBufferATIPROC) (GLuint buffer);
typedef void (APIENTRY * glUnmapObjectBufferATIPROC) (GLuint buffer);

static glMapObjectBufferATIPROC glMapObjectBufferATI;
static glUnmapObjectBufferATIPROC glUnmapObjectBufferATI;

void extgl_InitATIMapObjectBuffer(JNIEnv *env, jobject ext_set)
{
	if (!extgl_Extensions.GL_ATI_map_object_buffer)
		return;

	glMapObjectBufferATI = (glMapObjectBufferATIPROC) extgl_GetProcAddress("glMapObjectBufferATI");
	glUnmapObjectBufferATI = (glUnmapObjectBufferATIPROC) extgl_GetProcAddress("glUnmapObjectBufferATI");

	EXTGL_SANITY_CHECK(env, ext_set, GL_ATI_map_object_buffer)
}

/*
 * Class:	org.lwjgl.opengl.ati.ATIMapObjectBuffer
 * Method:	glMapObjectBufferATI
 */
JNIEXPORT jobject JNICALL Java_org_lwjgl_opengl_ati_ATIMapObjectBuffer_glMapObjectBufferATI
	(JNIEnv * env, jclass clazz, jint buffer, jint size, jobject oldBuffer)
{
	CHECK_EXISTS(glMapObjectBufferATI)
	void *buffer_address = glMapObjectBufferATI((GLenum)buffer);
	CHECK_GL_ERROR
	if (oldBuffer != NULL) {
		void *old_buffer_address = env->GetDirectBufferAddress(oldBuffer);
		if (old_buffer_address == buffer_address)
			return oldBuffer;
	}

	return safeNewBuffer(env, buffer_address, size);
}

/*
 * Class:	org.lwjgl.opengl.ati.ATIMapObjectBuffer
 * Method:	glUnmapObjectBufferATI
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ati_ATIMapObjectBuffer_glUnmapObjectBufferATI
	(JNIEnv * env, jclass clazz, jint buffer)
{
	CHECK_EXISTS(glUnmapObjectBufferATI)
	glUnmapObjectBufferATI(buffer);
	CHECK_GL_ERROR
}
