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
package org.lwjgl.test.openal;

import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALUTLoadWAVData;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

/**
 * $Id$
 *
 * This is a basic play test
 * Yes, over zealous use of getError ;)
 *
 * @author Brian Matzon <brian@matzon.dk>
 * @version $Revision$
 */
public class PlayTestMemory extends BasicTest {
    
    /**
     * Creates an instance of PlayTestMemory
     */
    public PlayTestMemory() {
        super();
    }

    /**
     * Runs the actual test, using supplied arguments
     */
    protected void execute(String[] args) {
        if(args.length < 1) {
            System.out.println("please specify filename to play");
            return;
        }        
        
        int lastError;
        
        //initialize AL, using ALC
        alInitialize();
        
        //create 1 buffer and 1 source
        IntBuffer buffers = createIntBuffer(1);
        IntBuffer sources = createIntBuffer(1);        
        
        // al generate buffers and sources
        al.genBuffers(1, Sys.getDirectBufferAddress(buffers));
        if((lastError = al.getError()) != AL.NO_ERROR) {
            exit(lastError);
        }

        al.genSources(1, Sys.getDirectBufferAddress(sources));
        if((lastError = al.getError()) != AL.NO_ERROR) {
            exit(lastError);
        }
        
        //load wave data
        ByteBuffer filebuffer = getData(args[0]);
        if(filebuffer == null) {
            System.out.println("Error loading file: " + args[0]);
            System.exit(-1);
        }
        
        ALUTLoadWAVData file = alut.loadWAVMemory(Sys.getDirectBufferAddress(filebuffer));
        if((lastError = al.getError()) != AL.NO_ERROR) {
            exit(lastError);
        }
        
        
        //copy to buffers
        al.bufferData(buffers.get(0), file.format, file.data, file.size, file.freq);
        if((lastError = al.getError()) != AL.NO_ERROR) {
            exit(lastError);
        }        
        
        //unload file again
        alut.unloadWAV(file.format, file.data, file.size, file.freq);        
        if((lastError = al.getError()) != AL.NO_ERROR) {
            exit(lastError);
        }        
        
        //set up source input            
        al.sourcei(sources.get(0), AL.BUFFER, buffers.get(0));
        if((lastError = al.getError()) != AL.NO_ERROR) {
            exit(lastError);
        }        
        
        //lets loop the sound
        al.sourcei(sources.get(0), AL.LOOPING, AL.TRUE);
        if((lastError = al.getError()) != AL.NO_ERROR) {
            exit(lastError);
        }        
        
        //play source 0
        al.sourcePlay(sources.get(0));
        if((lastError = al.getError()) != AL.NO_ERROR) {
            exit(lastError);
        }        
        
        //wait 5 secs
        try {
            System.out.println("Waiting 5 seconds for sound to complete");
            Thread.sleep(5000);
        } catch (InterruptedException inte) {
        }
        
        //stop source 0
        al.sourceStop(sources.get(0));
        if((lastError = al.getError()) != AL.NO_ERROR) {
            exit(lastError);
        }        
        
        //delete buffers and sources
        al.deleteSources(1, Sys.getDirectBufferAddress(sources));
        if((lastError = al.getError()) != AL.NO_ERROR) {
            exit(lastError);
        }
        
        al.deleteBuffers(1, Sys.getDirectBufferAddress(buffers));
        if((lastError = al.getError()) != AL.NO_ERROR) {
            exit(lastError);
        }        
        
        //no errorchecking from now on, since our context is gone.
        //shutdown
        alExit();
    }
    
    /**
     * Reads the file into a ByteBuffer
     *
     * @param filename Name of file to load
     * @return ByteBuffer containing file data
     */
    protected ByteBuffer getData(String filename) {
        ByteBuffer buffer = null;

        URL url = null;

        String cwd = System.getProperty("user.dir");

        try {
            url = new URL("file:///" + cwd + "/" + filename);
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
        
        System.out.println("Attempting to load: " + url);
        
        try {
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            int bufferLength = 4096;
            byte[] readBuffer = new byte[bufferLength];
            int read = -1;
            
            while((read = bis.read(readBuffer, 0, bufferLength)) != -1) {
                baos.write(readBuffer, 0, read);
            }
            
            //done reading, close
            bis.close();
            
            buffer = ByteBuffer.allocateDirect(baos.size());
            buffer.order(ByteOrder.nativeOrder());
            buffer.put(baos.toByteArray());
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return buffer;
    }    
    
    /**
     * main entry point
     *
     * @param args String array containing arguments
     */
    public static void main(String[] args) {
        PlayTestMemory playTestMemory = new PlayTestMemory();
        playTestMemory.execute(args);
    }
}