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
package org.lwjgl.openal.eax;

/**
 * $Id$
 *
 * The base OpenAL EAX functionality.
 *
 * This has been provided as a base class that we can use for either the
 * full EAX specification or as a cut-down OpenAL EAX embedded spec. (aka
 * a mini-driver).
 *
 * @author Brian Matzon <brian@matzon.dk>
 * @version $Revision$
 */
public abstract class BaseEAX {

	/** Has the EAX object been created? */
	protected static boolean created;

	static {
		initialize();
	}

	/**
	 * Override to provide any initialization code after creation.
	 */
	protected void init() {
	}

	/**
	 * Static initialization
	 */
	private static void initialize() {
		System.loadLibrary(org.lwjgl.Sys.getLibraryName());
	}

	/**
	 * Loads the EAX functions
	 * 
	 * @throws Exception if the EAX extensions couldn't be loaded
	 */
	public void create() throws Exception {
		if (created) {
			return;
		}

		if (!nCreate()) {
			throw new Exception("EAX instance could not be created.");
		}
		created = true;
		init();
	}

	/**
	 * Native method to create EAX instance
	 * 
	 * @return true if the EAX extensions could be found
	 */
	protected native boolean nCreate();

	/**
	 * "Destroy" the EAX object
	 */
	public void destroy() {
		if (!created) {
			return;
		}
		created = false;
		nDestroy();
	}

	/**
	 * Finalizer, marked final. 
	 */
	public void finalize() throws Throwable {
		super.finalize();
		destroy();
	}

	/**
	 * Native method the destroy the EAX
	 */
	protected native void nDestroy();
}