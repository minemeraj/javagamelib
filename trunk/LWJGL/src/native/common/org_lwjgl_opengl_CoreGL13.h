/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_lwjgl_opengl_CoreGL13 */

#ifndef _Included_org_lwjgl_opengl_CoreGL13
#define _Included_org_lwjgl_opengl_CoreGL13
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    glActiveTexture
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_glActiveTexture
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    glClientActiveTexture
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_glClientActiveTexture
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    nglCompressedTexImage1D
 * Signature: (IIIIIILjava/nio/Buffer;I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_nglCompressedTexImage1D
  (JNIEnv *, jclass, jint, jint, jint, jint, jint, jint, jobject, jint);

/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    nglCompressedTexImage2D
 * Signature: (IIIIIIILjava/nio/Buffer;I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_nglCompressedTexImage2D
  (JNIEnv *, jclass, jint, jint, jint, jint, jint, jint, jint, jobject, jint);

/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    nglCompressedTexImage3D
 * Signature: (IIIIIIIILjava/nio/Buffer;I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_nglCompressedTexImage3D
  (JNIEnv *, jclass, jint, jint, jint, jint, jint, jint, jint, jint, jobject, jint);

/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    nglCompressedTexSubImage1D
 * Signature: (IIIIIILjava/nio/Buffer;I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_nglCompressedTexSubImage1D
  (JNIEnv *, jclass, jint, jint, jint, jint, jint, jint, jobject, jint);

/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    nglCompressedTexSubImage2D
 * Signature: (IIIIIIIILjava/nio/Buffer;I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_nglCompressedTexSubImage2D
  (JNIEnv *, jclass, jint, jint, jint, jint, jint, jint, jint, jint, jobject, jint);

/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    nglCompressedTexSubImage3D
 * Signature: (IIIIIIIIIILjava/nio/Buffer;I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_nglCompressedTexSubImage3D
  (JNIEnv *, jclass, jint, jint, jint, jint, jint, jint, jint, jint, jint, jint, jobject, jint);

/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    nglGetCompressedTexImage
 * Signature: (IILjava/nio/Buffer;I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_nglGetCompressedTexImage
  (JNIEnv *, jclass, jint, jint, jobject, jint);

/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    glMultiTexCoord1f
 * Signature: (IF)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_glMultiTexCoord1f
  (JNIEnv *, jclass, jint, jfloat);

/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    glMultiTexCoord2f
 * Signature: (IFF)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_glMultiTexCoord2f
  (JNIEnv *, jclass, jint, jfloat, jfloat);

/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    glMultiTexCoord3f
 * Signature: (IFFF)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_glMultiTexCoord3f
  (JNIEnv *, jclass, jint, jfloat, jfloat, jfloat);

/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    glMultiTexCoord4f
 * Signature: (IFFFF)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_glMultiTexCoord4f
  (JNIEnv *, jclass, jint, jfloat, jfloat, jfloat, jfloat);

/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    nglLoadTransposeMatrixf
 * Signature: (Ljava/nio/FloatBuffer;I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_nglLoadTransposeMatrixf
  (JNIEnv *, jclass, jobject, jint);

/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    nglMultTransposeMatrixf
 * Signature: (Ljava/nio/FloatBuffer;I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_nglMultTransposeMatrixf
  (JNIEnv *, jclass, jobject, jint);

/*
 * Class:     org_lwjgl_opengl_CoreGL13
 * Method:    glSampleCoverage
 * Signature: (FZ)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_CoreGL13_glSampleCoverage
  (JNIEnv *, jclass, jfloat, jboolean);

#ifdef __cplusplus
}
#endif
#endif
