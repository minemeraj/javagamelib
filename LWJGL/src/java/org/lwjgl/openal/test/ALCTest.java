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
package org.lwjgl.openal.test;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCcontext;
import org.lwjgl.openal.ALCdevice;

import java.nio.ByteBuffer;
/**
 * $Id$
 *
 * This is a test for the ALC part of OpenAL
 *
 * @author Brian Matzon <brian@matzon.dk>
 * @version $Revision$
 */
public class ALCTest extends BasicTest {
    
    /**
     * Creates an instance of ALCTest
     */
    public ALCTest() {
        super();
    }
    
    /**
     * Runs the actual test, using supplied arguments
     */
    protected void execute(String[] args) {
        //error stuff
        int lastError = ALC.NO_ERROR;
        
        //create a device
        device = alc.openDevice(null);
        if(device == null) {
            System.out.println("Unable to create device");
            System.exit(-1);
        }
        
        //create attribute list for context creation
        ByteBuffer buffer = ByteBuffer.allocate(28);
        buffer.putInt(ALC.FREQUENCY);
        buffer.putInt(44100);
        buffer.putInt(ALC.SYNC);
        buffer.putInt(ALC.FALSE);
        buffer.putInt(ALC.REFRESH);
        buffer.putInt(15);
        buffer.putInt(0); //terminating int
        
        //create a context
        context = alc.createContext(device, org.lwjgl.Sys.getDirectBufferAddress(buffer));
        if(context == null) {
            System.out.println("Unable to create context");
            System.exit(-1);
        }
        
        if((lastError = alc.getError(device)) != ALC.NO_ERROR) {
            System.out.println("ALC Error: " + alc.getString(device, lastError));
            System.exit(-1);
        }
        
        //make current
        alc.makeContextCurrent(context);
        
        //process
        //NOTE - weird bug exposed!
        //calling alcProcessContext on a non suspended context hangs
        //this is a OpenAL specific bug - not our code
        //alc.processContext(context);

        //suspend
        alc.suspendContext(context);
        
        //process again
        alc.processContext(context);
        
        //query        
        System.out.println("DEFAULT_DEVICE_SPECIFIER: " + alc.getString(device, ALC.DEFAULT_DEVICE_SPECIFIER));
        System.out.println("DEVICE_SPECIFIER: " + alc.getString(device, ALC.DEVICE_SPECIFIER));
        System.out.println("EXTENSIONS: " + alc.getString(device, ALC.EXTENSIONS));
        
        //mo query - hmmmmm don't get alcGetIntegerv
        /*buffer.rewind();
        alc.getIntegerv(device, ALC.MAJOR_VERSION, 32, org.lwjgl.Sys.getDirectBufferAddress(buffer));
        System.out.println("ALC_MAJOR_VERSION: " + buffer.getInt());
        
        alc.getIntegerv(device, ALC.MINOR_VERSION, 4, org.lwjgl.Sys.getDirectBufferAddress(buffer));
        System.out.println("ALC_MINOR_VERSION: " + buffer.getInt());
        */
        
        //check current context
        ALCcontext currentContext = alc.getCurrentContext();
        if(context.context != currentContext.context) {
            System.out.println("Serious error! - context copy != current context");
            System.exit(-1);
        } 

        //check contexts device
        ALCdevice currentDevice = alc.getContextsDevice(context);
        if(device.device != currentDevice.device) {
            System.out.println("Serious error! - device copy != current contexts device");
            System.exit(-1);
        }         
        
        //close context again
        alc.destroyContext(context);
        
        //close it
        alc.closeDevice(device);
    }
    
    /**
     * main entry point
     *
     * @param args String array containing arguments
     */
    public static void main(String[] args) {
        ALCTest alcTest = new ALCTest();
        alcTest.execute(args);
    }
}