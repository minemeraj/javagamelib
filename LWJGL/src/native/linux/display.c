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

/**
 * $Id$
 *
 * Linux specific library for display handling.
 *
 * @author elias_naur <elias_naur@users.sourceforge.net>
 * @version $Revision$
 */

#include <X11/X.h>
#include <X11/Xlib.h>
#include <X11/extensions/xf86vmode.h>
#include <X11/extensions/Xrandr.h>
#include <X11/Xutil.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "display.h"
#include "common_tools.h"
#include "Window.h"
#include "org_lwjgl_opengl_LinuxDisplay.h"

#define NUM_XRANDR_RETRIES 5

typedef struct {
	int width;
	int height;
	int freq;
	union {
		int size_index; // Data for Xrandr extension
		XF86VidModeModeInfo xf86vm_modeinfo; // Data for XF86VidMode extension
	} mode_data;
} mode_info;

static int saved_width;
static int saved_height;
static int saved_freq;
static int current_width;
static int current_height;
static int current_freq;

int getScreenModeWidth(void) {
	return current_width;
}

int getScreenModeHeight(void) {
	return current_height;
}

static bool getXF86VidModeVersion(JNIEnv *env, Display *disp, int *major, int *minor) {
	int event_base, error_base;

	if (!XF86VidModeQueryExtension(disp, &event_base, &error_base)) {
		printfDebugJava(env, "XF86VidMode extension not available");
		return false;
	}
	if (!XF86VidModeQueryVersion(disp, major, minor)) {
		printfDebugJava(env, "Could not query XF86VidMode version");
		return false;
	}
	printfDebugJava(env, "XF86VidMode extension version %i.%i", *major, *minor);
	return true;
}

static bool getXrandrVersion(JNIEnv *env, Display *disp, int *major, int *minor) {
	int event_base, error_base;

	if (!XRRQueryExtension(disp, &event_base, &error_base)) {
		printfDebugJava(env, "Xrandr extension not available");
		return false;
	}
	if (!XRRQueryVersion(disp, major, minor)) {
		printfDebugJava(env, "Could not query Xrandr version");
		return false;
	}
	printfDebugJava(env, "Xrandr extension version %i.%i", *major, *minor);
	return true;
}

static bool isXrandrSupported(JNIEnv *env, Display *disp) {
	int major, minor;
	if (!getXrandrVersion(env, disp, &major, &minor))
		return false;
	return major >= 1;
}

static bool isXF86VidModeSupported(JNIEnv *env, Display *disp) {
	int minor_ver, major_ver;
	if (!getXF86VidModeVersion(env, disp, &major_ver, &minor_ver))
		return false;
	return major_ver >= 2;
}
	
JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_LinuxDisplay_isXrandrSupported(JNIEnv *env, jclass unused) {
	jboolean result = isXrandrSupported(env, getDisplay()) ? JNI_TRUE : JNI_FALSE;
	return result;
}

JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_LinuxDisplay_isXF86VidModeSupported(JNIEnv *env, jclass unused) {
	jboolean result = isXF86VidModeSupported(env, getDisplay()) ? JNI_TRUE : JNI_FALSE;
	return result;
}

static mode_info *getXrandrDisplayModes(Display *disp, int screen, int *num_modes) {
	int num_randr_sizes;
	XRRScreenSize *sizes = XRRSizes(disp, screen, &num_randr_sizes);
	mode_info *avail_modes = NULL;
	int list_size = 0;
	/* Count number of modes */
	int i;
	int mode_index = 0;
	for (i = 0; i < num_randr_sizes; i++) {
		int num_randr_rates;
		short *freqs = XRRRates(disp, screen, i, &num_randr_rates);
		int j;
		for (j = 0; j < num_randr_rates; j++) {
			if (list_size <= mode_index) {
				list_size += 1;
				avail_modes = (mode_info *)realloc(avail_modes, sizeof(mode_info)*list_size);
				if (avail_modes == NULL)
					return NULL;
			}
			avail_modes[mode_index].width = sizes[i].width;
			avail_modes[mode_index].height = sizes[i].height;
			avail_modes[mode_index].freq = freqs[j];
			avail_modes[mode_index].mode_data.size_index = i;
			mode_index++;
		}
	}
	*num_modes = mode_index;
	return avail_modes;
}

