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
 
/**
 * $Id$
 *
 * Win32 specific library for display handdling.
 *
 * @author cix_foo <cix_foo@users.sourceforge.net>
 * @version $Revision$
 */

#include "Window.h"
#include "org_lwjgl_Display.h"

#define WINDOWCLASSNAME "LWJGLWINDOW"

jobjectArray GetAvailableDisplayModesNT(JNIEnv * env);
jobjectArray GetAvailableDisplayModes9x(JNIEnv * env);
bool modeSet = false; // Whether we've done a display mode change
WORD* originalGamma = new WORD[256 * 3]; // Original gamma settings


/*
 * Class:     org_lwjgl_Display
 * Method:    nGetAvailableDisplayModes
 * Signature: ()[Lorg/lwjgl/DisplayMode;
 */
JNIEXPORT jobjectArray JNICALL Java_org_lwjgl_Display_nGetAvailableDisplayModes
  (JNIEnv * env, jclass clazz)
{
  // Determine whether to use a display name or NULL, which depends on the operating
	// system
	OSVERSIONINFO osvi;

	osvi.dwOSVersionInfoSize = sizeof(osvi);
	GetVersionEx(&osvi);
	
	if (osvi.dwPlatformId == VER_PLATFORM_WIN32_NT) {
#ifdef _DEBUG
  printf("Selecting NT display mode check");
#endif	
    return GetAvailableDisplayModesNT(env);
  }
  
#ifdef _DEBUG
  printf("Selecting 9x display mode check");
#endif	
  return GetAvailableDisplayModes9x(env);
}

/**
 * Choose displaymodes using NT codepath (multiple displaydevices)
 */
jobjectArray GetAvailableDisplayModesNT(JNIEnv * env) {
	int i = 0, j = 0, n = 0;
	int AvailableModes = 0;

	DISPLAY_DEVICE DisplayDevice;
	DEVMODE DevMode;

	DevMode.dmSize = sizeof(DEVMODE);
	DisplayDevice.cb = sizeof(DISPLAY_DEVICE);
  
  //enumerate all displays, and all of their displaymodes
	while(EnumDisplayDevices(NULL, i++, &DisplayDevice, 0) != 0) {
		while(EnumDisplaySettings(DisplayDevice.DeviceName, j++, &DevMode) != 0) {
			if (DevMode.dmBitsPerPel > 8) {
				AvailableModes++;
			}
		}
	}
		
#ifdef _DEBUG
	printf("Found %d displaymodes\n", AvailableModes);
#endif
  
	// now that we have the count create the classes, and add 'em all - we'll remove dups in Java
	// Allocate an array of DisplayModes big enough
	jclass displayModeClass = env->FindClass("org/lwjgl/DisplayMode");

	jobjectArray ret = env->NewObjectArray(AvailableModes, displayModeClass, NULL);
	jmethodID displayModeConstructor = env->GetMethodID(displayModeClass, "<init>", "(IIII)V");
  
	i = 0, j = 0, n = 0;
	while(EnumDisplayDevices(NULL, i++, &DisplayDevice, 0) != 0) {
		while(EnumDisplaySettings(DisplayDevice.DeviceName, j++, &DevMode) != 0) {
			// Filter out indexed modes
			if (DevMode.dmBitsPerPel > 8) {
				jobject displayMode;
				displayMode = env->NewObject(displayModeClass, displayModeConstructor, 
											 DevMode.dmPelsWidth, DevMode.dmPelsHeight,
											 DevMode.dmBitsPerPel, DevMode.dmDisplayFrequency);

				env->SetObjectArrayElement(ret, n++, displayMode);
			}
		}
	}
	return ret;
}

/**
 * Choose displaymodes using 9x codepath (single displaydevice)
 */
