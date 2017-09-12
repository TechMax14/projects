/*
 * Max Fishman
 * Prof. Drozdek
 * 4/20/2017
 * Hamming Error Detection
 */
import java.io.*;
import java.util.*;

public class HamTest { 
	private static File file;
	private static FileInputStream fist1, fist2; 	// file input stream variables
	private static FileOutputStream fost1, fost2;  // file output stream variables
	private static int count;
	private static String biConvert, cw1, cw2;
	
	public static void main(String[] args) throws IOException {
		Scanner kb = new Scanner(System.in);
		System.out.print("Enter data to en/de code: ");
		try(  PrintWriter out = new PrintWriter( "data.txt" )  ){ // allows user to enter data directly into program
		    out.println(kb.next());								  // instead of interacting with a pre-made .txt file
		}
		file = new File("data.txt");
		encoding();		// main methods for encoding and decoding
		decoding();
	}

	private static void encoding() throws IOException {
		int sym = 0;
		int[] data;
		fist1 = new FileInputStream(file);  // stream in data in decimal notation from data.txt
		data = new int[fist1.available()];  // build array of available characters from file
		fost1 = new FileOutputStream("encode.txt"); // create output stream variable 
		System.out.println(">>>>>>>>>>>>>>>[ENCODING]>>>>>>>>>>>>>>>");
		while((sym = fist1.read()) > 10) {  // while data.txt has meaningful characters
			biConvert = Integer.toBinaryString(sym);	// convert each character into a binary string
			System.out.println("Character integer value: " + sym);
			while(biConvert.length() < 8) {	// if bit string is not 8 bits add 0's to the front until its 8 bits
				biConvert = "0" + biConvert;
			}
			System.out.println("8-Bit Binary Conversion: " + biConvert);
			cw1 = biConvert.substring(0, 4);	// separate the 8-bit string into 2 code words of 4-bits
			cw2 = biConvert.substring(4, 8);
			System.out.println("Sub-Code Word 1: " + cw1);
			System.out.println("Sub-Code Word 2: " + cw2);
			String[] cws = {codeWords(cw1), codeWords(cw2)}; // call methods to get official code words with respected parity bits added
			System.out.println("Code Words: " + Arrays.toString(cws)); // print out official code words
			byte decConvert[] = {Byte.parseByte(cws[0], 2), Byte.parseByte(cws[1], 2)}; // converts code word decimals into base 2 byte strings
			System.out.println("In decimals: " + Arrays.toString(decConvert));
			fost1.write(decConvert);	// writes encoded data to output steam encode.txt
			System.out.println("****************************************");
		}
		File file = new File("encode.txt");		// retrieve encoded text
		Scanner sc = new Scanner(file);
		System.out.println("$$$$$$$$$$$$$$$$$$$");
		System.out.print("Encoded text: ");
		while(sc.hasNext()) {
			System.out.println(sc.nextLine()); // print out the encoded data
		}
		System.out.println("$$$$$$$$$$$$$$$$$$$");
	}
	
	public static String codeWords(String cw) {
		int G[][] = {{0,1,1,1,0,0,0},   // 4x7 generator matrix (see bottom for resource)
				 	{1,0,1,0,1,0,0},
				 	{1,1,0,0,0,1,0},
				 	{1,1,1,0,0,0,1}};
		int dBits[] = new int[4];
		for(int i=0; i<4; i++)
			dBits[i] = Character.getNumericValue(cw.charAt(i)); // build an array for the 4 data bits to multiply against the matrix
		String cws = "";
		for(int i=0; i<7; i++){
			int sum = 0;
			for(int j=0; j<4; j++){
				sum += (dBits[j] * G[j][i]); // increment through the two matrices and multiply 
			}
			cws += (sum % 2);  // results in a 7-bit string (p1,p2,p3,d1,d2,d3,d4) in mod 2
		}
		return cws;
	}

