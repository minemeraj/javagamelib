/*
 * Copyright (c) 2002-2004 LWJGL Project
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
 * * Neither the name of 'LWJGL' nor the names of
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
package org.lwjgl.opengl;

/**
 * This is the Display implementation interface. Display delegates
 * to implementors of this interface. There is one DisplayImplementation
 * for each supported platform.
 * @author elias_naur
 */

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;

final class LinuxDisplay implements DisplayImplementation {
	/* X11 constants */
	private final static int GrabSuccess = 0;
	private final static int AutoRepeatModeOff  = 0;
	private final static int AutoRepeatModeOn = 1;
	private final static int AutoRepeatModeDefault = 2;

	/** Window mode enum */
	private static final int FULLSCREEN_LEGACY = 1;
	private static final int FULLSCREEN_NETWM = 2;
	private static final int WINDOWED = 3;

	/** Current window mode */
	private static int current_window_mode = WINDOWED;
	
	/** Display mode switching API */
	private static final int XRANDR = 10;
	private static final int XF86VIDMODE = 11;
	private static final int NONE = 12;
	
	/** Current mode swithcing API */
	private static int current_displaymode_extension = NONE;

	/** Keep track on the current awt lock owner to avoid
	 * depending on JAWT locking to be re-entrant (This is a
	 * problem with GCJ). JAWT locking is not that well specified
	 * anyway so it is probably best to avoid assuming too much
	 * about it.
	 */
	private static Thread current_awt_lock_owner;
	private static int awt_lock_count;
	
	private static int display_connection_usage_count = 0;

	private static PeerInfo peer_info;

	/** Saved gamma used to restore display settings */
	private static ByteBuffer saved_gamma;
	private static ByteBuffer current_gamma;

	/** Saved mode to restore with */
	private static DisplayMode saved_mode;
	private static DisplayMode current_mode;

	private static boolean keyboard_grabbed;
	private static boolean pointer_grabbed;
	private static boolean input_released;
	private static boolean grab;
	private static boolean focused;
	private static boolean minimized;
	private static boolean dirty;
	private static boolean close_requested;
	private static ByteBuffer current_cursor;
	private static ByteBuffer blank_cursor;

	private static LinuxKeyboard keyboard;
	private static LinuxMouse mouse;

	private static ByteBuffer getCurrentGammaRamp() throws LWJGLException {
		lockAWT();
		try {
			incDisplay();
			try {
				if (isXF86VidModeSupported())
					return nGetCurrentGammaRamp();
				else
					return null;
			} finally {
				decDisplay();
			}
		} finally {
			unlockAWT();
		}
	}
	private static native ByteBuffer nGetCurrentGammaRamp() throws LWJGLException;
	
	private static int getBestDisplayModeExtension() {
		int result;
		if (isXrandrSupported()) {
			LWJGLUtil.log("Using Xrandr for display mode switching");
			result = XRANDR;
		} else if (isXF86VidModeSupported()) {
			LWJGLUtil.log("Using XF86VidMode for display mode switching");
			result = XF86VIDMODE;
		} else {
			LWJGLUtil.log("No display mode extensions available");
			result = NONE;
		}
		return result;
	}
	
	private static boolean isXrandrSupported() {
		if (Boolean.getBoolean("LWJGL_DISABLE_XRANDR"))
			return false;
		lockAWT();
		try {
			incDisplay();
			try {
				return nIsXrandrSupported();
			} finally {
				decDisplay();
			}
		} catch (LWJGLException e) {
			LWJGLUtil.log("Got exception while querying Xrandr support: " + e);
			return false;
		} finally {
			unlockAWT();
		}
	}
	private static native boolean nIsXrandrSupported() throws LWJGLException;

	private static boolean isXF86VidModeSupported() {
		lockAWT();
		try {
			incDisplay();
			try {
				return nIsXF86VidModeSupported();
			} finally {
				decDisplay();
			}
		} catch (LWJGLException e) {
			LWJGLUtil.log("Got exception while querying XF86VM support: " + e);
			return false;
		} finally {
			unlockAWT();
		}
	}
	private static native boolean nIsXF86VidModeSupported() throws LWJGLException;

