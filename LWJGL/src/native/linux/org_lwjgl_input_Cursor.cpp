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
 * Linux cursor handling.
 *
 * @author elias_naur <elias_naur@users.sourceforge.net>
 * @version $Revision$
 */

#include "org_lwjgl_input_Cursor.h"
#include "extxcursor.h"
#include "Window.h"
#include "common_tools.h"

/*
 * Class:     org_lwjgl_input_Cursor
 * Method:    nCreateCursor
 * Signature: (IIIIIII)I
 */
JNIEXPORT jlong JNICALL Java_org_lwjgl_input_Cursor_nCreateCursor
  (JNIEnv *env, jclass clazz, jint width, jint height, jint x_hotspot, jint y_hotspot, jint num_images, jobject image_buffer, jint images_offset, jobject delay_buffer, jint delays_offset)
{
	Display *disp = incDisplay(env);
	if (disp == NULL)
		return 0;
  const int *delays = NULL;
	if (delay_buffer != NULL)
		delays = (const int *)env->GetDirectBufferAddress(delay_buffer) + delays_offset;		
	XcursorPixel *pixels = (XcursorPixel *)env->GetDirectBufferAddress(image_buffer) + images_offset;
	int stride = width*height;
	XcursorImages *cursor_images = XcursorImagesCreate(num_images);
	if (cursor_images == NULL)
		throwException(env, "Could not allocate cursor.");
	cursor_images->nimage = num_images;
	for (int i = 0; i < num_images; i++) {
		XcursorImage *cursor_image = XcursorImageCreate(width, height);
		cursor_image->xhot = x_hotspot;
		cursor_image->yhot = y_hotspot;
		cursor_image->pixels = &(pixels[stride*i]);
		if (num_images > 1)
			cursor_image->delay = delays[i];		
		cursor_images->images[i] = cursor_image;
	}
	Cursor cursor = XcursorImagesLoadCursor(disp, cursor_images);
	XcursorImagesDestroy(cursor_images);
	return cursor;
}

/*
 * Class:     org_lwjgl_input_Cursor
 * Method:    nDestroyCursor
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_input_Cursor_nDestroyCursor
  (JNIEnv *env, jclass clazz, jlong cursor_handle)
{
	Cursor cursor = (Cursor)cursor_handle;
	XFreeCursor(getDisplay(), cursor);
	decDisplay();
}
