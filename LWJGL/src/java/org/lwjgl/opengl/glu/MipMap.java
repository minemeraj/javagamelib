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
package org.lwjgl.opengl.glu;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.BufferUtils;

/**
 * MipMap.java
 *
 *
 * Created 11-jan-2004
 * @author Erik Duijs
 */
public class MipMap extends Util {

	/**
	 * Method gluBuild2DMipmaps
	 *
	 * @param target
	 * @param components
	 * @param width
	 * @param height
	 * @param format
	 * @param type
	 * @param data
	 * @return int
	 */
	public static int gluBuild2DMipmaps(
		int target,
		int components,
		int width,
		int height,
		int format,
		int type,
		ByteBuffer data) {

		int retVal = 0;
		int error;
		int w, h, maxSize;
		ByteBuffer image, newimage;
		int neww, newh, level, bpp;
		boolean done;

		if (width < 1 || height < 1) return GLU.GLU_INVALID_VALUE;

		maxSize = glGetIntegerv(GL11.GL_MAX_TEXTURE_SIZE);

		w = nearestPower(width);
		if (w > maxSize) {
			w = maxSize;
		}
		h = nearestPower(height);
		if (h > maxSize) {
			h = maxSize;
		}

		bpp = bytesPerPixel(format, type);
		if (bpp == 0) {
			return GLU.GLU_INVALID_ENUM;
		}

		// Get current glPixelStore state
		PixelStoreState pss = new PixelStoreState();

		// set pixel packing
		GL11.glPixelStorei(GL11.GL_PACK_ROW_LENGTH, 0);
		GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
		GL11.glPixelStorei(GL11.GL_PACK_SKIP_ROWS, 0);
		GL11.glPixelStorei(GL11.GL_PACK_SKIP_PIXELS, 0);

		done = false;

		if (w != width || h != height) {
			// must rescale image to get "top" mipmap texture image
			image = BufferUtils.createByteBuffer((w + 4) * h * bpp);
			error = gluScaleImage(format, width, height, type, data, w, h, type, image);
			if (error != 0) {
				retVal = error;
				done = true;
			}
		} else {
			image = data;
		}

		level = 0;
		while (!done) {
			if (image != data) {
				/* set pixel unpacking */
				GL11.glPixelStorei(GL11.GL_UNPACK_ROW_LENGTH, 0);
				GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
				GL11.glPixelStorei(GL11.GL_UNPACK_SKIP_ROWS, 0);
				GL11.glPixelStorei(GL11.GL_UNPACK_SKIP_PIXELS, 0);
			}

			GL11.glTexImage2D(target, level, components, w, h, 0, format, type, image);

			if (w == 1 && h == 1)
				break;

			neww = (w < 2) ? 1 : w / 2;
			newh = (h < 2) ? 1 : h / 2;
			newimage = BufferUtils.createByteBuffer((neww + 4) * newh * bpp);

			error = gluScaleImage(format, w, h, type, image, neww, newh, type, newimage);
			if (error != 0) {
				retVal = error;
				done = true;
			}

			image = newimage;

			w = neww;
			h = newh;
			level++;
		}

		// Restore original glPixelStore state
		pss.save();

		return retVal;
	}

