import java.util.Stack;

public class Runner {
	
	String[] car;
	Stack<String> assembly;
	Stack<String> storage;
	public Runner(String[] car, Stack<String> assembly, Stack<String> storage) { // runner conrstructor 
		this.car = car; this.assembly = assembly; this.storage = storage;
	}
	
	public static void run(String[] car, Stack<String> assembly, Stack<String> storage) { // n = car
		for(int i = 0; i < car.length; i++) {	// main loop checking each car in queue list
			System.out.println("Car " + car[i] + " appears in the yard.");
			if (assembly.isEmpty() && storage.isEmpty()) { // base case, if both stack lines are empty
				assembly.push(car[i]); // add first car in queue to assembly stack 
				System.out.println("Move Car " + car[i] + " to the assembly line.");
			}
			else {
				if ((car[i].compareTo(assembly.peek())) > 0) { // String 2 comes first. If new car follows what's in the assembly
					assembly.push(car[i]); // add it to assembly stack 
					System.out.println("Move Car " + (car[i]) + " to the back of the assembly line.");
				}
				else { // String 1 comes first. If next car up does not fall into correct order												
					while(((car[i].compareTo(assembly.peek())) < 0)) { // while that order is incorrect in the assembly line
						storage.push(assembly.peek()); // push the assembly car on top of the stack to the back of the storage line
						assembly.pop(); // and pop that car moved to storage off the assembly line
						System.out.println("Move Car " + storage.peek() + " to the storage line.");
						if (assembly.isEmpty()) // if comparison continues to end of assembly line break out the loop
							break;
					}
					assembly.push(car[i]); // once the needed cars are stored in storage, add the new car to the assembly 
					System.out.println("Move Car " + car[i] + " to the assembly line.");
					while (!(storage.isEmpty())) { // while the storage has cars in line still
						assembly.push(storage.peek()); // add all those cars back to the assembly line
						System.out.println("Move Car " + storage.peek() + " back to the assembly line.");
						storage.pop(); // and lastly pop the storage line empty
					}
					
				}
			}
		}
	System.out.println("Assembly line is in alphabetical order:" + assembly);		
	}
}
