import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/*
 * Max Fishman
 * July 31, 2016
 * Dr. Simon
 * Assignment 6 - Miss America Database
 */


public class BeautyPag {
	public static void main(String args[]) throws NumberFormatException, IOException {		
		HashMap<String, Double> map = new HashMap<String, Double>(); // create hash map for state name string and double for score
		Comparator<StateNum> comaprator = new NameComparator<StateNum>(); // comparator for Queue to sort names
		PriorityQueue<StateNum> pq = new PriorityQueue<StateNum>(10, comaprator); // create Priority Queue for top ten
		DecimalFormat df = new DecimalFormat("#.00"); // formats the output (https://docs.oracle.com/javase/tutorial/i18n/format/decimalFormat.html)
		String line = null;
	    FileReader fileReader =  new FileReader("/Users/Max/Documents/Com Sci/Ass 6/src/pageant.txt"); // call file
		BufferedReader br = new BufferedReader(fileReader); // create bufferedReader
		while((line = br.readLine()) != null) { // while their is something to read in
			String[] recordData = line.split(",");  // create record data array that splits the file lines by commas
		    double score = 0; 
		    String b = recordData[2];
		    if (b.equals("P")) // if the category equals P
		    	score += .25 * Integer.parseInt(recordData[3]);	// score increments by data array object and multiplies it by its weighted amount	    
		    else if (b.equals("T")) // repeat for all 5 categories
		    	score += .35 * Integer.parseInt(recordData[3]);
		    else if (b.equals("L"))
		    	score += .15 * Integer.parseInt(recordData[3]);
		    else if (b.equals("E"))
		    	score += .20 * Integer.parseInt(recordData[3]);
		    else if (b.equals("O"))
		    	score += .5 * Integer.parseInt(recordData[3]);
		       
		    if (map.get(recordData[1]) == null) // if map has no data yet 
		    	map.put(recordData[1], score);  // put a new record data in the map
		    else 
		    	map.put(recordData[1], map.get(recordData[1]) + score); // else keep adding the scores up
		      
		}
		br.close();
		 for (String name: map.keySet()) {   // loop that prints out hash map (http://stackoverflow.com/questions/5920135/printing-hashmap-in-java)
            StateNum st = new StateNum(name, map.get(name)); // returns and compares the key and value for this program
            pq.add(st); // add the necessary data objects to the queue (state and score)           
            //String key =name.toString();
	        //String value = map.get(name).toString(); // prints out the score for all 50 ladies
	        //System.out.println(key + " " + value);   // but i can't get the format for it to look nice so I took it out
	     }
		 //System.out.println("________________________________________");
		 StateNum x; 
		 int count = 0; 
		 System.out.println("The best scores have been generated!\n"
		 		+ "Top Ten Finalist:");
		 while ((x = pq.poll()) != null && count < 10 ) { // while the queue has something on top to remove and contestants are less than 10
			 System.out.println((count+1) + ".) " +x.getKey() + " got [" + df.format(x.getValue()) + "]" ); // print out the state and score for the top 10 
			 count++; 
		 }
	}
}


