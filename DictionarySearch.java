import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
 * Max Fishman
 * Prof. Simon
 * RBTree Dictionary Database
 * 
 * Note: I attempted to use Sedgewicks RBT but his code is too advanced for what I was trying
 * to do. Too many methods he had that were not doing any good for the program. So, I de-RedBlacked
 * the tree, if you will, and used a regular BST using most of his same code. Secondly, I attempted to create 
 * an optimization method with Dan that efficiently optimizes the tree based on frequency using tree rotation but 
 * after 2 weeks I could not implement an effective optimization by rotation code. I also sat down with Quan a 
 * few times and he explained to me how he approached the problem in a completely different way than 
 * I was and talked me through some of the process. So I kept the code I was attempting in hope 
 * for some feedback from you on how I could change it to use rotation properly. But I ultimately
 * ended up re-routing my program and writing it in a way that will produce some results.
 */

public class DictionarySearch<Key extends Comparable<Key>,Value extends Comparable<Value>> {	// Sedgewick's BST modified
	
	// create universals to use in several methods and only initialize once
    public static int wordCompCount = 0;
    public static long FREQxCOMPcount = 0;
    public static LinkedList<databaseInfo> database = new LinkedList<databaseInfo>();
	
    private Node root;     // root of the BST

    private class Node {
        private Key key;           // key
        private Value val;         // associated data
        private Node left, right;  // links to left and right subtrees

        public Node(Key key, Value val) {	// Node constructor 
            this.key = key;
            this.val = val;
        }
        
        public String toString(){
        	return key.toString();
        }
    }
   