	/**
	 * Method gluScaleImage.
	 * @param format
	 * @param widthIn
	 * @param heightIn
	 * @param typein
	 * @param dataIn
	 * @param widthOut
	 * @param heightOut
	 * @param typeOut
	 * @param dataOut
	 * @return int
	 */
	public static int gluScaleImage(
		int format,
		int widthIn,
		int heightIn,
		int typein,
		ByteBuffer dataIn,
		int widthOut,
		int heightOut,
		int typeOut,
		ByteBuffer dataOut) {

		int components, i, j, k;
		float[] tempin, tempout;
		float sx, sy;
		int sizein, sizeout;
		int rowstride, rowlen;

		components = compPerPix(format);
		if (components == -1) {
			return GLU.GLU_INVALID_ENUM;
		}

		// temp image data
		tempin = new float[widthIn * heightIn * components];
		tempout = new float[widthOut * heightOut * components];

		// Determine bytes per input type
		switch (typein) {
			case GL11.GL_UNSIGNED_BYTE :
				sizein = 1;
				break;
			default :
				return GL11.GL_INVALID_ENUM;
		}

		// Determine bytes per output type
		switch (typeOut) {
			case GL11.GL_UNSIGNED_BYTE :
				sizeout = 1;
				break;

			default :
				return GL11.GL_INVALID_ENUM;
		}

		// Get glPixelStore state
		PixelStoreState pss = new PixelStoreState();

		//Unpack the pixel data and convert to floating point
		if (pss.unpackRowLength > 0) {
			rowlen = pss.unpackRowLength;
		} else {
			rowlen = widthIn;
		}
		if (sizein >= pss.unpackAlignment) {
			rowstride = components * rowlen;
		} else {
			rowstride = pss.unpackAlignment / sizein * ceil(components * rowlen * sizein, pss.unpackAlignment);
		}

		switch (typein) {
			case GL11.GL_UNSIGNED_BYTE :
				k = 0;
				dataIn.rewind();
				for (i = 0; i < heightIn; i++) {
					int ubptr = i * rowstride + pss.unpackSkipRows * rowstride + pss.unpackSkipPixels * components;
					for (j = 0; j < widthIn * components; j++) {
						tempin[k++] = (float) ((int) dataIn.get(ubptr++) & 0xff);
					}
				}
				break;

			default :
				return GLU.GLU_INVALID_ENUM;
		}

		// Do scaling
		sx = (float) widthIn / (float) widthOut;
		sy = (float) heightIn / (float) heightOut;

		float[] c = new float[components];
		int src, dst;

		for (int iy = 0; iy < heightOut; iy++) {
			for (int ix = 0; ix < widthOut; ix++) {
				int x0 = (int) (ix * sx);
				int x1 = (int) ((ix + 1) * sx);
				int y0 = (int) (iy * sy);
				int y1 = (int) ((iy + 1) * sy);

				int readPix = 0;

				// reset weighted pixel
				for (int ic = 0; ic < components; ic++) {
					c[ic] = 0;
				}

				// create weighted pixel
				for (int ix0 = x0; ix0 < x1; ix0++) {
					for (int iy0 = y0; iy0 < y1; iy0++) {

						src = (iy0 * widthIn + ix0) * components;

						for (int ic = 0; ic < components; ic++) {
							c[ic] += tempin[src + ic];
						}

						readPix++;
					}
				}

				// store weighted pixel
				dst = (iy * widthOut + ix) * components;

				if (readPix == 0) { 
				    // Image is sized up, caused by non power of two texture as input 
				    src = (y0 * widthIn + x0) * components; 
				    for (int ic = 0; ic < components; ic++) { 
				        tempout[dst++] = tempin[src + ic]; 
				    } 
				} else {
				    // sized down
				    for (k = 0; k < components; k++) { 
				        tempout[dst++] = c[k] / readPix; 
				    } 
				} 
			}
		}


		// Convert temp output
		if (pss.packRowLength > 0) {
			rowlen = pss.packRowLength;
		} else {
			rowlen = widthOut;
		}
		if (sizeout >= pss.packAlignment) {
			rowstride = components * rowlen;
		} else {
			rowstride = pss.packAlignment / sizeout * ceil(components * rowlen * sizeout, pss.packAlignment);
		}

		switch (typeOut) {
			case GL11.GL_UNSIGNED_BYTE :
				k = 0;
				for (i = 0; i < heightOut; i++) {
					int ubptr = i * rowstride + pss.packSkipRows * rowstride + pss.packSkipPixels * components;

					for (j = 0; j < widthOut * components; j++) {
						dataOut.put(ubptr++, (byte) tempout[k++]);
					}
				}
				break;

			default :
				return GLU.GLU_INVALID_ENUM;
		}

		return 0;
	}
}
