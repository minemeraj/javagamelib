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
 * Date: 2003-11-28
 * Time: 18:59:45
 */

package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public final class NVFragmentProgram extends NVProgram {

  /*
  Accepted by the <cap> parameter of Disable, Enable, and IsEnabled, by the
  <pname> parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev,
  and by the <target> parameter of BindProgramNV, LoadProgramNV,
  ProgramLocalParameter4dARB, ProgramLocalParameter4dvARB,
  ProgramLocalParameter4fARB, ProgramLocalParameter4fvARB,
  GetProgramLocalParameterdvARB, and GetProgramLocalParameterfvARB:
  */

  public static final int GL_FRAGMENT_PROGRAM_NV = 0x8870;

  /*
  Accepted by the <pname> parameter of GetBooleanv, GetIntegerv, GetFloatv,
  and GetDoublev:
  */

  public static final int GL_MAX_TEXTURE_COORDS_NV = 0x8871;

  public static final int GL_MAX_TEXTURE_IMAGE_UNITS_NV = 0x8872;

  public static final int GL_FRAGMENT_PROGRAM_BINDING_NV = 0x8873;

  public static final int GL_MAX_FRAGMENT_PROGRAM_LOCAL_PARAMETERS_NV = 0x8868;

  // ---------------------------

  public static void glProgramNamedParameter4fNV(int id, ByteBuffer name, float x, float y, float z, float w) {

    nglProgramNamedParameter4fNV(id, name.remaining(), name, name.position(), x, y, z, w);

  }

  private static native void nglProgramNamedParameter4fNV(
    int id,
    int length,
    ByteBuffer name,
    int nameOffset,
    float x,
    float y,
    float z,
    float w);

  // ---------------------------

  // ---------------------------

  public static void glGetProgramNamedParameterNV(int id, ByteBuffer name, FloatBuffer params) {

  	BufferChecks.checkBuffer(params);
  	nglGetProgramNamedParameterfvNV(id, name.remaining(), name, name.position(), params, params.position());

  }

  private static native void nglGetProgramNamedParameterfvNV(
    int id,
    int length,
    ByteBuffer name,
    int nameOffset,
    FloatBuffer params,
    int paramsOffset);

  // ---------------------------

  public static native void glProgramLocalParameter4fARB(int target, int index, float x, float y, float z, float w);

  // ---------------------------

  public static void glGetProgramLocalParameterARB(int target, int index, FloatBuffer params) {

  	BufferChecks.checkBuffer(params);
  	
    nglGetProgramLocalParameterfvARB(target, index, params, params.position());

  }

  private static native void nglGetProgramLocalParameterfvARB(
    int target,
    int index,
    FloatBuffer params,
    int params_offset);

  // ---------------------------

}