static mode_info *getXF86VidModeDisplayModes(Display *disp, int screen, int *num_modes) {
	int num_xf86vm_modes;
	XF86VidModeModeInfo **avail_xf86vm_modes;
	XF86VidModeGetAllModeLines(disp, screen, &num_xf86vm_modes, &avail_xf86vm_modes);
	mode_info *avail_modes = (mode_info *)malloc(sizeof(mode_info)*num_xf86vm_modes);
	if (avail_modes == NULL) {
		XFree(avail_xf86vm_modes);
		return NULL;
	}
	int i;
	for (i = 0; i < num_xf86vm_modes; i++) {
		avail_modes[i].width = avail_xf86vm_modes[i]->hdisplay;
		avail_modes[i].height = avail_xf86vm_modes[i]->vdisplay;
		avail_modes[i].freq = 0; // No frequency support in XF86VidMode
		avail_modes[i].mode_data.xf86vm_modeinfo = *avail_xf86vm_modes[i];
	}
	XFree(avail_xf86vm_modes);
	*num_modes = num_xf86vm_modes;
	return avail_modes;
}

static mode_info *getDisplayModes(Display *disp, int screen, jint extension, int *num_modes) {
	switch (extension) {
		case org_lwjgl_opengl_LinuxDisplay_XF86VIDMODE:
			return getXF86VidModeDisplayModes(disp, screen, num_modes);
		case org_lwjgl_opengl_LinuxDisplay_XRANDR:
			return getXrandrDisplayModes(disp, screen, num_modes);
		case org_lwjgl_opengl_LinuxDisplay_NONE:
			// fall through
		default:
			return NULL;
	}
}

static bool setXF86VidModeMode(Display *disp, int screen, mode_info *mode) {
	return True == XF86VidModeSwitchToMode(disp, screen, &mode->mode_data.xf86vm_modeinfo);
}

/* Try to set the mode specified through XRandR.
 * Return value is the Status code of the mode switch
 * The timestamp parameter is filled with the latest timestamp returned from XRRConfigTimes
 */
static Status trySetXrandrMode(Display *disp, int screen, mode_info *mode, Time *timestamp) {
	Status status;
	Drawable root_window = RootWindow(disp, screen);
	XRRScreenConfiguration *screen_configuration = XRRGetScreenInfo (disp, root_window);
	XRRConfigTimes(screen_configuration, timestamp);
	Rotation current_rotation;
	XRRConfigRotations(screen_configuration, &current_rotation);
	status = XRRSetScreenConfigAndRate(disp, screen_configuration, root_window, mode->mode_data.size_index, current_rotation, mode->freq, *timestamp);
	XRRFreeScreenConfigInfo(screen_configuration);
	return status;
}

static bool setXrandrMode(Display *disp, int screen, mode_info *mode) {
	int iteration;
	Time timestamp;
	Status status = trySetXrandrMode(disp, screen, mode, &timestamp);
	if (status == 0)
		return true; // Success
	Time new_timestamp;
	for (iteration = 0; iteration < NUM_XRANDR_RETRIES; iteration++) {
		status = trySetXrandrMode(disp, screen, mode, &new_timestamp);
		if (status == 0)
			return true; // Success
		if (new_timestamp == timestamp)
			return false; // Failure, and the stamps are equal meaning that the failure is not merely transient
		timestamp = new_timestamp;
	}
	return false;
}

static bool setMode(JNIEnv *env, Display *disp, int screen, jint extension, int width, int height, int freq, bool temporary) {
	int num_modes, i;
	mode_info *avail_modes = getDisplayModes(disp, screen, extension, &num_modes);
	if (avail_modes == NULL) {
		printfDebugJava(env, "Could not get display modes");
		return false;
	}
	bool result = false;
	for ( i = 0; i < num_modes; ++i ) {
		printfDebugJava(env, "Mode %d: %dx%d @%d", i, avail_modes[i].width, avail_modes[i].height, avail_modes[i].freq);
		if (avail_modes[i].width == width && avail_modes[i].height == height && avail_modes[i].freq == freq) {
			switch (extension) {
				case org_lwjgl_opengl_LinuxDisplay_XF86VIDMODE:
					if (!setXF86VidModeMode(disp, screen, &avail_modes[i])) {
						printfDebugJava(env, "Could not switch mode");
						continue;
					}
					break;
				case org_lwjgl_opengl_LinuxDisplay_XRANDR:
					if (!setXrandrMode(disp, screen, &avail_modes[i])) {
						printfDebugJava(env, "Could not switch mode");
						continue;
					}
					break;
				case org_lwjgl_opengl_LinuxDisplay_NONE: // Should never happen, since NONE imply no available display modes
				default:   // Should never happen
					continue;
			}
			result = true;
			if (!temporary) {
				current_width = width;
				current_height = height;
				current_freq = freq;
			}
			break;
		}
	}
	free(avail_modes);
	XFlush(disp);
	return result;
}

