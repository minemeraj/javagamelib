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
 * Win32 mouse handling.
 *
 * @author cix_foo <cix_foo@users.sourceforge.net>
 * @version $Revision$
 */


#include <X11/X.h>
#include <X11/Xlib.h>
#include <assert.h>
#include <string.h>
#include "org_lwjgl_input_Mouse.h"

#define NUM_BUTTONS 8

extern Display *disp;
extern Window win;

jfieldID fid_button;
jfieldID fid_dx;
jfieldID fid_dy;
jfieldID fid_dz;

int last_x;
int last_y;
int current_x;
int current_y;
unsigned char buttons[NUM_BUTTONS];

/*
 * Class:     org_lwjgl_input_Mouse
 * Method:    initIDs
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_input_Mouse_initIDs
  (JNIEnv * env, jclass clazz)
{
	// Get a global class instance, just to be sure
	static jobject globalClassLock = NULL;

	if (globalClassLock == NULL) {
		globalClassLock = (*env)->NewGlobalRef(env, clazz);
	}

	// Now cache the field IDs:
	if (fid_button == NULL) {
		fid_button = (*env)->GetStaticFieldID(env, clazz, "button", "[Z");
	}
	if (fid_dx == NULL) {
		fid_dx = (*env)->GetStaticFieldID(env, clazz, "dx", "I");
	}
	if (fid_dy == NULL) {
		fid_dy = (*env)->GetStaticFieldID(env, clazz, "dy", "I");
	}
	if (fid_dz == NULL) {
		fid_dz = (*env)->GetStaticFieldID(env, clazz, "dz", "I");
	}
}

/*
 * Class:     org_lwjgl_input_Mouse
 * Method:    nCreate
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_org_lwjgl_input_Mouse_nCreate
  (JNIEnv * env, jclass clazz)
{
	int i;
	current_x = current_y = last_x = last_y = 0;
	for (i = 0; i < NUM_BUTTONS; i++)
		buttons[i] = 0;
	int result = XGrabPointer(disp, win, False, PointerMotionMask | ButtonPressMask | ButtonReleaseMask, GrabModeAsync, GrabModeAsync, win, None, CurrentTime);
	if (result != GrabSuccess) {
#ifdef _DEBUG
		printf("Could not grab mouse\n");
#endif
		return JNI_FALSE;
	}
	return JNI_TRUE;
}

/*
 * Class:     org_lwjgl_input_Mouse
 * Method:    nDestroy
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_input_Mouse_nDestroy
  (JNIEnv * env, jclass clazz)
{
	XUngrabPointer(disp, CurrentTime);
}

void setButtonState(XButtonEvent event, unsigned char val) {
	switch (event.button) {
		case Button1:
			buttons[0] = val;
			break;
		case Button2:
			buttons[1] = val;
			break;
		case Button3:
			buttons[2] = val;
			break;
		case Button4:
			buttons[3] = val;
			break;
		case Button5:
			buttons[4] = val;
			break;
		default: assert(0);
	}
}

int checkPointer() {
	XEvent event;
	int count = 0;
	while (XCheckMaskEvent(disp, ButtonPressMask | ButtonReleaseMask | PointerMotionMask, &event)) {
		count++;
		switch (event.type) {
			case ButtonPress:
				setButtonState(event.xbutton, 1);
				break;
			case ButtonRelease:
				setButtonState(event.xbutton, 0);
				break;
			case MotionNotify:
				current_x = event.xbutton.x;
				current_y = event.xbutton.y;
				break;
			default: assert(0);
		}
	}
	return count;
}

/*
 * Class:     org_lwjgl_input_Mouse
 * Method:    nPoll
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_input_Mouse_nPoll
  (JNIEnv * env, jclass clazz)
{
	checkPointer();
	int moved_x = current_x - last_x;
	int moved_y = current_y - last_y;
	(*env)->SetStaticIntField(env, clazz, fid_dx, (jint)moved_x);
	(*env)->SetStaticIntField(env, clazz, fid_dy, (jint)moved_y);
	(*env)->SetStaticIntField(env, clazz, fid_dz, (jint)0);
	last_x = current_x;
	last_y = current_y;
	jbooleanArray buttonsArray = (jbooleanArray) (*env)->GetStaticObjectField(env, clazz, fid_button);
	unsigned char * class_buttons = (unsigned char *) (*env)->GetPrimitiveArrayCritical(env, buttonsArray, NULL);
	memcpy(class_buttons, buttons, NUM_BUTTONS*sizeof(unsigned char));
	(*env)->ReleasePrimitiveArrayCritical(env, buttonsArray, class_buttons, 0);
}
