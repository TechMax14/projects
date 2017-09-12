import java.util.*;

/* 
 * Max Fishman
 * July 24, 2016
 * Assignment 4 - Railway Car Sorting
 * Dr. Simon
 * 
 * Rules: In a stack of train cars that each have a random letter assigned
 * we must use stack heavily to get all the cars to the assembly line. Carts can 
 * move to from entrance to assembly area. Entrance to storage area. Last car can move 
 * storage to assembly. Last car can move from assembly to storage. All cars must be in
 * the assembly line in the end of the program in alphabetical order. Like Hanoi Tower.
 * Cars can only be compared to the neighbor car, can't wait for next car to decide to push 
 * or pop. Ex: if C shows up move it to assembly. If b shows up move C to storage. Move B to
 * assembly. Move C back to assembly. 
 * 
 */
public class CarAssemble {
	static Scanner kb = new Scanner(System.in);
	public static void main(String[] args) {
		Stack<String> assembly = new Stack<String>(); // create assembly line stack
		Stack<String> storage = new Stack<String>(); // create storage line stack
		int i = 0;
		String carName;
		System.out.println("-You are hired as the new regional railway car manager at Union Station-");
		System.out.println("Enter the letters of each train car you expect to be arriving --->"); // enter 9 to match assignment example
		carName = kb.nextLine();  	// set car names to scanner input
		String[] car = new String[26]; 	// create array to hold all car names
		for(i = 0; i < car.length; i++) {
			car = carName.split("\\s+"); // splits the carName string into the car array by white spaces
		}
		Runner.run(car, assembly, storage); // call run method passing what's needed. 
	}	
}
			