	private static boolean isNetWMFullscreenSupported() throws LWJGLException {
		if (Boolean.getBoolean("LWJGL_DISABLE_NETWM"))
			return false;
		lockAWT();
		try {
			incDisplay();
			try {
				return nIsNetWMFullscreenSupported();
			} finally {
				decDisplay();
			}
		} catch (LWJGLException e) {
			LWJGLUtil.log("Got exception while querying NetWM support: " + e);
			return false;
		} finally {
			unlockAWT();
		}
	}
	private static native boolean nIsNetWMFullscreenSupported() throws LWJGLException;
	
	/* Since Xlib is not guaranteed to be thread safe, we need a way to synchronize LWJGL
	 * Xlib calls with AWT Xlib calls. Fortunately, JAWT implements Lock()/Unlock() to
	 * do just that.
	 */
	static synchronized void lockAWT() {
		Thread this_thread = Thread.currentThread();
		while (current_awt_lock_owner != null && current_awt_lock_owner != this_thread) {
			try {
				LinuxDisplay.class.wait();
			} catch (InterruptedException e) {
				LWJGLUtil.log("Interrupted while waiting for awt lock: " + e);
			}
		}
		if (awt_lock_count == 0) {
			current_awt_lock_owner = this_thread;
			try {
				nLockAWT();
			} catch (LWJGLException e) {
				LWJGLUtil.log("Caught exception while locking AWT: " + e);
			}
		}
		awt_lock_count++;
	}
	private static native void nLockAWT() throws LWJGLException;
	
	static synchronized void unlockAWT() {
		if (awt_lock_count <= 0)
			throw new IllegalStateException("AWT not locked!");
		if (Thread.currentThread() != current_awt_lock_owner)
			throw new IllegalStateException("AWT already locked by " + current_awt_lock_owner);
		awt_lock_count--;
		if (awt_lock_count == 0) {
			try {
				nUnlockAWT();
			} catch (LWJGLException e) {
				LWJGLUtil.log("Caught exception while unlocking AWT: " + e);
			}
			current_awt_lock_owner = null;
			LinuxDisplay.class.notify();
		}
	}
	private static native void nUnlockAWT() throws LWJGLException;

	/**
	 * increment and decrement display usage.
	 */
	static void incDisplay() throws LWJGLException {
		if (display_connection_usage_count == 0) {
			GLContext.loadOpenGLLibrary();
			openDisplay();
		}
		display_connection_usage_count++;
	}
	
	static void decDisplay() {
		display_connection_usage_count--;
		if (display_connection_usage_count < 0)
			throw new InternalError("display_connection_usage_count < 0: " + display_connection_usage_count);
		if (display_connection_usage_count == 0) {
			closeDisplay();
			GLContext.unloadOpenGLLibrary();	
		}
	}

	private static native void openDisplay() throws LWJGLException;
	private static native void closeDisplay();

	private static int getWindowMode(boolean fullscreen) throws LWJGLException {
		if (fullscreen) {
			if (current_displaymode_extension == XRANDR && isNetWMFullscreenSupported()) {
				LWJGLUtil.log("Using NetWM for fullscreen window");
				return FULLSCREEN_NETWM;
			} else {
				LWJGLUtil.log("Using legacy mode for fullscreen window");
				return FULLSCREEN_LEGACY;
			}
		} else
			return WINDOWED;
	}
	
	private static native long getDisplay();
	private static native int getScreen();
	private static native long getWindow();

	private static void ungrabKeyboard() {
		if (keyboard_grabbed) {
			nUngrabKeyboard(getDisplay());
			keyboard_grabbed = false;
		}
	}
	private static native int nUngrabKeyboard(long display);
	
	private static void grabKeyboard() {
		if (!keyboard_grabbed) {
			int res = nGrabKeyboard(getDisplay(), getWindow());
			if (res == GrabSuccess)
				keyboard_grabbed = true;
		}
	}
	private static native int nGrabKeyboard(long display, long window);
	
