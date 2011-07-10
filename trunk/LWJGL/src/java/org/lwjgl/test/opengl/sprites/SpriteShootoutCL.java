package org.lwjgl.test.opengl.sprites;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.PointerBuffer;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opencl.*;
import org.lwjgl.opencl.api.Filter;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.Util;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

import static org.lwjgl.opencl.CL10.*;
import static org.lwjgl.opencl.CL10GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Sprite rendering demo. In this version OpenCL is used for the animation
 * computations. CL_KHR_gl_sharing is required for sharing the animation
 * data with OpenGL for rendering.
 *
 * @author Spasi
 * @since 18/3/2011
 */
public final class SpriteShootoutCL {

	private static final int SCREEN_WIDTH  = 800;
	private static final int SCREEN_HEIGHT = 600;

	private static final int ANIMATION_TICKS = 60;

	private boolean run       = true;
	private boolean render    = true;
	private boolean colorMask = true;
	private boolean animate   = true;
	private boolean smooth;
	private boolean vsync;

	private int ballSize  = 42;
	private int ballCount = 100 * 1000;

	private SpriteRenderer renderer;

	// OpenGL stuff
	private int texID;
	private int texBigID;
	private int texSmallID;

	// OpenCL stuff

	private IntBuffer errorCode = BufferUtils.createIntBuffer(1);

	private CLDevice       clDevice;
	private CLContext      clContext;
	private CLCommandQueue queue;
	private CLProgram      program;
	private CLKernel       kernel;
	private CLMem          clTransform;

	private PointerBuffer kernelGlobalWorkSize;

	private SpriteShootoutCL() {
	}

