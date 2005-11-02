/* MACHINE GENERATED FILE, DO NOT EDIT */

package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;
import org.lwjgl.BufferChecks;
import java.nio.*;

public class ARBBufferObject {
	public static final int GL_STREAM_DRAW_ARB = 0x88e0;
	public static final int GL_STREAM_READ_ARB = 0x88e1;
	public static final int GL_STREAM_COPY_ARB = 0x88e2;
	public static final int GL_STATIC_DRAW_ARB = 0x88e4;
	public static final int GL_STATIC_READ_ARB = 0x88e5;
	public static final int GL_STATIC_COPY_ARB = 0x88e6;
	public static final int GL_DYNAMIC_DRAW_ARB = 0x88e8;
	public static final int GL_DYNAMIC_READ_ARB = 0x88e9;
	public static final int GL_DYNAMIC_COPY_ARB = 0x88ea;
	public static final int GL_READ_ONLY_ARB = 0x88b8;
	public static final int GL_WRITE_ONLY_ARB = 0x88b9;
	public static final int GL_READ_WRITE_ARB = 0x88ba;
	public static final int GL_BUFFER_SIZE_ARB = 0x8764;
	public static final int GL_BUFFER_USAGE_ARB = 0x8765;
	public static final int GL_BUFFER_ACCESS_ARB = 0x88bb;
	public static final int GL_BUFFER_MAPPED_ARB = 0x88bc;
	public static final int GL_BUFFER_MAP_POINTER_ARB = 0x88bd;


	public static void glBindBufferARB(int target, int buffer) {
		StateTracker.bindBuffer(target, buffer);
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glBindBufferARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglBindBufferARB(target, buffer, function_pointer);
	}
	private static native void nglBindBufferARB(int target, int buffer, long function_pointer);

	public static void glDeleteBuffersARB(IntBuffer buffers) {
		StateTracker.deleteBuffers(buffers);
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glDeleteBuffersARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(buffers);
		nglDeleteBuffersARB((buffers.remaining()), buffers, buffers.position(), function_pointer);
	}
	private static native void nglDeleteBuffersARB(int n, IntBuffer buffers, int buffers_position, long function_pointer);