	private static void grabPointer() {
		if (!pointer_grabbed) {
			int result = nGrabPointer(getDisplay(), getWindow());
			if (result == GrabSuccess) {
				pointer_grabbed = true;
				// make sure we have a centered window
				if (isLegacyFullscreen()) {
					nSetViewPort(getDisplay(), getWindow(), getScreen());
				}
			}
		}
	}
	private static native int nGrabPointer(long display, long window);
	private static native void nSetViewPort(long display, long window, int screen);

	private static void ungrabPointer() {
		if (pointer_grabbed) {
			pointer_grabbed = false;
			nUngrabPointer(getDisplay());
		}
	}
	private static native int nUngrabPointer(long display);

	private static boolean isFullscreen() {
		return current_window_mode == FULLSCREEN_LEGACY || current_window_mode == FULLSCREEN_NETWM;
	}

	private static boolean shouldGrab() {
		return !input_released && grab;
	}

	private static void updatePointerGrab() {
		if (isFullscreen() || shouldGrab()) {
			grabPointer();
		} else {
			ungrabPointer();
		}
		updateCursor();
	}
	
	private static void updateCursor() {
		ByteBuffer cursor;
		if (shouldGrab()) {
			cursor = blank_cursor;
		} else {
			cursor = current_cursor;
		}
		nDefineCursor(getDisplay(), getWindow(), cursor);
	}
	private static native void nDefineCursor(long display, long window, ByteBuffer cursor_handle);
	
	private static boolean isLegacyFullscreen() {
		return current_window_mode == FULLSCREEN_LEGACY;
	}

	private static void updateKeyboardGrab() {
		if (isLegacyFullscreen())
			grabKeyboard();
		else
			ungrabKeyboard();
	}

	public void createWindow(DisplayMode mode, boolean fullscreen, int x, int y) throws LWJGLException {
		lockAWT();
		try {
			incDisplay();
			try {
				ByteBuffer handle = peer_info.lockAndGetHandle();
				try {
					current_window_mode = getWindowMode(fullscreen);
					nCreateWindow(handle, mode, current_window_mode, x, y);
					blank_cursor = createBlankCursor();
					current_cursor = null;
					focused = true;
					input_released = false;
					pointer_grabbed = false;
					keyboard_grabbed = false;
					close_requested = false;
					grab = false;
					minimized = false;
					dirty = true;
					updateInputGrab();
					nSetRepeatMode(getDisplay(), AutoRepeatModeOff);
				} finally {
					peer_info.unlock();
				}
			} catch (LWJGLException e) {
				decDisplay();
				throw e;
			}
		} finally {
			unlockAWT();
		}
	}
	private static native void nCreateWindow(ByteBuffer peer_info_handle, DisplayMode mode, int window_mode, int x, int y) throws LWJGLException;

	private static void updateInputGrab() {
		updatePointerGrab();
		updateKeyboardGrab();
	}

	public void destroyWindow() {
		lockAWT();
		try {
			try {
				setNativeCursor(null);
			} catch (LWJGLException e) {
				LWJGLUtil.log("Failed to reset cursor: " + e.getMessage());
			}
			nDestroyCursor(blank_cursor);
			blank_cursor = null;
			ungrabKeyboard();
			nDestroyWindow();
			nSetRepeatMode(getDisplay(), AutoRepeatModeDefault);
			decDisplay();
		} finally {
			unlockAWT();
		}
	}
	private static native void nDestroyWindow();

	public void switchDisplayMode(DisplayMode mode) throws LWJGLException {
		lockAWT();
		try {
			nSwitchDisplayMode(getScreen(), current_displaymode_extension, mode);
			current_mode = mode;
		} finally {
			unlockAWT();
		}
	}
	private static native void nSwitchDisplayMode(int screen, int extension, DisplayMode mode) throws LWJGLException;

	public void resetDisplayMode() {
		lockAWT();
		try {
			switchDisplayMode(saved_mode);
			if (isXF86VidModeSupported())
				doSetGamma(saved_gamma);
		} catch (LWJGLException e) {
			LWJGLUtil.log("Caught exception while resetting mode: " + e);
		} finally {
			unlockAWT();
		}
	}

