/**
 * (C) 2002 Shaven Puppy Ltd
 * 
 * Math.java Created on Aug 1, 2002 by foo
 */
package org.lwjgl;

/**
 * (C) 2002 Shaven Puppy Ltd
 * 
 * Math.java Created on Aug 1, 2002 by foo
 */
/**
 * A floating point math library, with vector processing capabilities.
 * This class differs significantly from its java.lang.Math counterpart
 * in that it a) makes no claims about strict accuracy and b) uses degrees
 * rather than radians for its functions which are more friendly to use.
 * 
 * @author foo
 */
public final class Math {

	static {
		System.loadLibrary(Sys.LIBRARY_NAME);
	}
	
	/** Floating point version of pi */
	public static final float PI = (float)java.lang.Math.PI;
	
	/*
	 * Unary matrix operations
	 */
	/** Perform no operation (except possibly transposition) */
	public static final int MATRIXOP_NOOP = 0;
	
	/** Negate the vector */
	public static final int MATRIXOP_NEGATE = 1;
	
	/** Normalise the vector (set to length 1) */
	public static final int MATRIXOP_NORMALIZE = 2;
	
	/** Compute the inverse matrix */
	public static final int MATRIXOP_INVERT = 3;
	
	/*
	 * Binary matrix operations
	 */
	 
	/** Multiply left source by right source */
	public static final int MATRIXOP_MULTIPLY = 4;
	
	/** Add right source to left source */
	public static final int MATRIXOP_ADD = 5;
	
	/** Subtract right source from left source */
	public static final int MATRIXOP_SUBTRACT = 6;

	/**
	 * No constructor for Math.
	 */
	private Math() {
		super();
	}
	
	/**
	 * @return a random float between 0 and 1.
	 */
	public static native float random();
	
	/**
	 * Return the sine of theta
	 *
	 * @param theta angle in degrees
	 * @return sin(theta)
	 */
	public static native float sin(float theta);

	/**
	 * Return the cosine of theta
	 *
	 * @param theta angle in degrees
	 * @return cos(theta)
	 */
	public static native float cos(float theta);

	/**
	 * Return the tangent of theta
	 *
	 * @param theta angle in degrees
	 * @return tan(theta)
	 */
	public static native float tan(float theta);

	/**
	 * Return the inverse sine of theta
	 *
	 * @param theta angle in degrees
	 * @return asin(theta)
	 */
	public static native float asin(float theta);

	/**
	 * Return the inverse cosine of theta
	 *
	 * @param theta angle in degrees
	 * @return acos(theta)
	 */
	public static native float acos(float theta);

	/**
	 * Return the inverse tangent of theta
	 *
	 * @param theta angle in degrees
	 * @return atan(theta)
	 */
	public static native float atan(float theta);
	
	/**
	 * Return the square root of a value
	 * @param n the number for which you want the square root
	 * @return sqrt(n)
	 */
	public static native float sqrt(float n);
	
	/**
	 * Apply a unary matrix operation to an array of matrices.
	 * @param operation A unary vector operation which must be one of
	 * MATRIXOP_NOOP,
	 * MATRIXOP_NEGATE,
	 * MATRIXOP_NORMALIZE, etc.
	 * @param sourceAddress The address of the source matrices
	 * @param sourceStride The distance between source matrices, in bytes; if 0,
	 * it is assumed that the matrices are tightly packed. This is equivalent to
	 * sourceStride == sourceWidth * sourceHeight * 4
	 * @param numElements The number of matrices
	 * @param sourceWidth The width of the source matrix. Must be > 0.
	 * @param sourceHeight The height of the source matrix. Must be > 0.
	 * @param transposeSource The source can be transposed automatically before
	 * the operation is performed on it
	 * @param destAddress The results are placed here. This may overlap the
	 * source and can even be the same, which performs the unary operation
	 * in-situ
	 * @param destStride The distance between destination matrices, in bytes,
	 * similar to sourceStride. Note that if the source and destination address
	 * overlap and the strides are different then the result is undefined.
	 * @param transposeDest The destination can be transposed before being
	 * written
	 */
	public static native void matrixOp(
		int operation,
		int sourceAddress,
		int sourceStride,
		int numElements,
		int sourceWidth,
		int sourceHeight,
		boolean transposeSource,
		int destAddress,
		int destStride,
		boolean transposeDest
	);
	
	/**
	 * Apply a binary matrix operation to two arrays of matrices. The results
	 * are computed by applying the first right element to each of the left
	 * elements in turn; then the second right element to each left element;
	 * and so on.
	 * @param operation A binary vector operation which must be one of
	 * MATRIXOP_MUTLIPLY,
	 * MATRIXOP_ADD,
	 * MATRIXOP_SUBTRACT, etc.
	 * @param leftSourceAddress The address of the source matrices
	 * @param leftSourceStride The distance between source matrices, in bytes; if 0,
	 * it is assumed that the matrices are tightly packed. This is equivalent to
	 * sourceStride == sourceWidth * sourceHeight * 4
	 * @param leftElements The number of left-hand-side matrices
	 * @param leftSourceWidth The width of the left source matrix. Must be > 0.
	 * @param leftSourceHeight The height of the left source matrix. Must be > 0.
	 * @param transposeLeftSource The left source can be transposed automatically before
	 * the operation is performed on it
	 * @param rightSourceAddress The address of the right source matrices
	 * @param rightSourceStride The distance between right source matrices, in bytes; if 0,
	 * it is assumed that the matrices are tightly packed. This is equivalent to
	 * sourceStride == sourceWidth * sourceHeight * 4
	 * @param rightElements The number of right-hand-side matrices
	 * @param rightSourceWidth The width of the right source matrix. Must be > 0.
	 * @param rightSourceHeight The height of the right source matrix. Must be > 0.
	 * @param transposeRightSource The right source can be transposed automatically before
	 * the operation is performed on it
	 * @param destAddress The results are placed here. This may overlap the
	 * sources and can even be the same, which performs the binary operation
	 * in-situ
	 * @param destStride The distance between destination matrices, in bytes,
	 * similar to sourceStride. Note that if the source and destination address
	 * overlap and the strides are different then the result is undefined.
	 * @param transposeDest The destination can be transposed before being
	 * written
	 */
	public static native void matrixOp(
		int operation,
		int leftSourceAddress,
		int leftSourceStride,
		int leftSourceWidth,
		int leftSourceHeight,
		boolean transposeLeftSource,
		int rightSourceAddress,
		int rightSourceStride,
		int rightSourceWidth,
		int rightSourceHeight,
		boolean transposeRightSource,
		int destAddress,
		int destStride,
		boolean transposeDest
	);

}