	public static void main(String[] args) {
		try {
			new SpriteShootoutCL().start();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	private void start() throws LWJGLException {
		try {
			initGL();
			initCL();

			renderer = new SpriteRendererDefault();

			updateBalls(ballCount);
			run();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			destroy();
		}
	}

	private void initCL() throws LWJGLException {
		CL.create();

		final List<CLPlatform> platforms = CLPlatform.getPlatforms();
		if ( platforms == null )
			throw new RuntimeException("No OpenCL platforms found.");

		final CLPlatform platform = platforms.get(0);

		final PointerBuffer ctxProps = BufferUtils.createPointerBuffer(3);
		ctxProps.put(CL_CONTEXT_PLATFORM).put(platform.getPointer()).put(0).flip();

		// Find devices with GL sharing support
		final Filter<CLDevice> glSharingFilter = new Filter<CLDevice>() {
			public boolean accept(final CLDevice device) {
				final CLDeviceCapabilities caps = CLCapabilities.getDeviceCapabilities(device);
				return caps.CL_KHR_gl_sharing;
			}
		};
		final List<CLDevice> devices = platform.getDevices(CL_DEVICE_TYPE_GPU, glSharingFilter);

		if ( devices == null )
			throw new RuntimeException("No OpenCL GPU device found.");

		clDevice = devices.get(0);
		// Make sure we use only 1 device
		devices.clear();
		devices.add(clDevice);

		clContext = CLContext.create(platform, devices, new CLContextCallback() {
			protected void handleMessage(final String errinfo, final ByteBuffer private_info) {
				System.out.println("[CONTEXT MESSAGE] " + errinfo);
			}
		}, Display.getDrawable(), errorCode);
		checkCLError(errorCode);

		queue = clCreateCommandQueue(clContext, clDevice, 0, errorCode);
		checkCLError(errorCode);
	}

	private void initGL() throws LWJGLException {
		Display.setLocation((Display.getDisplayMode().getWidth() - SCREEN_WIDTH) / 2,
		                    (Display.getDisplayMode().getHeight() - SCREEN_HEIGHT) / 2);
		Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
		Display.setTitle("Sprite Shootout - CL");
		Display.create();

		if ( !GLContext.getCapabilities().OpenGL20 )
			throw new RuntimeException("OpenGL 2.0 is required for this demo.");

		// Setup viewport

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, -1.0, 1.0);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glViewport(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

		// Create textures

		try {
			texSmallID = createTexture("res/ball_sm.png");
			texBigID = createTexture("res/ball.png");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		texID = texBigID;

		// Setup rendering state

		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.0f);

		glColorMask(colorMask, colorMask, colorMask, false);
		glDepthMask(false);
		glDisable(GL_DEPTH_TEST);

		// Setup geometry

		Util.checkGLError();
	}

	private static int createTexture(final String path) throws IOException {
		final BufferedImage img = ImageIO.read(SpriteShootoutCL.class.getClassLoader().getResource(path));

		final int w = img.getWidth();
		final int h = img.getHeight();

		final ByteBuffer buffer = readImage(img);

		final int texID = glGenTextures();

		glBindTexture(GL_TEXTURE_2D, texID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w, h, 0, GL_BGRA, GL_UNSIGNED_BYTE, buffer);

		return texID;
	}

	private static ByteBuffer readImage(final BufferedImage img) throws IOException {
		final Raster raster = img.getRaster();

		final int bands = raster.getNumBands();

		final int w = img.getWidth();
		final int h = img.getHeight();

		final int size = w * h * bands;

		final byte[] pixels = new byte[size];
		raster.getDataElements(0, 0, w, h, pixels);

		final ByteBuffer pbuffer = BufferUtils.createByteBuffer(size);

		if ( bands == 4 ) {
			for ( int i = 0; i < (w * h * 4); i += 4 ) {
				// Pre-multiply alpha
				final float a = unpackUByte01(pixels[i + 3]);
				pbuffer.put(packUByte01(unpackUByte01(pixels[i + 2]) * a));
				pbuffer.put(packUByte01(unpackUByte01(pixels[i + 1]) * a));
				pbuffer.put(packUByte01(unpackUByte01(pixels[i + 0]) * a));
				pbuffer.put(pixels[i + 3]);
			}
		} else if ( bands == 3 ) {
			for ( int i = 0; i < (w * h * 3); i += 3 ) {
				pbuffer.put(pixels[i + 2]);
				pbuffer.put(pixels[i + 1]);
				pbuffer.put(pixels[i + 0]);
			}
		} else
			pbuffer.put(pixels, 0, size);

		pbuffer.flip();

		return pbuffer;
	}

	private static float unpackUByte01(final byte x) {
		return (x & 0xFF) / 255.0f;
	}

	private static byte packUByte01(final float x) {
		return (byte)(x * 255.0f);
	}

	private void updateBalls(final int count) {
		System.out.println("NUMBER OF BALLS: " + count);
		renderer.updateBalls(ballCount);
	}

	private void run() {
		long startTime = System.currentTimeMillis() + 5000;
		long fps = 0;

		long time = Sys.getTime();
		final int ticksPerUpdate = (int)(Sys.getTimerResolution() / ANIMATION_TICKS);

		renderer.render(false, true, 0);

		while ( run ) {
			Display.processMessages();
			handleInput();

			glClear(GL_COLOR_BUFFER_BIT);

			final long currTime = Sys.getTime();
			final int delta = (int)(currTime - time);
			if ( smooth || delta >= ticksPerUpdate ) {
				renderer.render(render, animate, delta);
				time = currTime;
			} else
				renderer.render(render, false, 0);

			Display.update(false);

			if ( startTime > System.currentTimeMillis() ) {
				fps++;
			} else {
				long timeUsed = 5000 + (startTime - System.currentTimeMillis());
				startTime = System.currentTimeMillis() + 5000;
				System.out.println("FPS: " + (Math.round(fps / (timeUsed / 1000.0) * 10) / 10.0) + ", Balls: " + ballCount);
				fps = 0;
			}
		}
	}

	private void handleInput() {
		if ( Display.isCloseRequested() )
			run = false;

		while ( Keyboard.next() ) {
			if ( Keyboard.getEventKeyState() )
				continue;

			switch ( Keyboard.getEventKey() ) {
				case Keyboard.KEY_1:
				case Keyboard.KEY_2:
				case Keyboard.KEY_3:
				case Keyboard.KEY_4:
				case Keyboard.KEY_5:
				case Keyboard.KEY_6:
				case Keyboard.KEY_7:
				case Keyboard.KEY_8:
				case Keyboard.KEY_9:
				case Keyboard.KEY_0:
					ballCount = 1 << (Keyboard.getEventKey() - Keyboard.KEY_1);
					updateBalls(ballCount);
					break;
				case Keyboard.KEY_ADD:
				case Keyboard.KEY_SUBTRACT:
					int mult;
					if ( Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) )
						mult = 1000;
					else if ( Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU) )
						mult = 100;
					else if ( Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) )
						mult = 10;
					else
						mult = 1;
					if ( Keyboard.getEventKey() == Keyboard.KEY_SUBTRACT )
						mult = -mult;
					ballCount += mult * 100;
					if ( ballCount <= 0 )
						ballCount = 1;
					updateBalls(ballCount);
					break;
				case Keyboard.KEY_ESCAPE:
					run = false;
					break;
				case Keyboard.KEY_A:
					animate = !animate;
					System.out.println("Animation is now " + (animate ? "on" : "off") + ".");
					break;
				case Keyboard.KEY_C:
					colorMask = !colorMask;
					glColorMask(colorMask, colorMask, colorMask, false);
					System.out.println("Color mask is now " + (colorMask ? "on" : "off") + ".");
					// Disable alpha test when color mask is off, else we get no benefit.
					if ( colorMask ) {
						glEnable(GL_BLEND);
						glEnable(GL_ALPHA_TEST);
					} else {
						glDisable(GL_BLEND);
						glDisable(GL_ALPHA_TEST);
					}
					break;
				case Keyboard.KEY_R:
					render = !render;
					System.out.println("Rendering is now " + (render ? "on" : "off") + ".");
					break;
				case Keyboard.KEY_S:
					smooth = !smooth;
					System.out.println("Smooth animation is now " + (smooth ? "on" : "off") + ".");
					break;
				case Keyboard.KEY_T:
					if ( texID == texBigID ) {
						texID = texSmallID;
						ballSize = 16;
					} else {
						texID = texBigID;
						ballSize = 42;
					}
					renderer.updateBallSize();
					glBindTexture(GL_TEXTURE_2D, texID);
					System.out.println("Now using the " + (texID == texBigID ? "big" : "small") + " texture.");
					break;
				case Keyboard.KEY_V:
					vsync = !vsync;
					Display.setVSyncEnabled(vsync);
					System.out.println("VSYNC is now " + (vsync ? "enabled" : "disabled") + ".");
					break;
			}
		}

