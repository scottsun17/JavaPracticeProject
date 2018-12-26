/* File Sorting program
   
   What does it do?
 	
 	Sorting the directory in according to the extension of the files in the directory
 	
 	Regular files are sorted in according to their extension names
 	Directories are moved into subDir directory/folder
 	After the directory is sorted, a file.lock is created
 	To sort the directory again, delete the file.lock
	
	Special Event:
		a file without an extension name is moved to directory/folder - other
  
 	How is it achieved：
 		1. get file path and validate the file path
 		2. get all files and directories under the path and create a File type Array, File[]
 		3. iterate through the File[] and identify files and directories, then proceed accordingly  
 			
 			3.1 Files
 			3.2 Directories
		
		3.1-Files
			1. get the files extension name
			2. create a directory with the extension name of the file
			3. move the file to the directory
			
		3.2-Directories
			1. create subDir directory
			2. move all directories to the subDir
			
	Last Step:
		create a lock file to show that the sorting has been completed: file.lock
		if the directory has file.lock, the File Sorting program will not run
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DirectorySorting {
	public static void main(String[] args) throws FileNotFoundException, NullPointerException, IOException{
		//archiveDirectoryAtPath("file path");
		archiveDirectoryAtPath("C:\\Users\\Scott Sun\\Documents\\Test");
	}
	
	/**
	 * public method that execute the program to sort files in the diretcory
	 * @param path
	 * @throws IOException
	 */
	public static void archiveDirectoryAtPath(String path) throws IOException {
		//1. path validation
		if(null == path) {
			throw new NullPointerException("Invalid path");
		}
		
		//2. create file object in according to validated path
		File file = new File(path);
				
		//3. validate if it's a directory
		if(!file.exists() || !file.isDirectory()) {
			throw new FileNotFoundException("Directory not found");
		}
		
		//4. valid the directory has been sorted before, if true, archive terminate 
		if(new File(file, "file.lock").exists()) {
			System.out.println("Directory has been sorted");
			return;
		}
		
		//5. create a File type array, allFiles,to store all file information
		File[] allFiles = file.listFiles();
		
		//6. Iterator through allFiles to identify directory and regular file
		for (File fileItem : allFiles) {
			if(fileItem.isFile()) {
				//var file is the file to be sorted, fileName is the name of the file
				archiveFile(file, fileItem.getName());
			} else {
				//var file is the directory to be sorted, fileName is the name of the file
				archiveDir(file, fileItem.getName());
			}
		}
		
		//Sorting finished, lock the file
		File lockFile = new File(file, "file.lock");
		lockFile.createNewFile();
		
	}
	
	/**
	 * private method to archive files into their respective directory in according
	 * to their respective extension name 
	 * @param file 
	 * @param fileName
	 */
	private static void archiveFile(File file, String fileName) {
		//1. find associated extension name xxx.yyy find yyy
		int index = -1;
		String dirToCreate = null;
		File srcFile = null;
		File dstFile = null;
		
		/*2. get directory name from file to be used in creating new directory
			validating file's extension name by identifying the last '.' in the file name. 
			also return false if No dot or no extension after dot
		*/
		if((index = fileName.lastIndexOf('.')) != -1 && 
				fileName.substring(index + 1).length() != 0 ) {
			
			/*
			String parent = file.getAbsolutePath();
			String dirName = fileName.substring(index + 1).toUpperCase();
			dirToCreate = parent + File.separator + dirName;
				
			File.separator explained:
				a static declaration provided by File class to provide path separator for different OS
				Windows: \
				Linux & UNIX: /
				in this case, we don't have to manually differentiate which separator to use for the running OS environment 
			*/
			
			dirToCreate = file.getAbsolutePath() + File.separator + 
					fileName.substring(index + 1).toUpperCase();
		} else {
			dirToCreate = file.getAbsolutePath() + File.separator + "other";
		}
		
		new File(dirToCreate).mkdir();
		
		srcFile = new File(file, fileName);
		dstFile = new File(dirToCreate, fileName);
		
		srcFile.renameTo(dstFile);
	}
	
	
	/**
	 * private method to create subDir directory to store all directories 
	 * @param file
	 * @param dirName
	 */
	private static void archiveDir(File file, String dirName) {
		//create sub directory and then move
		File subDir = new File(file, "subDir");
		
		subDir.mkdir();
		
		File srcFile = new File(file, dirName);
		File dstFile = new File(subDir, dirName);
		
		srcFile.renameTo(dstFile);
		
	}
}