jobjectArray GetAvailableDisplayModes9x(JNIEnv * env) {
	int i = 0, j = 0, n = 0;
	int AvailableModes = 0;

	DEVMODE DevMode;

	DevMode.dmSize = sizeof(DEVMODE);
  
	//enumerate all displaymodes
	while(EnumDisplaySettings(NULL, j++, &DevMode) != 0) {
		if (DevMode.dmBitsPerPel > 8) {
			AvailableModes++;
		}
	}
		
#ifdef _DEBUG
	printf("Found %d displaymodes\n", AvailableModes);
#endif
  
	// now that we have the count create the classes, and add 'em all - we'll remove dups in Java
	// Allocate an array of DisplayModes big enough
	jclass displayModeClass = env->FindClass("org/lwjgl/DisplayMode");

	jobjectArray ret = env->NewObjectArray(AvailableModes, displayModeClass, NULL);
	jmethodID displayModeConstructor = env->GetMethodID(displayModeClass, "<init>", "(IIII)V");  
  
	i = 0, j = 0, n = 0;
	while(EnumDisplaySettings(NULL, j++, &DevMode) != 0) {
		// Filter out indexed modes
		if (DevMode.dmBitsPerPel > 8) {
			jobject displayMode;
			displayMode = env->NewObject(displayModeClass, displayModeConstructor,
			                              DevMode.dmPelsWidth, DevMode.dmPelsHeight,
						                  DevMode.dmBitsPerPel, DevMode.dmDisplayFrequency);
			env->SetObjectArrayElement(ret, n++, displayMode);
		}
	}
	return ret;
}


/*
 * Class:     org_lwjgl_Display
 * Method:    getPlatform
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_lwjgl_Display_getPlatform
  (JNIEnv * env, jclass clazz)
{
	return org_lwjgl_Display_PLATFORM_WGL;
}

/*
 * Class:     org_lwjgl_Display
 * Method:    setDisplayMode
 * Signature: (Lorg/lwjgl/DisplayMode;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_Display_setDisplayMode
  (JNIEnv * env, jclass clazz, jobject mode)
{

	jclass cls_displayMode = env->FindClass("org/lwjgl/DisplayMode");
	jfieldID fid_width = env->GetFieldID(cls_displayMode, "width", "I");
	jfieldID fid_height = env->GetFieldID(cls_displayMode, "height", "I");
	jfieldID fid_bpp = env->GetFieldID(cls_displayMode, "bpp", "I");
	jfieldID fid_freq = env->GetFieldID(cls_displayMode, "freq", "I");

	int width = env->GetIntField(mode, fid_width);
	int height = env->GetIntField(mode, fid_height);
	int bpp = env->GetIntField(mode, fid_bpp);
	int freq = env->GetIntField(mode, fid_freq);

	DEVMODE devmode;
	devmode.dmSize = sizeof(DEVMODE);
	devmode.dmBitsPerPel = bpp;
	devmode.dmPelsWidth = width;
	devmode.dmPelsHeight = height;
	devmode.dmDisplayFlags = 0;
	devmode.dmDisplayFrequency = freq;
	devmode.dmFields = DM_BITSPERPEL | DM_PELSWIDTH | DM_PELSHEIGHT | DM_DISPLAYFLAGS;
	if (freq != 0)
		devmode.dmFields |= DM_DISPLAYFREQUENCY;


	LONG cdsret = ChangeDisplaySettings(&devmode, CDS_FULLSCREEN);

	if (cdsret != DISP_CHANGE_SUCCESSFUL) {
		// Failed: so let's check to see if it's a wierd dual screen display
#ifdef _DEBUG
		printf("Failed to set display mode... assuming dual monitors\n");
#endif
		devmode.dmPelsWidth = width * 2;
		cdsret = ChangeDisplaySettings(&devmode, CDS_FULLSCREEN);

		if (cdsret != DISP_CHANGE_SUCCESSFUL) {
#ifdef _DEBUG
			printf("Failed to set display mode using dual monitors\n");
#endif
			throwException(env, "Failed to set display mode.");
			return;
		}
	}

	// The change was successful but might not be the exact change we were expecting.
	// Now we'll construct a new DisplayMode instance and stash it back in the Display
	// class's mode instance variable.

	// Get the screen
	HDC screenDC = GetDC(NULL);
	// Get the device caps
	width = GetDeviceCaps(screenDC, HORZRES);
	height = GetDeviceCaps(screenDC, VERTRES);
	bpp = GetDeviceCaps(screenDC, COLORRES);
	freq = GetDeviceCaps(screenDC, VREFRESH);
	if (freq <= 1)
		freq = 0; // Unknown
	ReleaseDC(NULL, screenDC);

	jmethodID ctor = env->GetMethodID(cls_displayMode, "<init>", "(IIII)V");
	jobject newMode = env->NewObject(cls_displayMode, ctor, width, height, bpp, freq);
	jfieldID fid_initialMode = env->GetStaticFieldID(clazz, "mode", "Lorg/lwjgl/DisplayMode;");
	env->SetStaticObjectField(clazz, fid_initialMode, newMode);
	env->DeleteLocalRef(newMode);

	modeSet = true;
}

/*
 * Class:     org_lwjgl_Display
 * Method:    resetDisplayMode
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_Display_resetDisplayMode
  (JNIEnv * env, jclass clazz)
{
	
	// Return device gamma to normal
	HDC screenDC = GetDC(NULL);
	SetDeviceGammaRamp(screenDC, originalGamma);
	ReleaseDC(NULL, screenDC);	

	if (modeSet) {
		modeSet = false;
		// Under Win32, all we have to do is:
		ChangeDisplaySettings(NULL, 0);

		// And we'll call init() again to put the correct mode back in Display
		Java_org_lwjgl_Display_init(env, clazz);
	}
}

/*
 * Class:     org_lwjgl_Display
 * Method:    getGammaRamp
 * Signature: ()[I
 */