	public int getGammaRampLength() {
		if (!isXF86VidModeSupported())
			return 0;
		lockAWT();
		try {
			try {
				incDisplay();
				try {
					return nGetGammaRampLength(getDisplay(), getScreen());
				} catch (LWJGLException e) {
					LWJGLUtil.log("Got exception while querying gamma length: " + e);
					return 0;
				} finally {
					decDisplay();
				}
			} catch (LWJGLException e) {
				LWJGLUtil.log("Failed to get gamma ramp length: " + e);
				return 0;
			}
		} finally {
			unlockAWT();
		}
	}
	private static native int nGetGammaRampLength(long display, int screen) throws LWJGLException;

	public void setGammaRamp(FloatBuffer gammaRamp) throws LWJGLException {
		if (!isXF86VidModeSupported())
			throw new LWJGLException("No gamma ramp support (Missing XF86VM extension)");
		doSetGamma(convertToNativeRamp(gammaRamp));
	}

	private static void doSetGamma(ByteBuffer native_gamma) throws LWJGLException {
		lockAWT();
		try {
			nSetGammaRamp(getScreen(), native_gamma);
			current_gamma = native_gamma;
		} finally {
			unlockAWT();
		}
	}
	private static native void nSetGammaRamp(int screen, ByteBuffer gammaRamp) throws LWJGLException;

	private static ByteBuffer convertToNativeRamp(FloatBuffer ramp) throws LWJGLException {
		return nConvertToNativeRamp(ramp, ramp.position(), ramp.remaining());
	}
	private static native ByteBuffer nConvertToNativeRamp(FloatBuffer ramp, int offset, int length) throws LWJGLException;

	public String getAdapter() {
		return null;
	}

	public String getVersion() {
		return null;
	}

	public DisplayMode init() throws LWJGLException {
		lockAWT();
		try {
			current_displaymode_extension = getBestDisplayModeExtension();
			if (current_displaymode_extension == NONE)
				throw new LWJGLException("No display mode extension is available");
			DisplayMode[] modes = getAvailableDisplayModes();
			if (modes == null || modes.length == 0)
				throw new LWJGLException("No modes available");
			switch (current_displaymode_extension) {
				case XRANDR:
					saved_mode = getCurrentXRandrMode();
					break;
				case XF86VIDMODE:
					saved_mode = modes[0];
					break;
				default:
					throw new LWJGLException("Unknown display mode extension: " + current_displaymode_extension);
			}
			current_mode = saved_mode;
			saved_gamma = getCurrentGammaRamp();
			current_gamma = saved_gamma;
			return saved_mode;
		} finally {
			unlockAWT();
		}
	}

	private static DisplayMode getCurrentXRandrMode() throws LWJGLException {
		lockAWT();
		try {
			incDisplay();
			try {
				return nGetCurrentXRandrMode();
			} finally {
				decDisplay();
			}
		} finally {
			unlockAWT();
		}
	}
	
	/** Assumes extension == XRANDR */
	private static native DisplayMode nGetCurrentXRandrMode() throws LWJGLException;

	public void setTitle(String title) {
		lockAWT();
		try {
			nSetTitle(title);
		} finally {
			unlockAWT();
		}
	}
	private static native void nSetTitle(String title);

	public boolean isCloseRequested() {
		boolean result = close_requested;
		close_requested = false;
		return result;
	}

	public boolean isVisible() {
		return !minimized;
	}

	public boolean isActive() {
		return focused || isLegacyFullscreen();
	}

	public boolean isDirty() {
		boolean result = dirty;
		dirty = false;
		return result;
	}

	public PeerInfo createPeerInfo(PixelFormat pixel_format) throws LWJGLException {
		peer_info = new LinuxDisplayPeerInfo(pixel_format);
		return peer_info;
	}
	
