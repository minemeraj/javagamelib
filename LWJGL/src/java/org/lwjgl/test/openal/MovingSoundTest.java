/*
 * Copyright (c) 2002 Lightweight Java Game Library Project
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
package org.lwjgl.test.openal;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.eax.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Window;
import org.lwjgl.vector.Vector3f;

import java.nio.IntBuffer;

/**
 * $Id$
 *
 * This test simulates a listener positioned in the center, and 
 * a source moving around the listener using the keyboard
 *
 * @author Brian Matzon <brian@matzon.dk>
 * @version $Revision$
 */
public class MovingSoundTest extends BasicTest {
  
  public static float MOVEMENT = 50.00f;

	/**
	 * Creates an instance of MovingSoundTest
	 */
	public MovingSoundTest() {
		super();
	}

	/**
	 * Runs the actual test, using supplied arguments
	 */
	protected void execute(String[] args) {
		if (args.length < 1) {
			System.out.println("no argument supplied, assuming Footsteps.wav");
      args = new String[] {"Footsteps.wav"};
		}

    try {
        Window.create("Moving Sound Test", 100, 100, 320, 240, 32, 0 ,0 ,0);
    } catch (Exception e) {
			e.printStackTrace();
		} 


		int lastError;
    Vector3f sourcePosition = new Vector3f();
    Vector3f listenerPosition = new Vector3f();
    boolean eaxApplied = false;
    EAXListenerProperties eaxListenerProp = null;
    
    //initialize keyboard
    try {
      Keyboard.create();
		} catch (Exception e) {
      e.printStackTrace();
      exit(-1);
		}

		//create 1 buffer and 1 source
		IntBuffer buffers = createIntBuffer(1);
		IntBuffer sources = createIntBuffer(1);

		// al generate buffers and sources
    buffers.position(0).limit(1);
		AL.alGenBuffers(buffers);
		if ((lastError = AL.alGetError()) != AL.AL_NO_ERROR) {
			exit(lastError);
		}

    sources.position(0).limit(1);
    AL.alGenSources(sources);
		if ((lastError = AL.alGetError()) != AL.AL_NO_ERROR) {
			exit(lastError);
		}

		//load wave data
		WaveData wavefile = WaveData.create(args[0]);

		//copy to buffers
    AL.alBufferData(
			buffers.get(0),
			wavefile.format,
			wavefile.data,
			wavefile.data.capacity(),
			wavefile.samplerate);
		if ((lastError = AL.alGetError()) != AL.AL_NO_ERROR) {
			exit(lastError);
		}

		//unload file again
		wavefile.dispose();

		//set up source input
    AL.alSourcei(sources.get(0), AL.AL_BUFFER, buffers.get(0));
		if ((lastError = AL.alGetError()) != AL.AL_NO_ERROR) {
			exit(lastError);
		}

    AL.alSourcef(sources.get(0), AL.AL_REFERENCE_DISTANCE, 1024.0f);
    AL.alSourcef(sources.get(0), AL.AL_ROLLOFF_FACTOR, 0.5f);

		//lets loop the sound
    AL.alSourcei(sources.get(0), AL.AL_LOOPING, AL.AL_TRUE);
		if ((lastError = AL.alGetError()) != AL.AL_NO_ERROR) {
			exit(lastError);
		}

		//play source 0
    AL.alSourcePlay(sources.get(0));
		if ((lastError = AL.alGetError()) != AL.AL_NO_ERROR) {
			exit(lastError);
		}
    
    //setup EAX if possible
    if (AL.alIsExtensionPresent("EAX")) {
      try {
          EAX.create();
          eaxListenerProp = new EAXListenerProperties();          
      } catch (Exception e) {
      }
    }    
    
    System.out.println("Move source with arrow keys\nMove listener with right shift and arrowkeys\nEnable EAX effect by pressing e (if available)\nExit with ESC");

		while(!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
      Window.updateState();
      
      Keyboard.poll();
      if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
        if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
          listenerPosition.x -= MOVEMENT;
          AL.alListener3f(AL.AL_POSITION, listenerPosition.x, listenerPosition.y, listenerPosition.z);
          System.out.println("listenerx: " + listenerPosition.x);
        } else {
          sourcePosition.x -= MOVEMENT;
          AL.alSource3f(sources.get(0), AL.AL_POSITION, sourcePosition.x, sourcePosition.y, sourcePosition.z);
          System.out.println("sourcex: " + sourcePosition.x);
        }
      }      
      if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
        if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
          listenerPosition.x += MOVEMENT;
          AL.alListener3f(AL.AL_POSITION, listenerPosition.x, listenerPosition.y, listenerPosition.z);
          System.out.println("listenerx: " + listenerPosition.x);
        } else {        
          sourcePosition.x += MOVEMENT;
          AL.alSource3f(sources.get(0), AL.AL_POSITION, sourcePosition.x, sourcePosition.y, sourcePosition.z);
          System.out.println("sourcex: " + sourcePosition.x);
        }
      }
      
      if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
          if(eaxApplied) {
            eaxListenerProp.setEnvironment(EAX.EAX_ENVIRONMENT_GENERIC);
            eaxListenerProp.eaxSet(EAXListenerProperties.EAXLISTENER_ENVIRONMENT, 0); 
          } else {
            eaxListenerProp.setEnvironment(EAX.EAX_ENVIRONMENT_HANGAR);
            eaxListenerProp.eaxSet(EAXListenerProperties.EAXLISTENER_ENVIRONMENT, 0); 
          }
          eaxApplied = !eaxApplied;
      }
      
      if(Window.isCloseRequested()) {
        break;
      }
     
      try {
        Thread.sleep(100);
			} catch (InterruptedException inte) {
			} 
    }

		//stop source 0
    AL.alSourceStop(sources.get(0));
		if ((lastError = AL.alGetError()) != AL.AL_NO_ERROR) {
			exit(lastError);
		}

		//delete buffers and sources
    sources.position(0).limit(1);
    AL.alDeleteSources(sources);
		if ((lastError = AL.alGetError()) != AL.AL_NO_ERROR) {
			exit(lastError);
		}

    buffers.position(0).limit(1);
    AL.alDeleteBuffers(buffers);
		if ((lastError = AL.alGetError()) != AL.AL_NO_ERROR) {
			exit(lastError);
		}

		//shutdown
		alExit();
	}

	/**
	 * main entry point
	 *
	 * @param args String array containing arguments
	 */
	public static void main(String[] args) {
		MovingSoundTest movingSoundTest = new MovingSoundTest();
		movingSoundTest.execute(args);
	}
}
