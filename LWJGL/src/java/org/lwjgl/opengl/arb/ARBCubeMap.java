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
 * Created by IntelliJ IDEA.
 * User: nj
 * Date: 12-08-2002
 * Time: 15:11:04
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.lwjgl.opengl.arb;

public interface ARBCubeMap
{
  public static final int NORMAL_MAP_ARB                                       = 0x8511;
  public static final int REFLECTION_MAP_ARB                                   = 0x8512;
  public static final int TEXTURE_CUBE_MAP_ARB                                 = 0x8513;
  public static final int TEXTURE_BINDING_CUBE_MAP_ARB                         = 0x8514;
  public static final int TEXTURE_CUBE_MAP_POSITIVE_X_ARB                      = 0x8515;
  public static final int TEXTURE_CUBE_MAP_NEGATIVE_X_ARB                      = 0x8516;
  public static final int TEXTURE_CUBE_MAP_POSITIVE_Y_ARB                      = 0x8517;
  public static final int TEXTURE_CUBE_MAP_NEGATIVE_Y_ARB                      = 0x8518;
  public static final int TEXTURE_CUBE_MAP_POSITIVE_Z_ARB                      = 0x8519;
  public static final int TEXTURE_CUBE_MAP_NEGATIVE_Z_ARB                      = 0x851A;
  public static final int PROXY_TEXTURE_CUBE_MAP_ARB                           = 0x851B;
  public static final int MAX_CUBE_MAP_TEXTURE_SIZE_ARB                        = 0x851C;
}