	public void update() {
		lockAWT();
		try {
			nUpdate(current_displaymode_extension, current_window_mode, saved_gamma, current_gamma, saved_mode, current_mode);
			checkInput();
		} catch (LWJGLException e) {
			LWJGLUtil.log("Caught exception while processing messages: " + e);
		} finally {
			unlockAWT();
		}
	}
	private static native void nUpdate(int extension, int current_window_mode, ByteBuffer saved_gamma, ByteBuffer current_gamma, DisplayMode saved_mode, DisplayMode current_mode) throws LWJGLException;

	public void reshape(int x, int y, int width, int height) {
		lockAWT();
		try {
			nReshape(x, y, width, height);
		} finally {
			unlockAWT();
		}
	}
	private static native void nReshape(int x, int y, int width, int height);

	public DisplayMode[] getAvailableDisplayModes() throws LWJGLException {
		lockAWT();
		try {
			incDisplay();
			try {
				DisplayMode[] modes = nGetAvailableDisplayModes(current_displaymode_extension);
				return modes;
			} finally {
				decDisplay();
			}
		} finally {
			unlockAWT();
		}
	}
	private static native DisplayMode[] nGetAvailableDisplayModes(int extension) throws LWJGLException;

	/* Mouse */
	public boolean hasWheel() {
		return true;
	}

	public int getButtonCount() {
		return mouse.getButtonCount();
	}

	public void createMouse() {
		lockAWT();
		try {
			mouse = new LinuxMouse(getDisplay(), getWindow());
		} finally {
			unlockAWT();
		}
	}

	public void destroyMouse() {
		mouse = null;
	}
	
	public void pollMouse(IntBuffer coord_buffer, ByteBuffer buttons) {
		update();
		lockAWT();
		try {
			mouse.poll(grab, coord_buffer, buttons);
		} finally {
			unlockAWT();
		}
	}
	
	public int readMouse(IntBuffer buffer) {
		update();
		lockAWT();
		try {
			return mouse.read(buffer);
		} finally {
			unlockAWT();
		}
	}
	
	public void setCursorPosition(int x, int y) {
		lockAWT();
		try {
			mouse.setCursorPosition(x, y);
		} finally {
			unlockAWT();
		}
	}
	
	private static void checkInput() {
		focused = nGetInputFocus(getDisplay()) == getWindow();
		if (focused) {
			acquireInput();
		} else {
			releaseInput();
		}
	}
	private static native long nGetInputFocus(long display);

	private static void releaseInput() {
		if (isLegacyFullscreen() || input_released)
			return;
		input_released = true;
		nSetRepeatMode(getDisplay(), AutoRepeatModeDefault);
		updateInputGrab();
		if (current_window_mode == FULLSCREEN_NETWM) {
			nIconifyWindow(getDisplay(), getWindow(), getScreen());
			try {
				nSwitchDisplayMode(getScreen(), current_displaymode_extension, saved_mode);
				nSetGammaRamp(getScreen(), saved_gamma);
			} catch (LWJGLException e) {
				LWJGLUtil.log("Failed to restore saved mode: " + e.getMessage());
			}
		}
	}
	private static native void nIconifyWindow(long display, long window, int screen);

	private static void acquireInput() {
		if (isLegacyFullscreen() || !input_released)
			return;
		input_released = false;
		nSetRepeatMode(getDisplay(), AutoRepeatModeOff);
		updateInputGrab();
		if (current_window_mode == FULLSCREEN_NETWM) {
			try {
				nSwitchDisplayMode(getScreen(), current_displaymode_extension, current_mode);
				nSetGammaRamp(getScreen(), current_gamma);
			} catch (LWJGLException e) {
				LWJGLUtil.log("Failed to restore mode: " + e.getMessage());
			}
		}
	}
	private static native void nSetRepeatMode(long display, int mode);

	public void grabMouse(boolean new_grab) {
		lockAWT();
		try {
			if (new_grab != grab) {
				grab = new_grab;
				updateInputGrab();
				mouse.changeGrabbed(grab, pointer_grabbed, shouldGrab());
			}
		} finally {
			unlockAWT();
		}
	}
	