		while ( Mouse.next() ) ;
	}

	private static void checkCLError(IntBuffer buffer) {
		org.lwjgl.opencl.Util.checkCLError(buffer.get(0));
	}

	private void destroy() {
		if ( clContext != null )
			clReleaseContext(clContext);
		Display.destroy();
		System.exit(0);
	}

	private abstract class SpriteRenderer {

		protected int progID;
		protected int animVBO;

		protected void createKernel(final String source) {
			program = clCreateProgramWithSource(clContext, source, errorCode);
			checkCLError(errorCode);
			final int build = clBuildProgram(program, clDevice, "", null);
			if ( build != CL_SUCCESS ) {
				System.out.println("BUILD LOG: " + program.getBuildInfoString(clDevice, CL_PROGRAM_BUILD_LOG));
				throw new RuntimeException("Failed to build CL program, status: " + build);
			}

			kernel = clCreateKernel(program, "animate", errorCode);
			checkCLError(errorCode);

			kernelGlobalWorkSize = BufferUtils.createPointerBuffer(1);
			kernelGlobalWorkSize.put(0, ballCount);

			kernel.setArg(0, SCREEN_WIDTH);
			kernel.setArg(1, SCREEN_HEIGHT);
		}

		protected void createProgram(final int vshID) {
			final int fshID = glCreateShader(GL_FRAGMENT_SHADER);
			glShaderSource(fshID, "#version 110\n" +
			                      "uniform sampler2D COLOR_MAP;" +
			                      "void main(void) {\n" +
			                      "     gl_FragColor = texture2D(COLOR_MAP, gl_PointCoord);\n" +
			                      "}");
			glCompileShader(fshID);
			if ( glGetShader(fshID, GL_COMPILE_STATUS) == GL_FALSE ) {
				System.out.println(glGetShaderInfoLog(fshID, glGetShader(fshID, GL_INFO_LOG_LENGTH)));
				throw new RuntimeException("Failed to compile fragment shader.");
			}

			progID = glCreateProgram();
			glAttachShader(progID, vshID);
			glAttachShader(progID, fshID);
			glLinkProgram(progID);
			if ( glGetProgram(progID, GL_LINK_STATUS) == GL_FALSE ) {
				System.out.println(glGetProgramInfoLog(progID, glGetProgram(progID, GL_INFO_LOG_LENGTH)));
				throw new RuntimeException("Failed to link shader program.");
			}

			glUseProgram(progID);
			glUniform1i(glGetUniformLocation(progID, "COLOR_MAP"), 0);

			glEnableClientState(GL_VERTEX_ARRAY);
		}

		public void updateBallSize() {
			glPointSize(ballSize);
			kernel.setArg(2, ballSize * 0.5f);
		}

		public void updateBalls(final int count) {
			kernelGlobalWorkSize.put(0, ballCount);

			final FloatBuffer transform = BufferUtils.createFloatBuffer(count * 4);

			final Random random = new Random();
			for ( int i = 0; i < count; i++ ) {
				transform.put((int)(random.nextFloat() * (SCREEN_WIDTH - ballSize)) + ballSize * 0.5f);
				transform.put((int)(random.nextFloat() * (SCREEN_HEIGHT - ballSize)) + ballSize * 0.5f);
				transform.put(random.nextFloat() * 0.4f - 0.2f);
				transform.put(random.nextFloat() * 0.4f - 0.2f);
			}
			transform.flip();

			if ( animVBO != 0 ) {
				clReleaseMemObject(clTransform);
				glDeleteBuffers(animVBO);
			}

			animVBO = glGenBuffers();

			glBindBuffer(GL_ARRAY_BUFFER, animVBO);
			glBufferData(GL_ARRAY_BUFFER, transform, GL_STATIC_DRAW);
			glVertexPointer(2, GL_FLOAT, (4 * 4), 0);

			clTransform = clCreateFromGLBuffer(clContext, CL_MEM_READ_WRITE, animVBO, errorCode);
			checkCLError(errorCode);
			kernel.setArg(4, clTransform);
		}

		protected abstract void render(boolean render, boolean animate, int delta);

	}

	private class SpriteRendererDefault extends SpriteRenderer {

		SpriteRendererDefault() {
			System.out.println("Shootout Implementation: OpenCL GPU animation");

			final int vshID = glCreateShader(GL_VERTEX_SHADER);
			glShaderSource(vshID, "#version 150\n" +
			                      "void main(void) {\n" +
			                      "     gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n" +
			                      "}");
			glCompileShader(vshID);
			if ( glGetShader(vshID, GL_COMPILE_STATUS) == GL_FALSE ) {
				System.out.println(glGetShaderInfoLog(vshID, glGetShader(vshID, GL_INFO_LOG_LENGTH)));
				throw new RuntimeException("Failed to compile vertex shader.");
			}

			createProgram(vshID);

			Util.checkGLError();

			createKernel("kernel void animate(\n" +
			             "        const int WIDTH,\n" +
			             "        const int HEIGHT,\n" +
			             "        const float radius,\n" +
			             "        const int delta,\n" +
			             "        global float4 *balls\n" +
			             ") {\n" +
			             "    unsigned int b = get_global_id(0);\n" +
			             "\n" +
			             "     float4 anim = balls[b];\n" +
			             "     anim.xy = anim.xy + anim.zw * delta;\n" +
			             "     float2 animC = clamp(anim.xy, (float2)radius, (float2)(WIDTH - radius, HEIGHT - radius));\n" +
			             "     if ( anim.x != animC.x ) anim.z = -anim.z;\n" +
			             "     if ( anim.y != animC.y ) anim.w = -anim.w;\n" +
			             "\n" +
			             "     balls[b] = (float4)(animC, anim.zw);\n" +
			             "}");

			updateBallSize();
		}

		public void updateBalls(final int count) {
			super.updateBalls(count);
		}

		public void render(final boolean render, final boolean animate, final int delta) {
			if ( animate ) {
				//glFinish();

				kernel.setArg(3, delta);

				clEnqueueAcquireGLObjects(queue, clTransform, null, null);
				clEnqueueNDRangeKernel(queue, kernel, 1, null, kernelGlobalWorkSize, null, null, null);
				clEnqueueReleaseGLObjects(queue, clTransform, null, null);

				clFinish(queue);
			}

			if ( render )
				glDrawArrays(GL_POINTS, 0, ballCount);
		}

	}

}