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
 * Win32 specific library for display handling.
 *
 * @author cix_foo <cix_foo@users.sourceforge.net>
 * @version $Revision$
 */

#include <windows.h>
// Multimon.h enables multi monitor emulation on win95 and winnt4
// So we only need the extended, multi-monitor aware path
#define COMPILE_MULTIMON_STUBS
#include <Multimon.h>
#include <jni.h>
#include "org_lwjgl_opengl_Win32Display.h"
#include "display.h"
#include "common_tools.h"

static bool modeSet = false; // Whether we've done a display mode change
static WORD originalGamma[3*org_lwjgl_opengl_Win32Display_GAMMA_LENGTH]; // Original gamma settings
static WORD currentGamma[3*org_lwjgl_opengl_Win32Display_GAMMA_LENGTH]; // Current gamma settings
static DEVMODE devmode; // Now we'll remember this value for the future

static jobject createDisplayMode(JNIEnv *env, DEVMODE *devmode) {
	jclass displayModeClass;

	jmethodID displayModeConstructor;

	displayModeClass = (*env)->FindClass(env, "org/lwjgl/opengl/DisplayMode");
	if (displayModeClass == NULL)
		return NULL;
	displayModeConstructor = (*env)->GetMethodID(env, displayModeClass, "<init>", "(IIII)V");
	if (displayModeConstructor == NULL)
		return NULL;

	return (*env)->NewObject(env, displayModeClass, displayModeConstructor, 
			devmode->dmPelsWidth, devmode->dmPelsHeight,
			devmode->dmBitsPerPel, devmode->dmDisplayFrequency);
}

/**
 * Choose displaymodes using extended codepath (multiple displaydevices)
 */
jobjectArray getAvailableDisplayModes(JNIEnv * env) {

	int i = 0, j = 0, n = 0;

//	DISPLAY_DEVICE DisplayDevice;
	DEVMODE DevMode;
	jobject *display_mode_objects = NULL;
	int list_size = 0;

	jclass displayModeClass;
	jobjectArray ret;
	displayModeClass = (*env)->FindClass(env, "org/lwjgl/opengl/DisplayMode");

	ZeroMemory(&DevMode, sizeof(DEVMODE));
//	ZeroMemory(&DisplayDevice, sizeof(DISPLAY_DEVICE));

	DevMode.dmSize = sizeof(DEVMODE);
//	DisplayDevice.cb = sizeof(DISPLAY_DEVICE);

	/* Multi-monitor stuff commented out since we're only really interested in the primary monitor */
/*	while(EnumDisplayDevices(NULL, i++, &DisplayDevice, 0) != 0) {
		// continue if mirroring device
		if((DisplayDevice.StateFlags & DISPLAY_DEVICE_MIRRORING_DRIVER) != 0) {
			continue;
		}

		j = 0;
		while(EnumDisplaySettings((const char *) DisplayDevice.DeviceName, j++, &DevMode) != 0) {*/
		while(EnumDisplaySettings(NULL, j++, &DevMode) != 0) {
			// Filter out indexed modes
			if (DevMode.dmBitsPerPel > 8 && ChangeDisplaySettings(&DevMode, CDS_FULLSCREEN | CDS_TEST) == DISP_CHANGE_SUCCESSFUL) {
				jobject displayMode;
				if (list_size <= n) {
					list_size += 1;
					display_mode_objects = (jobject *)realloc(display_mode_objects, sizeof(jobject)*list_size);
					if (display_mode_objects == NULL)
						return NULL;
				}
				displayMode = createDisplayMode(env, &DevMode);
				display_mode_objects[n++] = displayMode;
			}
		}
//	}
	printfDebugJava(env, "Found %d displaymodes", n);

	ret = (*env)->NewObjectArray(env, n, displayModeClass, NULL);
	for (i = 0; i < n; i++) {
		(*env)->SetObjectArrayElement(env, ret, i, display_mode_objects[i]);
	}
	free(display_mode_objects);   
	return ret;
}

