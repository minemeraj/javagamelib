package org.lwjgl.util;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * $Id$
 * <p>
 * NOTE: This simple XPM reader does not support extensions nor hotspots
 * </p>
 * @author Brian Matzon <brian@matzon.dk>
 * @version $Revision$
 */

public class XPMFile {

	/** Array of bytes (RGBA) */
	private byte	bytes[]	= null;

	/** Height of image */
	private int		height	= 0;

	/** Width of image */
	private int		width	= 0;

	/* 
	 * Private constructor, use load(String filename)  
	 */
	private XPMFile() { }
	
	/**
	 * Loads the XPM file 
	 * 
	 * @param file path to file
	 * @return XPMFile loaded, or exception 
	 * @throws IOException If any IO exceptions occurs while reading file
	 */
	public static XPMFile load(String file) throws IOException {
		return load(new FileInputStream(new File(file)));
	}
	
	/**
	 * Loads the XPM file 
	 * 
	 * @param is InputStream to read file from
	 * @return XPMFile loaded, or exception 
	 * @throws IOException If any IO exceptions occurs while reading file
	 */
	public static XPMFile load(InputStream is) throws IOException {
		XPMFile xFile = new XPMFile();
		xFile.readImage(is);
		return xFile;
	}	

	/** 
	 * @return the height of the image. 
	 */
	public int getHeight() {
		return height;
	}

	/** 
	 * @return the width of the image. 
	 */
	public int getWidth() {
		return width;
	}

	/** 
	 * @return The data of the image. 
	 */
	public byte[] getBytes() {
		return bytes;
	}

	/** 
	 * Read the image from the specified file. 
	 * 
	 * @throws IOException If any IO exceptions occurs while reading file
	 */
	private void readImage(InputStream is) throws IOException {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			HashMap colors = new HashMap();
	
			String comment = br.readLine();
			String typedef = br.readLine();
			int[] format = parseFormat(br.readLine());
	
			// setup color mapping
			for (int i = 0; i < format[2]; i++) {
				Object[] colorDefinition = parseColor(br.readLine());
				colors.put(colorDefinition[0], colorDefinition[1]);
			}
	
			// read actual image (convert to RGBA)
			bytes = new byte[format[0] * format[1] * 4];
			for (int i = 0; i < format[1]; i++) {
				parseImageLine(br.readLine(), format, colors, i);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to parse XPM File");
		}
	}

	/**
	 * Parses the format of the xpm file given a format string
	 *  
	 * @param format String to parse
	 * @return Array specifying width, height, colors, characters per pixel
	 */
	private int[] parseFormat(String format) {
		
		// format should look like this:
		// "16 16 122 2",
		
		// nuke first and last " and last ,
		format = format.substring(1, format.length() - 2);

		// tokenize it
		StringTokenizer st = new StringTokenizer(format);

		return new int[] { 
				Integer.parseInt(st.nextToken()), 	/* width */
				Integer.parseInt(st.nextToken()), 	/* height */
				Integer.parseInt(st.nextToken()), 	/* colors */
				Integer.parseInt(st.nextToken()) 	/* chars per pixel */
		};
	}

	/**
	 * Given a line defining a color/pixel, parses this into an array containing
	 * a key and a color
	 * @param line Line to parse
	 * @return Array containing a key (String) and a color (Integer)
	 */
	private Object[] parseColor(String line) {
		// line should look like this
		// "# 	c #0A0A0A",
		
		// nuke first and last "
		line = line.substring(1, line.length() - 2);

		String key = line.substring(0, 2);
		String type = line.substring(3, 4);
		String color = line.substring(6);

		// we always assume type is color, and supplied as #<r><g><b>
		return new Object[] { key, new Integer(Integer.parseInt(color, 16))};
	}

	/**
	 * Parses an Image line into its byte values
	 * @param line Line of chars to parse
	 * @param format Format to expext it in
	 * @param colors Colors to lookup
	 * @param index current index into lines, we've reached
	 */
	private void parseImageLine(String line, int[] format, HashMap colors, int index) {
		// offset for next line
		int offset = index * 4 * format[0];

		// nuke first "
		line = line.substring(1, line.length());

		// read format[3] characters format[0] times, each iteration is one color
		for (int i = 0; i < format[0]; i++) {
			String key = line.substring(i * 2, (i * 2 + 2));
			Integer color = (Integer) colors.get(key);
			bytes[offset +  (i * 4)     ]	= (byte) ((color.intValue() & 0x00ff0000) >> 16);
			bytes[offset + ((i * 4) + 1)] 	= (byte) ((color.intValue() & 0x0000ff00) >> 8);
			bytes[offset + ((i * 4) + 2)] 	= (byte) ((color.intValue() & 0x000000ff) >> 0); // looks better :)
			bytes[offset + ((i * 4) + 3)] 	= (byte) 0xff;	// always 0xff alpha 	
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("usage:\nXPMFile <file>");
		}

		try {
			String out = args[0].substring(0, args[0].indexOf(".")) + ".raw";
			XPMFile file = XPMFile.load(args[0]);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(out)));
			bos.write(file.getBytes());
			bos.close();

			//showResult(file.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	private static void showResult(byte[] bytes) {
		final BufferedImage i = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		int c = 0;
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 16; x++) {
				i.setRGB(x, y, (bytes[c] << 16) + (bytes[c + 1] << 8) + (bytes[c + 2] << 0) + (bytes[c + 3] << 24));//+(128<<24));//
				c += 4;
			}
		}

		final Frame frame = new Frame("XPM Result");
		frame.add(new Canvas() {

			public void paint(Graphics g) {
				g.drawImage(i, 0, 0, frame);
			}
		});

		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}

		});

		frame.setSize(100, 100);
		frame.setVisible(true);
	}*/
}