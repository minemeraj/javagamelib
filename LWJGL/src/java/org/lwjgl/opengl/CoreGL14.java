/**
 * (C) 2002 Shaven Puppy Ltd
 * 
 * CoreGL14.java Created on Aug 9, 2002 by foo
 */
package org.lwjgl.opengl;

import org.lwjgl.Sys;

/**
 * 
 * @author foo
 */
public class CoreGL14 extends CoreGL13 implements CoreGL14Constants {

	static {
		System.loadLibrary(Sys.LIBRARY_NAME);
	}

	/**
	 * Constructor for CoreGL14.
	 * @param colorBits
	 * @param alphaBits
	 * @param depthBits
	 * @param stencilBits
	 */
	public CoreGL14(
		int colorBits,
		int alphaBits,
		int depthBits,
		int stencilBits) {
		super(colorBits, alphaBits, depthBits, stencilBits);
	}

}
