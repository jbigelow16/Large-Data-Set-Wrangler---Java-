package First;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**	
 * !!!purpose of program is to print ENTIRE HashMap rather than just a set!!!
 * -program uploads two CSV files: 1st has integer keys paired with a value
 * 		& 2nd uploads just the integer keys
 * 	-2nd CSV becomes ArrayList - purpose for facilitation printing
 *	-1st CSV becomes HashMap
 *	-for each key in the ArrayList, program will print key in HashMap with 
 *		respective values while controlling for repeated keys containing different
 *		values
 */

public class data_wrangle {
	public static void main(String[] args) throws IOException {
		Map<String, ArrayList<String>> hmap = null;
		Map<String, ArrayList<String>> jmap = null;
		
		String csvIndex = "Book2.csv";		//CSV - one column = list of integers
		String csvFile = "Book1.csv"; 		//CSV - two columns = integer key #s, values
		String csvNext = "Book3.csv";		//CSV - *optional* two columns = integer key #s, more values
		
/**
 * jmap and csvNext are only necessary if the data set has more than two columns
 * likewise, the program is flexible—user can add more maps i.e. kmap, lmap, etc. for larger data sets
 *  -to expand for larger data sets...
 *  	+ add ***String csvMore = "Book1.csv"***
 *  	+ add ***kmap... ***
 *  	+ add ***deWrangle(csvMore, kmap)***
 */
		
		hmap = new HashMap<String, ArrayList<String>>();
		jmap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> track = new ArrayList<String>();
		ArrayList<String> trend = new ArrayList<String>();
		int HIGHlow [] = new int[2];

		trend = createIDX(csvIndex, track);
		HIGHlow = organizeIDX(trend);
		deWrangle(csvFile, hmap);
		deWrangle(csvNext, jmap);
		
		String intTOstr = null;
		int mini = HIGHlow[0];
		int maxi = HIGHlow[1];
		int idxCounter;
		
		// delete comment tags below to confirm high and low index values
		//System.out.println("First IDX Value: " + mini + " - " + "Final IDX Value: " + maxi);
		
		// iterate over index in ArrayList -> print index along with corresponding value(s)
		for(int bDUM = mini; bDUM < (maxi + 1); bDUM++){
			idxCounter = bDUM;
			intTOstr = Integer.toString(idxCounter);
			System.out.println(idxCounter + ": " + hmap.get(intTOstr) + " : " + jmap.get(intTOstr));
		}
	}
	
	public static ArrayList<String> createIDX(String csvIndex, ArrayList<String> track){
		// read comma separated file line by line
		BufferedReader buffrdrIDX = null;
		String shift = "";		
		try {
			int lineNumber = 0;
			int tokenNumber = 0;
			buffrdrIDX = new BufferedReader(new FileReader(csvIndex));
			StringTokenizer star = null;
			// append eat index as string to ArrayList
			do{
				lineNumber = lineNumber + 1;
				star = new StringTokenizer(shift);
				while (star.hasMoreTokens()){
					tokenNumber = tokenNumber + 1;
					String tokenNext = star.nextToken();
					track.add(tokenNext);
				}
				
			} while ((shift = buffrdrIDX.readLine()) != null);
			
		} catch (IOException e) {
			e.printStackTrace();
			}
		finally {
			if (buffrdrIDX != null) {
				try {
					buffrdrIDX.close();
				} catch (Exception e) {
					System.err.println("Cannot read CSV : " + e);

				}
			}
		}
		ArrayList<String> trend = new ArrayList<String>();
		for(int cDUM = 0; cDUM < track.size(); cDUM++){
			trend.add(track.get(cDUM));
		}
		// "trend" is equal to "track" 
		return trend;
	}
	
	// find min and max index values
	public static int [] organizeIDX(ArrayList<String> trend){
		int length = trend.size();
		int trace [] = new int[length];
		for(int aDUM = 0; aDUM<length; aDUM++){
			String stng = trend.get(aDUM);
			int ingr = Integer.parseInt(stng);
			trace[aDUM] = ingr;
		}
		Arrays.sort(trace);
		int min;
		int max;
		min = trace[0];
		max = trace[length-1];
		int [] HIGHlow = new int [2];
		HIGHlow[0] = min;
		HIGHlow[1] = max;
		return HIGHlow;
	}
		
	// similar to method, "createIDX", but one more dimension since ther are two columns
	public static void deWrangle(String excel, Map<String, ArrayList<String>> mapall){	
		BufferedReader buffrdrWRANG = null;
		String line = "";
		String delim = ",";
		
		try {
			int lineNumber = 0;
			int tokenNumber = 0;
			buffrdrWRANG = new BufferedReader(new FileReader(excel));
			StringTokenizer st = null;
			do {
				lineNumber = lineNumber + 1;

				// use comma as token separator, then use new line as token separator
				st = new StringTokenizer(line, delim);
				
				while (st.hasMoreTokens()) {
					tokenNumber = tokenNumber + 1;
					String token_lhs = st.nextToken();
					
					while (st.hasMoreTokens()) {
						String token_rhs = st.nextToken();
						ArrayList<String> arrVal = mapall.get(token_lhs);
						
						if (arrVal == null) {
							arrVal = new ArrayList<String>();
							mapall.put(token_lhs, arrVal);
						}
						arrVal.add(token_rhs);
					}
				}	
			
			} while ((line = buffrdrWRANG.readLine()) != null);	
		
		} catch (IOException e) {
			e.printStackTrace();	
		
		} finally {
			if (buffrdrWRANG != null) {
				try {
					buffrdrWRANG.close();
				
				} catch (Exception e) {
					System.err.println("Cannot read CSV : " + e);
				}
			}
		}
	}	
}
