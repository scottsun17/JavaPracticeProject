/**
 * 
 * @author Scott Sun
 *
 * View File - dispay song information and lyrics by music time to achieve the effect of Karaoke
 * 
 * Song information and Lyric are stored in two HashMap
 * 
 * lyrics' delay displaying effect is achieved through Thread.sleep 
 *
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

public class View {
	public static void main(String[] args) throws IOException, InterruptedException {
		
		//path example C:\\Users\\Scott Sun\\Music\\Im yours by Jason Mraz.lrc
		//get Lyric contents from the reader
		String[] lrcContents = ReadLrc.readLrcAtPath("Input LRC file path here");
		
		//retrieve song and lyrics separately using AnalyzeLrc
		ArrayList<String> songInfo = AnalyzeLrc.getSongInfoFromLrcContents(lrcContents);
		ArrayList<String> lyrics = AnalyzeLrc.getSongLrcFromLrcContents(lrcContents);
		
		//map song info and lyrics to HashMap
		HashMap<String, String> songMap = AnalyzeLrc.analyzeSongInfo(songInfo);
		HashMap<Integer, String> lrcMap = AnalyzeLrc.analyzeLrcInfo(lyrics);
		
		show(songMap, lrcMap);
	}
	
	private static void show(HashMap<String, String> songMap, HashMap<Integer, String> lrcMap) throws InterruptedException {
		//display song information
		System.out.println("Song: " + songMap.get("[ti"));
		System.out.println("Singer: " + songMap.get("[ar"));
		System.out.println("Album: " + songMap.get("[al"));
		
		Set<Integer> timeSet = lrcMap.keySet();
		
		ArrayList<Integer> timeList = new ArrayList<Integer>();
		
		for (Integer time : timeSet) {
			timeList.add(time);
		}
		
		//rewrite Comparator to sort
		timeList.sort(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		});
		
		//delay lyrics display by time
		Thread.sleep(timeList.get(0));
		System.out.println(lrcMap.get(timeList.get(0)));
		
		for(int i = 1; i < timeList.size() - 1; i++) {
			Thread.sleep(timeList.get(i) - timeList.get(i - 1));
			System.out.println(lrcMap.get(timeList.get(i)));
		}
		
	}
}
