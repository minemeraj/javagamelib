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
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.BufferChecks;

public final class ARBVertexShader {

	/*
	 * Accepted by the <shaderType> argument of CreateShaderObjectARB and
	 * returned by the <params> parameter of GetObjectParameter{if}vARB:
	*/
	public static final int GL_VERTEX_SHADER_ARB = 0x8B31;

	/*
	 * Accepted by the <pname> parameter of GetBooleanv, GetIntegerv,
	 * GetFloatv, and GetDoublev:
	*/
	public static final int GL_MAX_VERTEX_UNIFORM_COMPONENTS_ARB = 0x8B4A;
	public static final int GL_MAX_VARYING_FLOATS_ARB = 0x8B4B;
	public static final int GL_MAX_VERTEX_ATTRIBS_ARB = 0x8869;
	public static final int GL_MAX_TEXTURE_IMAGE_UNITS_ARB = 0x8872;
	public static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS_ARB = 0x8B4C;
	public static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS_ARB = 0x8B4D;
	public static final int GL_MAX_TEXTURE_COORDS_ARB = 0x8871;

	/*
	 * Accepted by the <cap> parameter of Disable, Enable, and IsEnabled, and
	 * by the <pname> parameter of GetBooleanv, GetIntegerv, GetFloatv, and
	 * GetDoublev:
	*/
	public static final int GL_VERTEX_PROGRAM_POINT_SIZE_ARB = 0x8642;
	public static final int GL_VERTEX_PROGRAM_TWO_SIDE_ARB = 0x8643;

	/*
	* Accepted by the <pname> parameter GetObjectParameter{if}vARB:
	*/
	public static final int GL_OBJECT_ACTIVE_ATTRIBUTES_ARB = 0x8B89;
	public static final int GL_OBJECT_ACTIVE_ATTRIBUTE_MAX_LENGTH_ARB = 0x8B8A;

	/*
	 * Accepted by the <pname> parameter of GetVertexAttrib{dfi}vARB:
	*/
	public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED_ARB = 0x8622;
	public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE_ARB = 0x8623;
	public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE_ARB = 0x8624;
	public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE_ARB = 0x8625;
	public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED_ARB = 0x886A;
	public static final int GL_CURRENT_VERTEX_ATTRIB_ARB = 0x8626;

	/*
	 * Accepted by the <pname> parameter of GetVertexAttribPointervARB:
	*/
	public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER_ARB = 0x8645;

	/*
	 * Returned by the <type> parameter of GetActiveAttribARB:
	*/
	public static final int GL_FLOAT = 0x1406;
	public static final int GL_FLOAT_VEC2_ARB = 0x8B50;
	public static final int GL_FLOAT_VEC3_ARB = 0x8B51;
	public static final int GL_FLOAT_VEC4_ARB = 0x8B52;
	public static final int GL_FLOAT_MAT2_ARB = 0x8B5A;
	public static final int GL_FLOAT_MAT3_ARB = 0x8B5B;
	public static final int GL_FLOAT_MAT4_ARB = 0x8B5C;

	static native void initNativeStubs() throws LWJGLException;

	// ---------------------------
	public static void glBindAttribLocationARB(int programObj, int index, ByteBuffer name) {
		BufferChecks.checkDirect(name);
		if ( name.get(name.limit() - 1) != 0 ) {
			throw new IllegalArgumentException("<name> must be a null-terminated string.");
		}
		nglBindAttribLocationARB(programObj, index, name, name.position());
	}

	private static native void nglBindAttribLocationARB(int programObj, int index, ByteBuffer name, int nameOffset);
	// ---------------------------

	// ---------------------------
	public static void glGetActiveAttribARB(int programObj, int index,
	                                        IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name) {
		BufferChecks.checkDirect(name);
		BufferChecks.checkDirect(size);
		BufferChecks.checkDirect(type);

		if ( length == null ) {
			nglGetActiveAttribARB(programObj, index, name.remaining(), null, -1,
			                      size, size.position(), type, type.position(), name, name.position());
		} else {
			BufferChecks.checkDirect(length);
			nglGetActiveAttribARB(programObj, index, name.remaining(), length, length.position(),
			                      size, size.position(), type, type.position(), name, name.position());
		}
	}

	private static native void nglGetActiveAttribARB(int programObj, int index, int maxLength,
	                                                 IntBuffer length, int lengthOffset,
	                                                 IntBuffer size, int sizeOffset,
	                                                 IntBuffer type, int typeOffset,
	                                                 ByteBuffer name, int nameOffset);
	// ---------------------------

	// ---------------------------
	public static int glGetAttribLocationARB(int programObj, ByteBuffer name) {
		BufferChecks.checkDirect(name);
		if ( name.get(name.limit() - 1) != 0 ) {
			throw new IllegalArgumentException("<name> must be a null-terminated string.");
		}
		return nglGetAttribLocationARB(programObj, name, name.position());
	}

	private static native int nglGetAttribLocationARB(int programObj, ByteBuffer name, int nameOffset);
	// ---------------------------

}
