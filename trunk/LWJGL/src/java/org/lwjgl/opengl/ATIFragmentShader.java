/* MACHINE GENERATED FILE, DO NOT EDIT */

package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;
import org.lwjgl.BufferChecks;
import java.nio.*;

public final class ATIFragmentShader {
	public static final int GL_BIAS_BIT_ATI = 0x8;
	public static final int GL_NEGATE_BIT_ATI = 0x4;
	public static final int GL_COMP_BIT_ATI = 0x2;
	public static final int GL_SATURATE_BIT_ATI = 0x40;
	public static final int GL_EIGHTH_BIT_ATI = 0x20;
	public static final int GL_QUARTER_BIT_ATI = 0x10;
	public static final int GL_HALF_BIT_ATI = 0x8;
	public static final int GL_X8_BIT_ATI = 0x4;
	public static final int GL_X4_BIT_ATI = 0x2;
	public static final int GL_X2_BIT_ATI = 0x1;
	public static final int GL_BLUE_BIT_ATI = 0x4;
	public static final int GL_GREEN_BIT_ATI = 0x2;
	public static final int GL_RED_BIT_ATI = 0x1;
	public static final int GL_SWIZZLE_STRQ_DQ_ATI = 0x897b;
	public static final int GL_SWIZZLE_STRQ_ATI = 0x897a;
	public static final int GL_SWIZZLE_STQ_DQ_ATI = 0x8979;
	public static final int GL_SWIZZLE_STR_DR_ATI = 0x8978;
	public static final int GL_SWIZZLE_STQ_ATI = 0x8977;
	public static final int GL_SWIZZLE_STR_ATI = 0x8976;
	public static final int GL_COLOR_ALPHA_PAIRING_ATI = 0x8975;
	public static final int GL_NUM_LOOPBACK_COMPONENTS_ATI = 0x8974;
	public static final int GL_NUM_INPUT_INTERPOLATOR_COMPONENTS_ATI = 0x8973;
	public static final int GL_NUM_INSTRUCTIONS_TOTAL_ATI = 0x8972;
	public static final int GL_NUM_INSTRUCTIONS_PER_PASS_ATI = 0x8971;
	public static final int GL_NUM_PASSES_ATI = 0x8970;
	public static final int GL_NUM_FRAGMENT_CONSTANTS_ATI = 0x896f;
	public static final int GL_NUM_FRAGMENT_REGISTERS_ATI = 0x896e;
	public static final int GL_SECONDARY_INTERPOLATOR_ATI = 0x896d;
	public static final int GL_DOT2_ADD_ATI = 0x896c;
	public static final int GL_CND0_ATI = 0x896b;
	public static final int GL_CND_ATI = 0x896a;
	public static final int GL_LERP_ATI = 0x8969;
	public static final int GL_MAD_ATI = 0x8968;
	public static final int GL_DOT4_ATI = 0x8967;
	public static final int GL_DOT3_ATI = 0x8966;
	public static final int GL_SUB_ATI = 0x8965;
	public static final int GL_MUL_ATI = 0x8964;
	public static final int GL_ADD_ATI = 0x8963;
	public static final int GL_MOV_ATI = 0x8961;
	public static final int GL_CON_31_ATI = 0x8960;
	public static final int GL_CON_30_ATI = 0x895f;
	public static final int GL_CON_29_ATI = 0x895e;
	public static final int GL_CON_28_ATI = 0x895d;
	public static final int GL_CON_27_ATI = 0x895c;
	public static final int GL_CON_26_ATI = 0x895b;
	public static final int GL_CON_25_ATI = 0x895a;
	public static final int GL_CON_24_ATI = 0x8959;
	public static final int GL_CON_23_ATI = 0x8958;
	public static final int GL_CON_22_ATI = 0x8957;
	public static final int GL_CON_21_ATI = 0x8956;
	public static final int GL_CON_20_ATI = 0x8955;
	public static final int GL_CON_19_ATI = 0x8954;
	public static final int GL_CON_18_ATI = 0x8953;
	public static final int GL_CON_17_ATI = 0x8952;
	public static final int GL_CON_16_ATI = 0x8951;
	public static final int GL_CON_15_ATI = 0x8950;
	public static final int GL_CON_14_ATI = 0x894f;
	public static final int GL_CON_13_ATI = 0x894e;
	public static final int GL_CON_12_ATI = 0x894d;
	public static final int GL_CON_11_ATI = 0x894c;
	public static final int GL_CON_10_ATI = 0x894b;
	public static final int GL_CON_9_ATI = 0x894a;
	public static final int GL_CON_8_ATI = 0x8949;
	public static final int GL_CON_7_ATI = 0x8948;
	public static final int GL_CON_6_ATI = 0x8947;
	public static final int GL_CON_5_ATI = 0x8946;
	public static final int GL_CON_4_ATI = 0x8945;
	public static final int GL_CON_3_ATI = 0x8944;
	public static final int GL_CON_2_ATI = 0x8943;
	public static final int GL_CON_1_ATI = 0x8942;
	public static final int GL_CON_0_ATI = 0x8941;
	public static final int GL_REG_31_ATI = 0x8940;
	public static final int GL_REG_30_ATI = 0x893f;
	public static final int GL_REG_29_ATI = 0x893e;
	public static final int GL_REG_28_ATI = 0x893d;
	public static final int GL_REG_27_ATI = 0x893c;
	public static final int GL_REG_26_ATI = 0x893b;
	public static final int GL_REG_25_ATI = 0x893a;
	public static final int GL_REG_24_ATI = 0x8939;
	public static final int GL_REG_23_ATI = 0x8938;
	public static final int GL_REG_22_ATI = 0x8937;
	public static final int GL_REG_21_ATI = 0x8936;
	public static final int GL_REG_20_ATI = 0x8935;
	public static final int GL_REG_19_ATI = 0x8934;
	public static final int GL_REG_18_ATI = 0x8933;
	public static final int GL_REG_17_ATI = 0x8932;
	public static final int GL_REG_16_ATI = 0x8931;
	public static final int GL_REG_15_ATI = 0x8930;
	public static final int GL_REG_14_ATI = 0x892f;
	public static final int GL_REG_13_ATI = 0x892e;
	public static final int GL_REG_12_ATI = 0x892d;
	public static final int GL_REG_11_ATI = 0x892c;
	public static final int GL_REG_10_ATI = 0x892b;
	public static final int GL_REG_9_ATI = 0x892a;
	public static final int GL_REG_8_ATI = 0x8929;
	public static final int GL_REG_7_ATI = 0x8928;
	public static final int GL_REG_6_ATI = 0x8927;
	public static final int GL_REG_5_ATI = 0x8926;
	public static final int GL_REG_4_ATI = 0x8925;
	public static final int GL_REG_3_ATI = 0x8924;
	public static final int GL_REG_2_ATI = 0x8923;
	public static final int GL_REG_1_ATI = 0x8922;
	public static final int GL_REG_0_ATI = 0x8921;
	public static final int GL_FRAGMENT_SHADER_ATI = 0x8920;