JNIEXPORT jboolean JNICALL Java_org_lwjgl_Display_getGammaRamp
  (JNIEnv * env, jclass clazz, jintArray red, jintArray green, jintArray blue)
{
#ifdef _DEBUG
	if (red == NULL) {
		throwRuntimeException(env, "Null red array.");
		return JNI_FALSE;
	}
	if (green == NULL) {
		throwRuntimeException(env, "Null green array.");
		return JNI_FALSE;
	}
	if (blue == NULL) {
		throwRuntimeException(env, "Null blue array.");
		return JNI_FALSE;
	}
	if (env->GetArrayLength(red) != 256) {
		throwRuntimeException(env, "Red array is not 256 long.");
		return JNI_FALSE;
	}
	if (env->GetArrayLength(green) != 256) {
		throwRuntimeException(env, "Green array is not 256 long.");
		return JNI_FALSE;
	}
	if (env->GetArrayLength(blue) != 256) {
		throwRuntimeException(env, "Blue array is not 256 long.");
		return JNI_FALSE;
	}
#endif

	jint * redPtr = env->GetIntArrayElements(red, NULL);
	jint * greenPtr = env->GetIntArrayElements(green, NULL);
	jint * bluePtr = env->GetIntArrayElements(blue, NULL);

	WORD currentGamma[768];
	HDC screenDC = GetDC(NULL);
	if (GetDeviceGammaRamp(screenDC, currentGamma) == FALSE) {
#ifdef _DEBUG
			printf("Failed to get device gamma\n");
#endif
		env->ReleaseIntArrayElements(red, redPtr, JNI_ABORT);
		env->ReleaseIntArrayElements(green, greenPtr, JNI_ABORT);
		env->ReleaseIntArrayElements(blue, bluePtr, JNI_ABORT);
		ReleaseDC(NULL, screenDC);
		return JNI_FALSE;
	}
	ReleaseDC(NULL, screenDC);
	for (int i = 0; i < 256; i ++) {
		redPtr[i] = (jint) currentGamma[i];
		greenPtr[i] = (jint) currentGamma[i + 256];
		bluePtr[i] = (jint) currentGamma[i + 512];
	}
	env->ReleaseIntArrayElements(red, redPtr, 0);
	env->ReleaseIntArrayElements(green, greenPtr, 0);
	env->ReleaseIntArrayElements(blue, bluePtr, 0);
	return JNI_TRUE;
}

