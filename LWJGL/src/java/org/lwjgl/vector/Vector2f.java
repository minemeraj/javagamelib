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
package org.lwjgl.vector;

import java.nio.FloatBuffer;

import org.lwjgl.Math;

/**
 * $Id$
 *
 * Holds a 2-tuple vector.
 * 
 * @author cix_foo <cix_foo@users.sourceforge.net>
 * @version $Revision$
 */

public class Vector2f extends Vector {
	
	public float x, y;

	/**
	 * Constructor for Vector3f.
	 */
	public Vector2f() {
		super();
	}
	
	/**
	 * Constructor
	 */
	public Vector2f(Vector2f src) {
		set(src);
	}
	
	/**
	 * Constructor
	 */
	public Vector2f(float x, float y) {
		set(x, y);
	}

	/**
	 * Set values
	 * @return this
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Load from another Vector2f
	 * @param src The source vector
	 * @return this
	 */
	public Vector2f set(Vector2f src) {
		x = src.x;
		y = src.y;
		return this;
	}
	
	/**
	 * @return the length squared of the vector
	 */
	public float lengthSquared() {
		return x * x + y * y;
	}
	
	/**
	 * Translate a vector
	 * @param x The translation in x
	 * @param y the translation in y
	 * @return this
	 */
	public Vector2f translate(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	/**
	 * Negate a vector
	 * @return this
	 */
	public Vector negate() {
		x = -x;
		y = -y;
		return this;
	}
	
	/**
	 * Negate a vector and place the result in a destination vector.
	 * @param dest The destination vector or null if a new vector is to be created
	 * @return the negated vector
	 */
	public Vector2f negate(Vector2f dest) {
		if (dest == null)
			dest = new Vector2f();
		dest.x = -x;
		dest.y = -y;
		return dest;
	}
	
	
	/**
	 * Normalise this vector and place the result in another vector.
	 * @param dest The destination vector, or null if a new vector is to be created
	 * @return the normalised vector
	 */
	public Vector2f normalise(Vector2f dest) {
		float l = length();
		
		if (dest == null)
			dest = new Vector2f(x / l, y / l);
		else
			dest.set(x / l, y / l);
		
		return dest;
	}
	
	/**
	 * The dot product of two vectors is calculated as
	 * v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
	 * @param left The LHS vector
	 * @param right The RHS vector
	 * @return left dot right
	 */
	public static float dot(Vector2f left, Vector2f right) {
		return left.x * right.x + left.y * right.y;
	}


	
	/**
	 * Calculate the angle between two vectors, in degrees
	 * @param a A vector
	 * @param b The other vector
	 * @return the angle between the two vectors, in degrees
	 */
    public static float angle(Vector2f a, Vector2f b) {
        float dls = dot(a, b) / (a.length() * b.length());
        if (dls < -1f)
            dls = -1f;
        else if (dls > 1.0f)
            dls = 1.0f;
        return Math.acos(dls);
    }
    
    /**
     * Add a vector to another vector and place the result in a destination
     * vector.
     * @param left The LHS vector
     * @param right The RHS vector
     * @param dest The destination vector, or null if a new vector is to be created
     * @return the sum of left and right in dest
     */
    public static Vector2f add(Vector2f left, Vector2f right, Vector2f dest) {
    	if (dest == null)
    		return new Vector2f(left.x + right.x, left.y + right.y);
    	else {
			return dest.set(left.x + right.x, left.y + right.y);
    	}
    }

    /**
     * Subtract a vector from another vector and place the result in a destination
     * vector.
     * @param left The LHS vector
     * @param right The RHS vector
     * @param dest The destination vector, or null if a new vector is to be created
     * @return left minus right in dest
     */
    public static Vector2f sub(Vector2f left, Vector2f right, Vector2f dest) {
    	if (dest == null)
    		return new Vector2f(left.x - right.x, left.y - right.y);
    	else {
			return dest.set(left.x - right.x, left.y - right.y);
    	}
    }
      
    /**
     * Store this vector in a FloatBuffer
     * @param buf The buffer to store it in, at the current position
     * @return this
     */
    public Vector store(FloatBuffer buf) {
    	buf.put(x);
    	buf.put(y);
    	return this;
    }
    
    /**
     * Load this vector from a FloatBuffer
     * @param buf The buffer to load it from, at the current position
     * @return this
     */
    public Vector load(FloatBuffer buf) {
    	x = buf.get();
    	y = buf.get();
    	return this;
    }
	
	/* (non-Javadoc)
	 * @see org.lwjgl.vector.Vector#scale(float)
	 */
	public Vector scale(float scale) {
		
		x *= scale;
		y *= scale;
		
		return this;
	}

}