void switchDisplayMode(JNIEnv * env, jobject mode)
{
	jclass cls_displayMode = (*env)->GetObjectClass(env, mode);
	jfieldID fid_width = (*env)->GetFieldID(env, cls_displayMode, "width", "I");
	jfieldID fid_height = (*env)->GetFieldID(env, cls_displayMode, "height", "I");
	jfieldID fid_bpp = (*env)->GetFieldID(env, cls_displayMode, "bpp", "I");
	jfieldID fid_freq = (*env)->GetFieldID(env, cls_displayMode, "freq", "I");

	int width = (*env)->GetIntField(env, mode, fid_width);
	int height = (*env)->GetIntField(env, mode, fid_height);
	int bpp = (*env)->GetIntField(env, mode, fid_bpp);
	int freq = (*env)->GetIntField(env, mode, fid_freq);
	LONG cdsret;

	ZeroMemory(&devmode, sizeof(DEVMODE));
	devmode.dmSize = sizeof(DEVMODE);
	devmode.dmBitsPerPel = bpp;
	devmode.dmPelsWidth = width;
	devmode.dmPelsHeight = height;
	devmode.dmDisplayFrequency = freq;
	devmode.dmFields = DM_BITSPERPEL | DM_PELSWIDTH | DM_PELSHEIGHT;
	if (freq != 0)
		devmode.dmFields |= DM_DISPLAYFREQUENCY;
	cdsret = ChangeDisplaySettings(&devmode, CDS_FULLSCREEN);

	if (cdsret != DISP_CHANGE_SUCCESSFUL) {
		/* What's the proper way to do this multiply with 2 thing, if at all necessary? */
/*		// Failed: so let's check to see if it's a wierd dual screen display
		printfDebugJava(env, "Failed to set display mode (%ld) ... assuming dual monitors", cdsret);
		devmode.dmPelsWidth = width * 2;
		cdsret = ChangeDisplaySettings(&devmode, CDS_FULLSCREEN);

		if (cdsret != DISP_CHANGE_SUCCESSFUL) {
			printfDebugJava(env, "Failed to set display mode using dual monitors (%ld)", cdsret);*/
			throwFormattedException(env, "Failed to set display mode (%ld).", cdsret);
			return;
//		}
	}
	modeSet = true;
}

void setGammaRamp(JNIEnv * env, jobject gammaRampBuffer)
{
	int i;
	float scaledRampEntry;
	WORD rampEntry;
	HDC screenDC;
	const float *gammaRamp = (const float *)(*env)->GetDirectBufferAddress(env, gammaRampBuffer);
	// Turn array of floats into array of RGB WORDs

	for (i = 0; i < org_lwjgl_opengl_Win32Display_GAMMA_LENGTH; i ++) {
		scaledRampEntry = gammaRamp[i]*0xffff;
		rampEntry = (WORD)scaledRampEntry;
		currentGamma[i] = rampEntry;
		currentGamma[i + org_lwjgl_opengl_Win32Display_GAMMA_LENGTH] = rampEntry;
		currentGamma[i + 2*org_lwjgl_opengl_Win32Display_GAMMA_LENGTH] = rampEntry;
	}
	screenDC = GetDC(NULL);
	if (SetDeviceGammaRamp(screenDC, currentGamma) == FALSE) {
		throwException(env, "Failed to set device gamma.");
	}
	ReleaseDC(NULL, screenDC);
}