	public static void glGenBuffersARB(IntBuffer buffers) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glGenBuffersARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(buffers);
		nglGenBuffersARB((buffers.remaining()), buffers, buffers.position(), function_pointer);
	}
	private static native void nglGenBuffersARB(int n, IntBuffer buffers, int buffers_position, long function_pointer);

	public static boolean glIsBufferARB(int buffer) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glIsBufferARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		boolean __result = nglIsBufferARB(buffer, function_pointer);
		return __result;
	}
	private static native boolean nglIsBufferARB(int buffer, long function_pointer);

	public static void glBufferDataARB(int target, int size, int usage) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glBufferDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglBufferDataARB(target, size, null, 0, usage, function_pointer);
	}
	public static void glBufferDataARB(int target, ByteBuffer data, int usage) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glBufferDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(data);
		nglBufferDataARB(target, (data.remaining()), data, data.position(), usage, function_pointer);
	}
	public static void glBufferDataARB(int target, DoubleBuffer data, int usage) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glBufferDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(data);
		nglBufferDataARB(target, (data.remaining() << 3), data, data.position() << 3, usage, function_pointer);
	}
	public static void glBufferDataARB(int target, FloatBuffer data, int usage) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glBufferDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(data);
		nglBufferDataARB(target, (data.remaining() << 2), data, data.position() << 2, usage, function_pointer);
	}
	public static void glBufferDataARB(int target, IntBuffer data, int usage) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glBufferDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(data);
		nglBufferDataARB(target, (data.remaining() << 2), data, data.position() << 2, usage, function_pointer);
	}
	public static void glBufferDataARB(int target, ShortBuffer data, int usage) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glBufferDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(data);
		nglBufferDataARB(target, (data.remaining() << 1), data, data.position() << 1, usage, function_pointer);
	}
	private static native void nglBufferDataARB(int target, int size, Buffer data, int data_position, int usage, long function_pointer);

	public static void glBufferSubDataARB(int target, int offset, ByteBuffer data) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glBufferSubDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(data);
		nglBufferSubDataARB(target, offset, (data.remaining()), data, data.position(), function_pointer);
	}
	public static void glBufferSubDataARB(int target, int offset, DoubleBuffer data) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glBufferSubDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(data);
		nglBufferSubDataARB(target, offset, (data.remaining() << 3), data, data.position() << 3, function_pointer);
	}
	public static void glBufferSubDataARB(int target, int offset, FloatBuffer data) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glBufferSubDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(data);
		nglBufferSubDataARB(target, offset, (data.remaining() << 2), data, data.position() << 2, function_pointer);
	}
	public static void glBufferSubDataARB(int target, int offset, IntBuffer data) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glBufferSubDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(data);
		nglBufferSubDataARB(target, offset, (data.remaining() << 2), data, data.position() << 2, function_pointer);
	}
	public static void glBufferSubDataARB(int target, int offset, ShortBuffer data) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glBufferSubDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(data);
		nglBufferSubDataARB(target, offset, (data.remaining() << 1), data, data.position() << 1, function_pointer);
	}
	private static native void nglBufferSubDataARB(int target, int offset, int size, Buffer data, int data_position, long function_pointer);

	public static void glGetBufferSubDataARB(int target, int offset, ByteBuffer data) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glGetBufferSubDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(data);
		nglGetBufferSubDataARB(target, offset, (data.remaining()), data, data.position(), function_pointer);
	}
	public static void glGetBufferSubDataARB(int target, int offset, DoubleBuffer data) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glGetBufferSubDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(data);
		nglGetBufferSubDataARB(target, offset, (data.remaining() << 3), data, data.position() << 3, function_pointer);
	}
	public static void glGetBufferSubDataARB(int target, int offset, FloatBuffer data) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glGetBufferSubDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(data);
		nglGetBufferSubDataARB(target, offset, (data.remaining() << 2), data, data.position() << 2, function_pointer);
	}
	public static void glGetBufferSubDataARB(int target, int offset, IntBuffer data) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glGetBufferSubDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(data);
		nglGetBufferSubDataARB(target, offset, (data.remaining() << 2), data, data.position() << 2, function_pointer);
	}
	public static void glGetBufferSubDataARB(int target, int offset, ShortBuffer data) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glGetBufferSubDataARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkDirect(data);
		nglGetBufferSubDataARB(target, offset, (data.remaining() << 1), data, data.position() << 1, function_pointer);
	}
	private static native void nglGetBufferSubDataARB(int target, int offset, int size, Buffer data, int data_position, long function_pointer);

	/**
	 * glMapBufferARB maps a gl vertex buffer buffer to a ByteBuffer. The oldBuffer argument can be null,
	 * in which case a new ByteBuffer will be created, pointing to the returned memory. If oldBuffer is non-null,
	 * it will be returned if it points to the same mapped memory, otherwise a new ByteBuffer is created. That
	 * way, an application will normally use glMapBufferARB like this:
	 * <p/>
	 * ByteBuffer mapped_buffer; mapped_buffer = glMapBufferARB(..., ..., ..., null); ... // Another map on the same buffer mapped_buffer = glMapBufferARB(..., ..., ..., mapped_buffer);
	 * @param result_size   The size of the buffer area.
	 * @param old_buffer    A ByteBuffer. If this argument points to the same address as the new mapping, it will be returned and no new buffer will be created. In that case, size is ignored.
	 * @return A ByteBuffer representing the mapped buffer memory.
	 */
	public static java.nio.ByteBuffer glMapBufferARB(int target, int access, int result_size, java.nio.ByteBuffer old_buffer) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glMapBufferARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		if (old_buffer != null)
			BufferChecks.checkDirect(old_buffer);
		java.nio.ByteBuffer __result = nglMapBufferARB(target, access, result_size, old_buffer, function_pointer);
		return __result;
	}
	private static native java.nio.ByteBuffer nglMapBufferARB(int target, int access, int result_size, java.nio.ByteBuffer old_buffer, long function_pointer);

	public static boolean glUnmapBufferARB(int target) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glUnmapBufferARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		boolean __result = nglUnmapBufferARB(target, function_pointer);
		return __result;
	}
	private static native boolean nglUnmapBufferARB(int target, long function_pointer);

	public static void glGetBufferParameterARB(int target, int pname, IntBuffer params) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glGetBufferParameterivARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		BufferChecks.checkBuffer(params, 4);
		nglGetBufferParameterivARB(target, pname, params, params.position(), function_pointer);
	}
	private static native void nglGetBufferParameterivARB(int target, int pname, IntBuffer params, int params_position, long function_pointer);

	public static java.nio.ByteBuffer glGetBufferPointerARB(int target, int pname, int result_size) {
		long function_pointer = GLContext.getCapabilities().ARB_buffer_object_glGetBufferPointervARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		java.nio.ByteBuffer __result = nglGetBufferPointervARB(target, pname, result_size, function_pointer);
		return __result;
	}
	private static native java.nio.ByteBuffer nglGetBufferPointervARB(int target, int pname, int result_size, long function_pointer);
}
