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
 * win32 mouse handling.
 *
 * @author elias_naur <elias_naur@users.sourceforge.net>
 * @version $Revision$
 */

#include "Window.h"
#include "org_lwjgl_input_Cursor.h"
#include "org_lwjgl_opengl_Win32Display.h"
#include "common_tools.h"

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_Win32Display_getMaxCursorSize
	(JNIEnv *env, jobject self)
{
	return GetSystemMetrics(SM_CXCURSOR);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_Win32Display_getMinCursorSize
	(JNIEnv *env, jobject self)
{
	return GetSystemMetrics(SM_CXCURSOR);
}


JNIEXPORT void JNICALL Java_org_lwjgl_opengl_Win32Display_nCreateCursor
  (JNIEnv *env, jobject self, jobject handle_buffer, jint width, jint height, jint x_hotspot, jint y_hotspot, jint num_images, jobject image_buffer, jint images_offset, jobject delay_buffer, jint delays_offset)
{
    unsigned char col0, col1, col2, col3, col4, col5, col6, col7;
    unsigned char mask;
    BITMAPINFO bitmapInfo;

    HBITMAP cursorMask;
    HCURSOR *cursor_handle;

    HCURSOR cursor = NULL;
    ICONINFO iconInfo;
    char *ptrCursorImage;
    HBITMAP colorDIB;
    int  *srcPtr;
    char *dstPtr;
        int bitWidth;
        int scanlinePad;
        int imageSize; // Size in bits
        unsigned char *maskPixels;
        int pixelCount = 0;
        int maskCount = 0;
	HBITMAP	colorBitmap;
	int x, y;

        int *pixels;
        if ((*env)->GetDirectBufferCapacity(env, handle_buffer) < sizeof(HCURSOR)) {
		throwException(env, "Handle buffer not large enough");
		return;
	}
        pixels = (int *)(*env)->GetDirectBufferAddress(env, image_buffer) + images_offset;

    memset(&bitmapInfo, 0, sizeof(BITMAPINFO));
    bitmapInfo.bmiHeader.biSize              = sizeof(BITMAPINFOHEADER);
    bitmapInfo.bmiHeader.biWidth             = width;
    bitmapInfo.bmiHeader.biHeight            = -height;
    bitmapInfo.bmiHeader.biPlanes            = 1;

    bitmapInfo.bmiHeader.biBitCount          = 24;
    bitmapInfo.bmiHeader.biCompression       = BI_RGB;

    colorDIB = CreateDIBSection(GetDC(NULL), (BITMAPINFO*)&(bitmapInfo),
                                    DIB_RGB_COLORS,
                                    (void**)&(ptrCursorImage),
                                    NULL, 0);
    srcPtr = pixels;
    dstPtr = ptrCursorImage;
    if (!dstPtr) {
		throwException(env, "Could not allocate DIB section.");
        return;
    }
    for (y = 0; y < height; y++ ) {
        for (x = 0; x < width; x++ ) {
            dstPtr[2] = (*srcPtr >> 0x10) & 0xFF;
            dstPtr[1] = (*srcPtr >> 0x08) & 0xFF;
            dstPtr[0] = *srcPtr & 0xFF;

            srcPtr++;
            dstPtr += 3;
        }
    }

    colorBitmap = CreateDIBitmap(GetDC(NULL),
                             (BITMAPINFOHEADER*)&bitmapInfo.bmiHeader,
                             CBM_INIT,
                             (void *)ptrCursorImage,
                             (BITMAPINFO*)&bitmapInfo,
                             DIB_RGB_COLORS);

    DeleteObject(colorDIB);

	// Convert alpha map to pixel packed mask
        bitWidth = width >> 3;
        scanlinePad = bitWidth & (sizeof(WORD) - 1);
        imageSize = (bitWidth + scanlinePad)*height; // Size in bits
        maskPixels = (unsigned char*)malloc(sizeof(unsigned char)*imageSize);
	memset(maskPixels, 0, imageSize);
	for (y = 0; y < height; y++)
		for (x = 0; x < bitWidth; x++) {
                        col0 = (pixels[pixelCount++] & 0x01000000) >> 17;
                        col1 = (pixels[pixelCount++] & 0x01000000) >> 18;
                        col2 = (pixels[pixelCount++] & 0x01000000) >> 19;
                        col3 = (pixels[pixelCount++] & 0x01000000) >> 20;
                        col4 = (pixels[pixelCount++] & 0x01000000) >> 21;
                        col5 = (pixels[pixelCount++] & 0x01000000) >> 22;
                        col6 = (pixels[pixelCount++] & 0x01000000) >> 23;
                        col7 = (pixels[pixelCount++] & 0x01000000) >> 24;
                        mask = col0 | col1 | col2 | col3 | col4 | col5 | col6 | col7;
			maskPixels[maskCount++] = ~mask; // 1 is tranparant, 0 opaque
		}

        cursorMask = CreateBitmap(width, height, 1, 1, maskPixels);

	memset(&iconInfo, 0, sizeof(ICONINFO));
	iconInfo.hbmMask = cursorMask;
	iconInfo.hbmColor = colorBitmap;
	iconInfo.fIcon = FALSE;
	iconInfo.xHotspot = x_hotspot;
	iconInfo.yHotspot = y_hotspot;
	cursor = CreateIconIndirect(&iconInfo);
	DeleteObject(colorBitmap);
	DeleteObject(cursorMask);
        free(maskPixels);

        cursor_handle = (HCURSOR *)(*env)->GetDirectBufferAddress(env, handle_buffer);
	*cursor_handle = cursor;
}

/*
 * Class:     org_lwjgl_input_Cursor
 * Method:    nDestroyCursor
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_Win32Display_destroyCursor
  (JNIEnv *env, jobject self, jobject handle_buffer)
{
//	HCURSOR cursor = (HCURSOR)cursor_handle;
        HCURSOR *cursor_handle = (HCURSOR *)(*env)->GetDirectBufferAddress(env, handle_buffer);
	DestroyCursor(*cursor_handle);
}