	public int getNativeCursorCapabilities() {
		lockAWT();
		try {
			incDisplay();
			try {
				return nGetNativeCursorCapabilities();
			} finally {
				decDisplay();
			}
		} catch (LWJGLException e) {
			throw new RuntimeException(e);
		} finally {
			unlockAWT();
		}
	}
	private static native int nGetNativeCursorCapabilities() throws LWJGLException;

	public void setNativeCursor(Object handle) throws LWJGLException {
		current_cursor = (ByteBuffer)handle;
		lockAWT();
		try {
			updateCursor();
		} finally {
			unlockAWT();
		}
	}
	
	public int getMinCursorSize() {
		lockAWT();
		try {
			incDisplay();
			try {
				return nGetMinCursorSize();
			} finally {
				decDisplay();
			}
		} catch (LWJGLException e) {
			LWJGLUtil.log("Exception occurred in getMinCursorSize: " + e);
			return 0;
		} finally {
			unlockAWT();
		}
	}
	private static native int nGetMinCursorSize();

	public int getMaxCursorSize() {
		lockAWT();
		try {
			incDisplay();
			try {
				return nGetMaxCursorSize();
			} finally {
				decDisplay();
			}
		} catch (LWJGLException e) {
			LWJGLUtil.log("Exception occurred in getMaxCursorSize: " + e);
			return 0;
		} finally {
			unlockAWT();
		}
	}
	private static native int nGetMaxCursorSize();
	
	/* Keyboard */
	public void createKeyboard() throws LWJGLException {
		lockAWT();
		try {
			keyboard = new LinuxKeyboard(getDisplay(), getWindow());
		} finally {
			unlockAWT();
		}
	}
	
	public void destroyKeyboard() {
		lockAWT();
		try {
			keyboard.destroy();
			keyboard = null;
		} finally {
			unlockAWT();
		}
	}
	
	public void pollKeyboard(ByteBuffer keyDownBuffer) {
		update();
		lockAWT();
		try {
			keyboard.poll(keyDownBuffer);
		} finally {
			unlockAWT();
		}
	}

	public int readKeyboard(IntBuffer buffer) {
		update();
		lockAWT();
		try {
			return keyboard.read(buffer);
		} finally {
			unlockAWT();
		}
	}
	
/*	public int isStateKeySet(int key) {
		return Keyboard.STATE_UNKNOWN;
	}
*/
	private static native ByteBuffer nCreateCursor(int width, int height, int xHotspot, int yHotspot, int numImages, IntBuffer images, int images_offset, IntBuffer delays, int delays_offset) throws LWJGLException;

	private static ByteBuffer createBlankCursor() {
		return nCreateBlankCursor(getDisplay(), getWindow());
	}
	private static native ByteBuffer nCreateBlankCursor(long display, long window);

	public Object createCursor(int width, int height, int xHotspot, int yHotspot, int numImages, IntBuffer images, IntBuffer delays) throws LWJGLException {
		lockAWT();
		try {
			incDisplay();
			try {
				return nCreateCursor(width, height, xHotspot, yHotspot, numImages, images, images.position(), delays, delays != null ? delays.position() : -1);
			} catch (LWJGLException e) {
				decDisplay();
				throw e;
			}
		} finally {
			unlockAWT();
		}
	}

	public void destroyCursor(Object cursorHandle) {
		lockAWT();
		try {
			nDestroyCursor(cursorHandle);
			decDisplay();
		} finally {
			unlockAWT();
		}
	}
	private static native void nDestroyCursor(Object cursorHandle);
	
	public int getPbufferCapabilities() {
		lockAWT();
		try {
			incDisplay();
			try {
				return nGetPbufferCapabilities();
			} finally {
				decDisplay();
			}
		} catch (LWJGLException e) {
			LWJGLUtil.log("Exception occurred in getPbufferCapabilities: " + e);
			return 0;
		} finally {
			unlockAWT();
		}
	}
	private static native int nGetPbufferCapabilities();

	public boolean isBufferLost(PeerInfo handle) {
		return false;
	}