/*
 * Class:     org_lwjgl_Display
 * Method:    setGammaRamp
 * Signature: ([I[I[I)V
 */
JNIEXPORT jboolean JNICALL Java_org_lwjgl_Display_setGammaRamp
  (JNIEnv * env, jclass clazz, jintArray red, jintArray green, jintArray blue)
{
#ifdef _DEBUG
	if (red == NULL) {
		throwRuntimeException(env, "Null red array.");
		return JNI_FALSE;
	}
	if (green == NULL) {
		throwRuntimeException(env, "Null green array.");
		return JNI_FALSE;
	}
	if (blue == NULL) {
		throwRuntimeException(env, "Null blue array.");
		return JNI_FALSE;
	}
	if (env->GetArrayLength(red) != 256) {
		throwRuntimeException(env, "Red array is not 256 long.");
		return JNI_FALSE;
	}
	if (env->GetArrayLength(green) != 256) {
		throwRuntimeException(env, "Green array is not 256 long.");
		return JNI_FALSE;
	}
	if (env->GetArrayLength(blue) != 256) {
		throwRuntimeException(env, "Blue array is not 256 long.");
		return JNI_FALSE;
	}
#endif

	jint * redPtr = env->GetIntArrayElements(red, NULL);
	jint * greenPtr = env->GetIntArrayElements(green, NULL);
	jint * bluePtr = env->GetIntArrayElements(blue, NULL);

	// Turn array of ints into array of RGB WORDs
	WORD newGamma[768];
	for (int i = 0; i < 256; i ++) {
		newGamma[i] = (WORD)(min(0x00010000, redPtr[i]));
		newGamma[i + 256] = (WORD)(min(0x00010000, greenPtr[i]));
		newGamma[i + 512] = (WORD)(min(0x00010000, bluePtr[i]));
	}
	jboolean ret;
	HDC screenDC = GetDC(NULL);
	if (SetDeviceGammaRamp(screenDC, newGamma) == FALSE) {
#ifdef _DEBUG
		printf("Failed to set device gamma\n");
#endif
		ret = JNI_FALSE;
	} else {
		ret = JNI_TRUE;
	}
	ReleaseDC(NULL, screenDC);

	env->ReleaseIntArrayElements(red, redPtr, JNI_ABORT);
	env->ReleaseIntArrayElements(green, greenPtr, JNI_ABORT);
	env->ReleaseIntArrayElements(blue, bluePtr, JNI_ABORT);

	return ret;
}


/*
 * Class:     org_lwjgl_Display
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_Display_init
  (JNIEnv * env, jclass clazz)
{
	// Determine the current screen resolution
	// Get the screen
	HDC screenDC = GetDC(NULL);
	if (!screenDC) {
		printf("Couldn't get screen DC!\n");
		return;
	}
	// Get the device caps
	int width = GetDeviceCaps(screenDC, HORZRES);
	int height = GetDeviceCaps(screenDC, VERTRES);
	int bpp = GetDeviceCaps(screenDC, COLORRES);
	int freq = GetDeviceCaps(screenDC, VREFRESH);
	if (freq <= 1)
		freq = 0; // Unknown

	jclass jclass_DisplayMode = env->FindClass("org/lwjgl/DisplayMode");
	jmethodID ctor = env->GetMethodID(jclass_DisplayMode, "<init>", "(IIII)V");
	jobject newMode = env->NewObject(jclass_DisplayMode, ctor, width, height, bpp, freq);
	jfieldID fid_initialMode = env->GetStaticFieldID(clazz, "mode", "Lorg/lwjgl/DisplayMode;");
	env->SetStaticObjectField(clazz, fid_initialMode, newMode);
	env->DeleteLocalRef(newMode);

	// Get the default gamma ramp
	if (GetDeviceGammaRamp(screenDC, originalGamma) == FALSE) {
#ifdef _DEBUG
		printf("Failed to get initial device gamma\n");
#endif
	}
	ReleaseDC(NULL, screenDC);
}