	private static void decoding() throws IOException {
		int sym = 0;
		count = 0;
		String[] data;
		try {
			fist2 = new FileInputStream("encode.txt");  // try grabbing data in encode.txt and assign to fist2
			data = new String[fist2.available()];		// create array of encoded text
			fost2 = new FileOutputStream("decoded.txt"); // create output to decoded.txt variable
			System.out.println("<<<<<<<<<<<<<<<[DECODING]<<<<<<<<<<<<<<<");
			while((sym = fist2.read()) != -1) {  // while their are characters available in encode.txt
				biConvert = Integer.toBinaryString(sym); 	// convert each symbol/character to binary
				while(biConvert.length() < 7) {
					biConvert = "0" + biConvert;	// if byte string is not 7-bits, add 0's to beginning
				}
				data[count] = biConvert;  // convert each character in string to binary
				count ++;
			}
			for(int i=0; i<data.length; i+=2){  // for each data bit 
				System.out.println("|Code Words|\nFirst: " + data[i]); // print out each code word
				System.out.println("Second: " + data[i+1]);
				byte decodeIt = Byte.parseByte((parityGet(data[i]) + parityGet(data[i+1])) , 2); // and parse each of the 4 data values into bytes (base 2)
				System.out.println("Decoded Value: " + decodeIt + "\n________________________________________");
				fost2.write(decodeIt); // write back to output stream with data decoded
			}
		}
		catch(Exception ex) {
			ex.printStackTrace(); // catch error exceptions in previous try method
		}
		File file = new File("decoded.txt");  // retrieve final text in decoded.txt
		Scanner sc = new Scanner(file);
		System.out.println("$$$$$$$$$$$$$$$$$$$");
		System.out.print("Decoded text: ");
		while(sc.hasNext()) {
			System.out.println(sc.nextLine());	// print out final decoded code
		}
		System.out.println("$$$$$$$$$$$$$$$$$$$");
		fist2.close(); // close input stream from encode.txt
	}

	private static String parityGet(String data) {
		Random rand = new Random();
		int H[][] = { {1,0,0,0,1,1,1},	 // 3x7 parity check matrix (same source)
				      {0,1,0,1,0,1,1},
				      {0,0,1,1,1,0,1}};
		int curCW[] = new int[7];
		for(int i=0; i<7; i++)
			curCW[i] = Character.getNumericValue(data.charAt(i));	// create 7 integer array from data bits
		System.out.println("Current Code Word: " + Arrays.toString(curCW));
		
		// MANUAL CORRUPTION CODE
		//------------------------------------------------------------------
		int tmp = rand.nextInt(7);											// create random array index for error
		System.out.println("Error added in slot " + (tmp+1));
		if(curCW[tmp] == 0) curCW[tmp] = 1;									// if array index in code word is 0 change it to 1
		else curCW[tmp] = 0;												// or vice versa
		System.out.println("Corrupted Code Word: " + Arrays.toString(curCW));
		//------------------------------------------------------------------
		
		int[] parity = new int[3]; 		// build array for the 3 parity bits
		int sum;
		for(int i=0; i<3; i++) {
			sum = 0;
			for(int j=0; j<7; j++)			// multiply the parity check matrix with the current code word matrix (7x1)
				sum += (H[i][j]*curCW[j]);
			parity[i] = (sum % 2);			// for each of the 3 parities mod it by 2 and add it to the array
		}	
		System.out.println("Parity Status: " + Arrays.toString(parity));
		
		if((parity[0] + parity[1] + parity[2]) != 0){  // if all 3 parity bits are not 0
			fixParity(parity, H, curCW);			   // then there is an error in the code word and the parity must be fixed
			System.out.println("Fixed Code Word: " + Arrays.toString(curCW));
		}
		return ("" + curCW[3] + curCW[4] + curCW[5] + curCW[6]);   // return 4 data bit values
	}

	private static int[] fixParity(int[] parCheck, int[][] H, int[] curCW) {
		int error = 0;
		for(int i=0; i<7; i++) {
			count = 0;
			for(int j=0; j<3; j++) {			// increment through the 3x7 parity check matrix
				if(parCheck[j] == H[j][i])      // if the parity column matches the parity check matrix column then add to the counter
					count++;
				if(count == 3) {				// if count reaches 3 then we found our error position
					error = i;
				}
			}			
		}
		System.out.println("Error Detectect at: " + (error+1));
		if(curCW[error] == 0) curCW[error] = 1; // once error position is detected, if the value of the error is 0 change it to a 1
		else curCW[error] = 0;					// else if error value is 1 change it to a 0
		
		return curCW;		// return the current and official code word
	}
	
}

// Kevin pointed me in the direction of this source (http://michael.dipperstein.com/hamming/#example2)
// really helpful breakdown and explanation of what the hamming code is and how it's implemented
