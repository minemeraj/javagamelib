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
/*
 * Created by LWJGL.
 * User: spasi
 * Date: 2003-12-16
 * Time: 21:25:07
 */

package org.lwjgl.opengl.ati;

public interface ATITextureFloat {

	/*
	 * Accepted by the <internalFormat> parameter of TexImage1D,
	 * TexImage2D, and TexImage3D:
	*/
	public static final int GL_RGBA_FLOAT32_ATI = 0x8814;
	public static final int GL_RGB_FLOAT32_ATI = 0x8815;
	public static final int GL_ALPHA_FLOAT32_ATI = 0x8816;
	public static final int GL_INTENSITY_FLOAT32_ATI = 0x8817;
	public static final int GL_LUMINANCE_FLOAT32_ATI = 0x8818;
	public static final int GL_LUMINANCE_ALPHA_FLOAT32_ATI = 0x8819;
	public static final int GL_RGBA_FLOAT16_ATI = 0x881A;
	public static final int GL_RGB_FLOAT16_ATI = 0x881B;
	public static final int GL_ALPHA_FLOAT16_ATI = 0x881C;
	public static final int GL_INTENSITY_FLOAT16_ATI = 0x881D;
	public static final int GL_LUMINANCE_FLOAT16_ATI = 0x881E;
	public static final int GL_LUMINANCE_ALPHA_FLOAT16_ATI = 0x881F;

}