    public static class frequency implements Comparator<databaseInfo>{		// frequency Comparator 
    	public int compare(databaseInfo freq1, databaseInfo freq2) {		// to sort databaseInfo
        	if(freq1.getFreq() < freq2.getFreq())
        		return 1;
        	return -1;
    	}
    }
    // Sedgewicks get() method
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");	// if key empty return exception
        return get(root, key);		// else call get()
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Value get(Node x, Key key) {
    	wordCompCount = 0;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            wordCompCount++; FREQxCOMPcount++;
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;		// return value of get node
        }
        return null;
    }

    public boolean contains(Key key) {		// sedgewicks
        return get(key) != null;			// if get method returns not null BST contains key
    }

    public void delete(Key key) { 			// // sedgewicks
        if (key == null) throw new IllegalArgumentException("argument to delete() is null"); // if key null return exception
        if (!contains(key)) return;			// if BST does not contain key
        root = delete(root, key);			// call delete starting at root
    }

    private Node delete(Node n, Key key) { 		// sedgewick's delete method
        if (key.compareTo(n.key) < 0)  {
            n.left = delete(n.left, key);	
        }
        else if (key.compareTo(n.key) < 0)  {
        	n.right = delete(n.right, key);	
        }
        else {
        	if (n.right == null) return n.left;
        	if (n.left == null) return n.right;
        }
        return n;
    }

    public boolean isEmpty() {		// sedgewicks code
        return root == null;
    }
    public Key min() {				// sedgewicks code
        if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
        return min(root).key;
    } 

    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node x) { 		// sedgewicks code
        // assert x != null;
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 

    public Key max() {				// sedgewicks code
        if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
        return max(root).key;
    } 

    // the largest key in the subtree rooted at x; null if no such key
    private Node max(Node x) { 		// sedgewicks code
        // assert x != null;
        if (x.right == null) return x; 
        else                 return max(x.right); 
    } 
    public Iterable<Key> keys() {		// sedgewicks code
        if (isEmpty()) return new Queue<Key>();
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {		// sedgewicks code
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    } 
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) { 	// sedgewicks code
        if (x == null) return; 
        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key); 
        if (cmplo < 0) keys(x.left, queue, lo, hi); 
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key); 
        if (cmphi > 0) keys(x.right, queue, lo, hi); 
    }
    
    public static void optimize() throws FileNotFoundException {	// optimize method
    	File fileReader =  new File("/Users/Max/Documents/Com Sci/Dictionary Search/Words.txt"); // call file
		Scanner sc = new Scanner(fileReader); 
		do {
			String[] read = sc.nextLine().split("\\s+");
        	database.add(new databaseInfo(read[0], Integer.parseInt(read[1])));		// add unoptimized dictionary to linked list
	        Collections.sort(database, new frequency());	// sort linked list by frequency 
		} while(sc.hasNext());
		sc.close();
    }
    
    public void put(Key key, Value val) {		// sedgewicks code
        if (key == null) throw new IllegalArgumentException("first argument to put() is null"); // if key null throw exception
        if (val == null) {		// if val null delete key and return
            delete(key);
            return;
        }
        root = put(root, key, val);			// call put method with root as initial node
    }

	private Node put(Node n, Key key, Value val) { 			// sedgewicks code 
        if (n == null) return new Node(key, val);
        int cmp = key.compareTo(n.key);
        if      (cmp < 0) n.left  = put(n.left,  key, val); 
        else if (cmp > 0) n.right = put(n.right, key, val); 
        else              n.val   = val;
        return n;
    }

    public static void main(String[] args) throws IOException { 	// main method
    	System.out.println("Enter 'done' to finsih search.\n");		// end program instructions
    	DictionarySearch.run();										// call run() so instructions is not repeated in recursion     
    }
    
    public static void run() throws FileNotFoundException {
    	DictionarySearch<String, Integer> ds1 = new DictionarySearch<String, Integer>();	//pre-optimized shortcut
    	DictionarySearch<String, Integer> ds2 = new DictionarySearch<String, Integer>();	//post optimized shortcut
    	File fileReader =  new File("/Users/Max/Documents/Com Sci/Dictionary Search/Words.txt"); // call file
		Scanner sc = new Scanner(fileReader); // create file reading scanner
		while(sc.hasNext()) {		// input dictionary in BST alphabetically 
	        	String[] read = sc.nextLine().split("\\s+");	// read data base line and split it by the space
	        	ds1.put(read[0], Integer.parseInt(read[1])); // call put method with each line variable
	    }      
		System.out.println("Comparison x Frequencies:");
		FREQxCOMPcount = 0;
        for(String str : ds1.keys()) {	// calculate total comparisons by frequency
        	ds1.get(str);					// credit Quan for the advice
        	FREQxCOMPcount += wordCompCount*ds1.get(str);
        }
        long a = FREQxCOMPcount;
        System.out.println(NumberFormat.getNumberInstance(Locale.US).format(FREQxCOMPcount) + " - before optimization");       
        optimize();		// optimize
	    for(databaseInfo d : database){		// once linkedlist is sorted by freq, re-enter into BST
	    	String word = d.getWord();
        	Integer freq = d.getFreq();
        	ds2.put(word, freq);			// get words and frequencies through databaseInfo and put in BST
	    }
	    
	    FREQxCOMPcount = 0;
        for(String s : ds2.keys()) {	// repeat total comparison for after optimizing
        	ds2.get(s);
        	FREQxCOMPcount += wordCompCount * ds2.get(s);
        }
        long b = FREQxCOMPcount;
        System.out.println(NumberFormat.getNumberInstance(Locale.US).format(FREQxCOMPcount) + " - after optimization \n");
	    
        System.out.println("Saved comparisons by optimization: " + NumberFormat.getNumberInstance(Locale.US).format(a-b) + "!\n");
        
		Scanner kb = new Scanner(System.in);
		String wordSearch = "";
		while(!wordSearch.equals("done")) {		// if word searching for equals 'done'		
			System.out.println("Enter a word to search for: ");
			wordSearch = kb.next();		// enter word to search and compare comparisons it takes pre/post optimizing
	        ds1.get(wordSearch);
	       	System.out.println(wordCompCount + " comparisons needed before optimizing for (" + wordSearch + ")");
	       	ds2.get(wordSearch);
	       	System.out.println(wordCompCount + " comparisons needed after optimizing for (" + wordSearch + ")\n");
	       //	System.out.println();
		

		}
		System.out.println("Terminated");
		System.exit(0);       
		sc.close(); kb.close();
		
    }
	
	
}

	/* Here I have my initial optimization idea using rotation but couldn't get it to compile
	 * 
	 * private Node optimize(Node node) {
		
		if(node.left != null) node.left=optimize(node.left);
		if(node.right != null) node.right=optimize(node.right);
		
		if(node.left==null&&node.right==null)return node;
		
		
		if(node.left==null && greaterThan(node.right, node)) {
				show();
				Node n = rotateLeft(node);
				show();
				optimize(n.left);
				return n;
		}
		else if(node.right==null && greaterThan(node.left, node)) {
			Node n = rotateRight(node);
			optimize(n.right);
			return n;
		}
		else if(node.right!=null&&node.left!=null)
		{
			if(greaterThan(node.left,node)||greaterThan(node.right, node)){
				if(greaterThan(node.left, node.right)){
					Node n = rotateLeft(node);
					optimize(n.left);
					return n;
				}
				else
				{
					Node n = rotateRight(node);
					optimize(n.right);
					return n;
				}
			}
		}
		return node;
	}
	
	public boolean greaterThan(Node node1, Node node2) {
		return node1.val.compareTo(node2.val) > 0;  	// if node1 (child)  greater than node2 (parent) = true		
	}
	
    private Node rotateRight(Node n) {		// sedgewick's rotate method's
        Node x = n.left;
        n.left = x.right;
        x.right = n;
        return x;
    }

    private Node rotateLeft(Node n) {
        Node x = n.right;
        n.right = x.left;
        x.left = n;
        return x;
    } */