	public PeerInfo createPbuffer(int width, int height, PixelFormat pixel_format,
			IntBuffer pixelFormatCaps,
			IntBuffer pBufferAttribs) throws LWJGLException {
		return new LinuxPbufferPeerInfo(width, height, pixel_format);
	}

	public void setPbufferAttrib(PeerInfo handle, int attrib, int value) {
		throw new UnsupportedOperationException();
	}

	public void bindTexImageToPbuffer(PeerInfo handle, int buffer) {
		throw new UnsupportedOperationException();
	}

	public void releaseTexImageFromPbuffer(PeerInfo handle, int buffer) {
		throw new UnsupportedOperationException();
	}
	
	private static ByteBuffer convertIcon(ByteBuffer icon, int width, int height) {
		ByteBuffer icon_copy = BufferUtils.createByteBuffer(icon.capacity());
		int x = 0;
		int y = 5;
		byte r,g,b,a;

		int depth = 4;

		for (y = 0; y < height; y++) {
			for (x = 0; x < width; x++) {
				r = icon.get((x*4)+(y*width*4));
				g = icon.get((x*4)+(y*width*4)+1);
				b = icon.get((x*4)+(y*width*4)+2);
				a = icon.get((x*4)+(y*width*4)+3);

				icon_copy.put((x*depth)+(y*width*depth), b); // blue
				icon_copy.put((x*depth)+(y*width*depth)+1, g); // green
				icon_copy.put((x*depth)+(y*width*depth)+2, r);
				icon_copy.put((x*depth)+(y*width*depth)+3, a);
			}
		}
		return icon_copy;
	}

	/**
	 * Sets one or more icons for the Display.
	 * <ul>
	 * <li>On Windows you should supply at least one 16x16 icon and one 32x32.</li>
	 * <li>Linux (and similar platforms) expect one 32x32 icon.</li>
	 * <li>Mac OS X should be supplied one 128x128 icon</li>
	 * </ul>
	 * The implementation will use the supplied ByteBuffers with image data in RGBA and perform any conversions necessary for the specific platform.
	 *
	 * @param icons Array of icons in RGBA mode
	 * @return number of icons used.
	 */
	public int setIcon(ByteBuffer[] icons) {
		lockAWT();
		try {
			incDisplay();
			try {
				for (int i=0;i<icons.length;i++) {
					int size = icons[i].limit() / 4;
					int dimension = (int)Math.sqrt(size);
					if (dimension == 32) {
						ByteBuffer icon = convertIcon(icons[i], dimension, dimension);
						nSetWindowIcon(icon, icon.capacity(), dimension, dimension);
						return 1;
					}
				}
				return 0;
			} finally {
				decDisplay();
			}
		} catch (LWJGLException e) {
			LWJGLUtil.log("Failed to set display icon: " + e);
			return 0;
		} finally {
			unlockAWT();
		}
	}
	
	private static native void nSetWindowIcon(ByteBuffer icon, int icons_size, int width, int height);

	/* Callbacks from nUpdate() */
	private static void handleButtonEvent(long millis, int type, int button, int state) {
		if (mouse != null)
			mouse.handleButtonEvent(grab, type, button);
	}

	private static void handleKeyEvent(long event_ptr, long millis, int type, int keycode, int state) {
		if (keyboard != null)
			keyboard.handleKeyEvent(event_ptr, millis, type, keycode, state);
	}

	private static void handlePointerMotionEvent(long root_window, int x_root, int y_root, int x, int y, int state) {
		if (mouse != null)
			mouse.handlePointerMotion(grab, pointer_grabbed, shouldGrab(), root_window, x_root, y_root, x, y);
	}

	private static void handleWarpEvent(int x, int y) {
		if (mouse != null)
			mouse.handleWarpEvent(x, y);
	}

	private static void handleExposeEvent() {
		dirty = true;
	}

	private static void handleUnmapNotifyEvent() {
		dirty = true;
		minimized = true;
	}

	private static void handleMapNotifyEvent() {
		dirty = true;
		minimized = false;
	}

	private static void handleCloseEvent() {
		close_requested = true;
	}
}