jobject initDisplay(JNIEnv * env)
{
	DEVMODE devmode;
	jobject newMode;
	LONG cdsret;

	// Get the screen
	HDC screenDC = GetDC(NULL);
	if (!screenDC) {
		throwException(env, "Couldn't get screen DC!");
		return NULL;
	}
	// Get the default gamma ramp
	if (GetDeviceGammaRamp(screenDC, originalGamma) == FALSE) {
		printfDebugJava(env, "Failed to get initial device gamma");
	}
	memcpy(currentGamma, originalGamma, sizeof(WORD)*3*org_lwjgl_opengl_Win32Display_GAMMA_LENGTH);
	ReleaseDC(NULL, screenDC);

	if (!EnumDisplaySettings(NULL, ENUM_CURRENT_SETTINGS, &devmode)) {
		throwFormattedException(env, "Couldn't get current display settings (%ld)", GetLastError());
		return NULL;
	}
	newMode = createDisplayMode(env, &devmode);
	return newMode;
}

void resetDisplayMode(JNIEnv * env) {
	// Return device gamma to normal
	HDC screenDC = GetDC(NULL);
	if (!SetDeviceGammaRamp(screenDC, originalGamma)) {
		printfDebugJava(env, "Could not reset device gamma");
	}
	ReleaseDC(NULL, screenDC);	

	if (modeSet) {
		modeSet = false;
		// Under Win32, all we have to do is:
		ChangeDisplaySettings(NULL, 0);

		// And we'll call init() again to put the correct mode back in Display
		if (env != NULL)
			initDisplay(env);
	}
}

/*
 * Put display settings back to what they were when the window is maximized.
 */
void restoreDisplayMode(void) {
	// Restore gamma
	HDC screenDC = GetDC(NULL);
	LONG cdsret;
	if (!SetDeviceGammaRamp(screenDC, currentGamma)) {
		printfDebug("Could not restore device gamma\n");
	}
	ReleaseDC(NULL, screenDC);

	if (!modeSet) {
		printfDebug("Attempting to restore the display mode\n");
		modeSet = true;
		cdsret = ChangeDisplaySettings(&devmode, CDS_FULLSCREEN);

		if (cdsret != DISP_CHANGE_SUCCESSFUL) {
			printfDebug("Failed to restore display mode\n");
		}
	}
}

jstring getVersion(JNIEnv * env, char *driver)
{
#define BUFFER_SIZE 1024
	jstring ret = NULL;

	const char *dll_ext = ".dll";
	TCHAR driverDLL[BUFFER_SIZE] = "\0";
	DWORD var = 0;
	DWORD dwInfoSize;
	LPVOID lpInfoBuff;
	BOOL bRetval;

	if (driver == NULL) {
		return NULL;
	}
	strncat_s(driverDLL, BUFFER_SIZE, driver, strlen(driver));
	strncat_s(driverDLL, BUFFER_SIZE, dll_ext, strlen(dll_ext));
	dwInfoSize = GetFileVersionInfoSize(driverDLL, &var);
	lpInfoBuff = malloc(dwInfoSize);
	bRetval = GetFileVersionInfo(driverDLL, 0, dwInfoSize, lpInfoBuff);
	if (bRetval == 0) {
	} else {
		VS_FIXEDFILEINFO * fxdFileInfo;

		UINT uiLen = 0;
		bRetval = VerQueryValue(lpInfoBuff, TEXT("\\"), (void **) &fxdFileInfo, &uiLen);
		if (bRetval != 0) {
			TCHAR version[BUFFER_SIZE];
			TCHAR ms[BUFFER_SIZE], ls[BUFFER_SIZE];
			_snprintf_s(ms, BUFFER_SIZE, _TRUNCATE, "%d.%d\0", fxdFileInfo->dwProductVersionMS >> 16, fxdFileInfo->dwProductVersionMS & 0xFFFF);
			_snprintf_s(ls, BUFFER_SIZE, _TRUNCATE, "%d.%d\0", fxdFileInfo->dwProductVersionLS >> 16, fxdFileInfo->dwProductVersionLS & 0xFFFF);
			_snprintf_s(version, BUFFER_SIZE, _TRUNCATE, "%s.%s\0", ms, ls);
			ret = NewStringNative(env, version);
		}
	}

	free(lpInfoBuff);

	return ret;
}
