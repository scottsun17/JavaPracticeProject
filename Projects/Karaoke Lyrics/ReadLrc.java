/*Author: Scott Sun
 * 
 * This is the reader file that read lrc file from Path input from View.java
 * 
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadLrc {
	/**
	 * Get song lyrics from file path provided
	 * @param path LRC file path location
	 * @return a String array of song lyrics 
	 * @throws IOException File not found 
	 */
	public static String[] readLrcAtPath(String path) throws IOException {
		//validating file path
		if(null == path) {
			throw new NullPointerException("Invalid file path");
		}
		
		//1. get the LRC file
		File file = new File(path);
		
		//2. validating file 
		if(!file.exists() || !file.isFile()) {
			throw new FileNotFoundException("Invalid File");
		}
		
		//3. set up InputStream
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		int length = -1;
		byte[] buffer = new byte[1024 * 4];
		StringBuilder sb = new StringBuilder(); //save memory space
		
		while((length = bis.read(buffer)) != -1) {
			sb.append(new String(buffer, 0, length));
		}
		
		//4. close resource
		bis.close();
		
		//return a String type array
		return sb.toString().split("\n");

	}
}
