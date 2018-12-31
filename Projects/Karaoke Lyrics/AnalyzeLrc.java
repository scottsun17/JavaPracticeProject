/*Author: Scott Sun
 	
 	Analyzing Lyric input from ReadLrc.java
 	
 	We break the lyric into two section
 		1. Song Information
 		2. Lyrics
 	
 	Song and Lyrics are stored separately in HashMap
 	 
 */
 
 
import java.util.ArrayList;
import java.util.HashMap;

public class AnalyzeLrc {
	/**
	 * The method helps the user to get all lyrics information
	 * @param lrcContents information from the LRC file
	 * @return ArrayList<String> includes all lyrics information
	 */
	public static ArrayList<String> getSongLrcFromLrcContents(String[] lrcContents) {
		ArrayList<String> allLrcInfo = new ArrayList<String>();
		
		if(null == lrcContents || lrcContents.length == 0) {
			throw new NullPointerException();
		}
		
		for(String string : lrcContents) {
			//ignore meaningless line space in the lrcConetents 
			if(string.length() > 3) {
				//analyze input information to differentiate song info and lyrics in according to the second char [index 1]
				if(string.charAt(1) >= '0' && string.charAt(1) <= '9') {
					//song lyrics
					allLrcInfo.add(string);
				}
			}
		}
		
		return allLrcInfo;
	}
	
	/**
	 * This method helps the user to get all song information
	 * @param lrcContents information from the LRC file
	 * @return ArrayList<String> includes all song information
	 */
	public static ArrayList<String> getSongInfoFromLrcContents(String[] lrcContents) {
		
		ArrayList<String> allSongInfo = new ArrayList<String>();
		
		if(null == lrcContents || lrcContents.length == 0) {
			throw new NullPointerException();
		}
		
		for(String string : lrcContents) {
			//ignore meaningless line space in the lrcConetents 
			if(string.length() > 3) {
				//analyze input information to differentiate song info and lyrics in according to the second char [index 1]
				//if it is lyrics, index 1 starts with number that represents minutes - see LRC file for example
				if(!(string.charAt(1) >= '0' && string.charAt(1) <= '9')) {
					// song info
					allSongInfo.add(string);
				}
			}
		}
		
		return allSongInfo;
	}
	
	/**
	 * Analyze song information and put into the HashMap
	 * @param allSongInfo 
	 * @return HashMap<String, String> includes all song information
	 */
	public static HashMap<String, String> analyzeSongInfo(ArrayList<String> allSongInfo) {
		HashMap<String, String> map = new HashMap<String, String>();
			
		for (String songInfo : allSongInfo) {
			map.put(songInfo.substring(0,  songInfo.indexOf(":")), 
					songInfo.substring((songInfo.indexOf(":") + 1), songInfo.indexOf("]")));
		
		}
			
		return map;

	}
	
	/**
	 * Analyze song information and put into the HashMap
	 * @param allLrcInfo
	 * @return HashMap<Integer, String> includes all time thread and lyrics information
	 */
	public static HashMap<Integer, String> analyzeLrcInfo(ArrayList<String> allLrcInfo){
		
		String lyrics = null;
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		
		for (String lrcInfo : allLrcInfo) {
			String[] arr = lrcInfo.split("]");
			
			//get lyrics - last element in the arr
			lyrics = arr[arr.length - 1]; 
			
			/*in case of repeating lyrics like the following: "[01:58.09][02:03.09][02:06.09]And I will nibble your ear"
			 * 
			 * after split by "]" we get the following in the arr
			 	index 0			[01:58.09
			 	index 1			[02:03.09
			 	index 2			[02:06.09
			 	index 3			And I will nibble your ear
			
			 * now loop through index 0 ~ 2 to deal with lyric time and map it to the HashMap
			 */
			for (int i = 0; i < arr.length -1; i++) {
				int min = Integer.valueOf(arr[i].substring(1, 3));
				int sec = Integer.valueOf(arr[i].substring(4, 6));
				int ms = Integer.valueOf(arr[i].substring(7, 9)) * 10;
				
				ms = (min * 60 + sec) * 1000 + ms;
				map.put(ms, lyrics);
			}		
		}	
		
		return map;
	}	
}
	
	
	