int getGammaRampLengthOfDisplay(JNIEnv *env, Display *disp, int screen) {
	int ramp_size;
	if (XF86VidModeGetGammaRampSize(disp, screen, &ramp_size) == False) {
		printfDebugJava(env, "XF86VidModeGetGammaRampSize call failed");
		return 0;
	}
	return ramp_size;
}

JNIEXPORT jobject JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nConvertToNativeRamp(JNIEnv *env, jclass unused, jobject ramp_buffer, jint buffer_offset, jint length) {
	const jfloat *ramp_ptr = (const jfloat *)(*env)->GetDirectBufferAddress(env, ramp_buffer) + buffer_offset;
	jobject native_ramp = newJavaManagedByteBuffer(env, length*3*sizeof(unsigned short));
	if (native_ramp == NULL) {
		throwException(env, "Failed to allocate gamma ramp buffer");
		return NULL;
	}
	unsigned short *native_ramp_ptr = (unsigned short *)(*env)->GetDirectBufferAddress(env, native_ramp);
	int i;
	for (i = 0; i < length; i++) {
		float scaled_gamma = ramp_ptr[i]*0xffff;
		short scaled_gamma_short = (unsigned short)round(scaled_gamma);
		native_ramp_ptr[i] = scaled_gamma_short;
		native_ramp_ptr[i + length] = scaled_gamma_short;
		native_ramp_ptr[i + length*2] = scaled_gamma_short;
	}
	return native_ramp;
}

jobject initDisplay(JNIEnv *env, int screen, jint extension) {
	int num_modes;
	mode_info *avail_modes;
	Display *disp = XOpenDisplay(NULL);
	if (disp == NULL) {
		throwException(env, "Could not open display");
		return NULL;
	}

	avail_modes = getDisplayModes(disp, screen, extension, &num_modes);
	if (avail_modes == NULL || num_modes == 0) {
		throwException(env, "Could not get display modes");
		XCloseDisplay(disp);
		return NULL;
	}
	saved_width = current_width = avail_modes[0].width;
	saved_height = current_height = avail_modes[0].height;
	saved_freq = current_freq = avail_modes[0].freq;
	int bpp = XDefaultDepth(disp, screen);
	printfDebugJava(env, "Original display dimensions: width %d, height %d freq %d", saved_width, saved_height, saved_freq);
	jclass jclass_DisplayMode = (*env)->FindClass(env, "org/lwjgl/opengl/DisplayMode");
	jmethodID ctor = (*env)->GetMethodID(env, jclass_DisplayMode, "<init>", "(IIII)V");
	jobject newMode = (*env)->NewObject(env, jclass_DisplayMode, ctor, saved_width, saved_height, bpp, saved_freq);

	free(avail_modes);

	XCloseDisplay(disp);
	return newMode;
}

JNIEXPORT jobject JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nGetCurrentGammaRamp(JNIEnv *env, jclass unused) {
	int ramp_size = getGammaRampLengthOfDisplay(env, getDisplay(), getCurrentScreen());
	jobject ramp_buffer = newJavaManagedByteBuffer(env, sizeof(unsigned short)*3*ramp_size);
	if (ramp_buffer == NULL) {
		throwException(env, "Could not allocate gamma ramp buffer");
		return NULL;
	}
	unsigned short *ramp = (unsigned short *)(*env)->GetDirectBufferAddress(env, ramp_buffer);
	if (!XF86VidModeGetGammaRamp(getDisplay(), getCurrentScreen(), ramp_size, ramp,
				ramp + ramp_size, ramp + ramp_size*2)) {
		throwException(env, "Could not get the current gamma ramp");
		return NULL;
	}
	return ramp_buffer;
}