	private ATIFragmentShader() {
	}

	static native void initNativeStubs() throws LWJGLException;

	public static void glSetFragmentShaderConstantATI(int dst, FloatBuffer pfValue) {
		BufferChecks.checkBuffer(pfValue, 4);
		nglSetFragmentShaderConstantATI(dst, pfValue, pfValue.position());
	}
	private static native void nglSetFragmentShaderConstantATI(int dst, FloatBuffer pfValue, int pfValue_position);

	public static native void glAlphaFragmentOp3ATI(int op, int dst, int dstMod, int arg1, int arg1Rep, int arg1Mod, int arg2, int arg2Rep, int arg2Mod, int arg3, int arg3Rep, int arg3Mod);

	public static native void glAlphaFragmentOp2ATI(int op, int dst, int dstMod, int arg1, int arg1Rep, int arg1Mod, int arg2, int arg2Rep, int arg2Mod);

	public static native void glAlphaFragmentOp1ATI(int op, int dst, int dstMod, int arg1, int arg1Rep, int arg1Mod);

	public static native void glColorFragmentOp3ATI(int op, int dst, int dstMask, int dstMod, int arg1, int arg1Rep, int arg1Mod, int arg2, int arg2Rep, int arg2Mod, int arg3, int arg3Rep, int arg3Mod);

	public static native void glColorFragmentOp2ATI(int op, int dst, int dstMask, int dstMod, int arg1, int arg1Rep, int arg1Mod, int arg2, int arg2Rep, int arg2Mod);

	public static native void glColorFragmentOp1ATI(int op, int dst, int dstMask, int dstMod, int arg1, int arg1Rep, int arg1Mod);

	public static native void glSampleMapATI(int dst, int interp, int swizzle);

	public static native void glPassTexCoordATI(int dst, int coord, int swizzle);

	public static native void glEndFragmentShaderATI();

	public static native void glBeginFragmentShaderATI();

	public static native void glDeleteFragmentShaderATI(int id);

	public static native void glBindFragmentShaderATI(int id);

	public static native int glGenFragmentShadersATI(int range);
}
