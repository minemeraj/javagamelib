/* 
 * Copyright (c) 2002 Light Weight Java Game Library Project
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
 * Time: 15:34:05
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 *
 * Note: 2X_BIT_ATI, 4X_BIT_ATI and 8X_BIT_ATI has been changed to X2_BIT_ATI, X4_BIT_ATI and X8_BIT_ATI
 * because variables cannot start with a number.
 *
 */
package org.lwjgl.opengl.ati;

public interface ATIFragmentShader
{
  public static final int FRAGMENT_SHADER_ATI                                  = 0x8920;
  public static final int REG_0_ATI                                            = 0x8921;
  public static final int REG_1_ATI                                            = 0x8922;
  public static final int REG_2_ATI                                            = 0x8923;
  public static final int REG_3_ATI                                            = 0x8924;
  public static final int REG_4_ATI                                            = 0x8925;
  public static final int REG_5_ATI                                            = 0x8926;
  public static final int REG_6_ATI                                            = 0x8927;
  public static final int REG_7_ATI                                            = 0x8928;
  public static final int REG_8_ATI                                            = 0x8929;
  public static final int REG_9_ATI                                            = 0x892A;
  public static final int REG_10_ATI                                           = 0x892B;
  public static final int REG_11_ATI                                           = 0x892C;
  public static final int REG_12_ATI                                           = 0x892D;
  public static final int REG_13_ATI                                           = 0x892E;
  public static final int REG_14_ATI                                           = 0x892F;
  public static final int REG_15_ATI                                           = 0x8930;
  public static final int REG_16_ATI                                           = 0x8931;
  public static final int REG_17_ATI                                           = 0x8932;
  public static final int REG_18_ATI                                           = 0x8933;
  public static final int REG_19_ATI                                           = 0x8934;
  public static final int REG_20_ATI                                           = 0x8935;
  public static final int REG_21_ATI                                           = 0x8936;
  public static final int REG_22_ATI                                           = 0x8937;
  public static final int REG_23_ATI                                           = 0x8938;
  public static final int REG_24_ATI                                           = 0x8939;
  public static final int REG_25_ATI                                           = 0x893A;
  public static final int REG_26_ATI                                           = 0x893B;
  public static final int REG_27_ATI                                           = 0x893C;
  public static final int REG_28_ATI                                           = 0x893D;
  public static final int REG_29_ATI                                           = 0x893E;
  public static final int REG_30_ATI                                           = 0x893F;
  public static final int REG_31_ATI                                           = 0x8940;
  public static final int CON_0_ATI                                            = 0x8941;
  public static final int CON_1_ATI                                            = 0x8942;
  public static final int CON_2_ATI                                            = 0x8943;
  public static final int CON_3_ATI                                            = 0x8944;
  public static final int CON_4_ATI                                            = 0x8945;
  public static final int CON_5_ATI                                            = 0x8946;
  public static final int CON_6_ATI                                            = 0x8947;
  public static final int CON_7_ATI                                            = 0x8948;
  public static final int CON_8_ATI                                            = 0x8949;
  public static final int CON_9_ATI                                            = 0x894A;
  public static final int CON_10_ATI                                           = 0x894B;
  public static final int CON_11_ATI                                           = 0x894C;
  public static final int CON_12_ATI                                           = 0x894D;
  public static final int CON_13_ATI                                           = 0x894E;
  public static final int CON_14_ATI                                           = 0x894F;
  public static final int CON_15_ATI                                           = 0x8950;
  public static final int CON_16_ATI                                           = 0x8951;
  public static final int CON_17_ATI                                           = 0x8952;
  public static final int CON_18_ATI                                           = 0x8953;
  public static final int CON_19_ATI                                           = 0x8954;
  public static final int CON_20_ATI                                           = 0x8955;
  public static final int CON_21_ATI                                           = 0x8956;
  public static final int CON_22_ATI                                           = 0x8957;
  public static final int CON_23_ATI                                           = 0x8958;
  public static final int CON_24_ATI                                           = 0x8959;
  public static final int CON_25_ATI                                           = 0x895A;
  public static final int CON_26_ATI                                           = 0x895B;
  public static final int CON_27_ATI                                           = 0x895C;
  public static final int CON_28_ATI                                           = 0x895D;
  public static final int CON_29_ATI                                           = 0x895E;
  public static final int CON_30_ATI                                           = 0x895F;
  public static final int CON_31_ATI                                           = 0x8960;
  public static final int MOV_ATI                                              = 0x8961;
  public static final int ADD_ATI                                              = 0x8963;
  public static final int MUL_ATI                                              = 0x8964;
  public static final int SUB_ATI                                              = 0x8965;
  public static final int DOT3_ATI                                             = 0x8966;
  public static final int DOT4_ATI                                             = 0x8967;
  public static final int MAD_ATI                                              = 0x8968;
  public static final int LERP_ATI                                             = 0x8969;
  public static final int CND_ATI                                              = 0x896A;
  public static final int CND0_ATI                                             = 0x896B;
  public static final int DOT2_ADD_ATI                                         = 0x896C;
  public static final int SECONDARY_INTERPOLATOR_ATI                           = 0x896D;
  public static final int NUM_FRAGMENT_REGISTERS_ATI                           = 0x896E;
  public static final int NUM_FRAGMENT_CONSTANTS_ATI                           = 0x896F;
  public static final int NUM_PASSES_ATI                                       = 0x8970;
  public static final int NUM_INSTRUCTIONS_PER_PASS_ATI                        = 0x8971;
  public static final int NUM_INSTRUCTIONS_TOTAL_ATI                           = 0x8972;
  public static final int NUM_INPUT_INTERPOLATOR_COMPONENTS_ATI                = 0x8973;
  public static final int NUM_LOOPBACK_COMPONENTS_ATI                          = 0x8974;
  public static final int COLOR_ALPHA_PAIRING_ATI                              = 0x8975;
  public static final int SWIZZLE_STR_ATI                                      = 0x8976;
  public static final int SWIZZLE_STQ_ATI                                      = 0x8977;
  public static final int SWIZZLE_STR_DR_ATI                                   = 0x8978;
  public static final int SWIZZLE_STQ_DQ_ATI                                   = 0x8979;
  public static final int SWIZZLE_STRQ_ATI                                     = 0x897A;
  public static final int SWIZZLE_STRQ_DQ_ATI                                  = 0x897B;
  public static final int RED_BIT_ATI                                          = 0x00000001;
  public static final int GREEN_BIT_ATI                                        = 0x00000002;
  public static final int BLUE_BIT_ATI                                         = 0x00000004;
  public static final int X2_BIT_ATI                                           = 0x00000001;
  public static final int X4_BIT_ATI                                           = 0x00000002;
  public static final int X8_BIT_ATI                                           = 0x00000004;
  public static final int HALF_BIT_ATI                                         = 0x00000008;
  public static final int QUARTER_BIT_ATI                                      = 0x00000010;
  public static final int EIGHTH_BIT_ATI                                       = 0x00000020;
  public static final int SATURATE_BIT_ATI                                     = 0x00000040;
  public static final int COMP_BIT_ATI                                         = 0x00000002;
  public static final int NEGATE_BIT_ATI                                       = 0x00000004;
  public static final int BIAS_BIT_ATI                                         = 0x00000008;
}