static void setGamma(JNIEnv *env, Display *disp, int screen, jobject ramp_buffer, bool throw_on_error) {
	unsigned short *ramp_ptr = (unsigned short *)(*env)->GetDirectBufferAddress(env, ramp_buffer);
	jlong capacity = (*env)->GetDirectBufferCapacity(env, ramp_buffer);
	int size = capacity/(sizeof(unsigned short)*3);
	if (size == 0)
		return;
	if (XF86VidModeSetGammaRamp(disp, screen, size, ramp_ptr, ramp_ptr + size, ramp_ptr + size*2) == False) {
		if (throw_on_error)
			throwException(env, "Could not set gamma ramp.");
		else
			printfDebugJava(env, "Could not set gamma ramp.");
	}
}

void temporaryRestoreMode(JNIEnv *env, int screen, jint extension, jobject saved_gamma_ramp) {
	Display *disp = XOpenDisplay(NULL);
	if (disp == NULL) {
		printfDebugJava(env, "Could not open display");
		return;
	}
	if (!setMode(env, disp, screen, extension, current_width, current_height, current_freq, false))
		printfDebugJava(env, "Could not restore mode");
	// Don't propagate error to caller
	setGamma(env, disp, screen, saved_gamma_ramp, false);
	XCloseDisplay(disp);
}

void switchDisplayMode(JNIEnv * env, jobject mode, int screen, jint extension) {
	if (mode == NULL) {
		throwException(env, "mode must be non-null");
		return;
	}
	jclass cls_displayMode = (*env)->GetObjectClass(env, mode);
	jfieldID fid_width = (*env)->GetFieldID(env, cls_displayMode, "width", "I");
	jfieldID fid_height = (*env)->GetFieldID(env, cls_displayMode, "height", "I");
	jfieldID fid_freq = (*env)->GetFieldID(env, cls_displayMode, "freq", "I");
	int width = (*env)->GetIntField(env, mode, fid_width);
	int height = (*env)->GetIntField(env, mode, fid_height);
	int freq = (*env)->GetIntField(env, mode, fid_freq);
	Display *disp = XOpenDisplay(NULL);
	if (disp == NULL) {
		throwException(env, "Could not open display");
		return;
	}
	if (!setMode(env, disp, screen, extension, width, height, freq, false))
		throwException(env, "Could not switch mode.");
	XCloseDisplay(disp);
}

void resetDisplayMode(JNIEnv *env, int screen, jint extension, jobject gamma_ramp, bool temporary) {
	Display *disp = XOpenDisplay(NULL);
	if (disp == NULL) {
		printfDebugJava(env, "Failed to contact X Server");
		return;
	}
	if (!setMode(env, disp, screen, extension, saved_width, saved_height, saved_freq, temporary)) {
		printfDebugJava(env, "Failed to reset mode");
	}
	setGamma(env, disp, screen, gamma_ramp, false);
	XCloseDisplay(disp);
}

jobjectArray getAvailableDisplayModes(JNIEnv * env, Display *disp, int screen, jint extension) {
	int num_modes, i;
	mode_info *avail_modes;
	int bpp = XDefaultDepth(disp, screen);
	avail_modes = getDisplayModes(disp, screen, extension, &num_modes);
	if (avail_modes == NULL) {
		printfDebugJava(env, "Could not get display modes");
		XCloseDisplay(disp);
		return NULL;
	}
	// Allocate an array of DisplayModes big enough
	jclass displayModeClass = (*env)->FindClass(env, "org/lwjgl/opengl/DisplayMode");
	jobjectArray ret = (*env)->NewObjectArray(env, num_modes, displayModeClass, NULL);
	jmethodID displayModeConstructor = (*env)->GetMethodID(env, displayModeClass, "<init>", "(IIII)V");

	for (i = 0; i < num_modes; i++) {
		jobject displayMode = (*env)->NewObject(env, displayModeClass, displayModeConstructor, avail_modes[i].width, avail_modes[i].height, bpp, avail_modes[i].freq);
		(*env)->SetObjectArrayElement(env, ret, i, displayMode);
	}
	free(avail_modes);
	return ret;
}

void setGammaRamp(JNIEnv *env, jobject gamma_ramp_buffer, int screen) {
	Display * disp = XOpenDisplay(NULL);
	if (disp == NULL) {
		throwException(env, "Could not open display");
		return;
	}
	setGamma(env, disp, screen, gamma_ramp_buffer, true);
	XCloseDisplay(disp);
}