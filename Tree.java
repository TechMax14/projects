/*
 * Max Fishman
 * July 30, 2016
 * Dr.Simon
 * Assignment 5 - Trees, Recursion 
 * 
 * Only help I received was for the diameter method thanks to stackoverflow.com and the numBetween method. 
 * Devon gave me pointers on each case we needed for numBetween and also told me I can just add 1 to a recursive 
 * method in the return statement. Once I ran into some run time errors Chris and Bois helped me debug partially.
 * Turns out my main issue with this method was that I kept getting 6 from A to V and expected 7. But it wsn't until
 * a day or two later I pieced together that I deleted V from the tree. Jokes on me. 
 */
public class Tree<K extends Comparable<K>,D> {

    private Node<K,D> root; // create private Node object root
    
    public Tree() { root = null; } // constructor setting root to 0

    public Tree(K[] keys, D[] data) {
	root = buildTree(keys, data, 0, keys.length-1); // set root to what buildTree returns
    }

    private Node<K,D> buildTree(K[] keys, D[] data, int lo, int hi) {  // lo is set to 0, hi is key array length-1
	if(lo > hi) // if array list runs out return nothing
	    return null;
	int m = (hi - lo) / 2 + lo; // create a media and assign it to keys and data
	return new Node<K, D>(keys[m], data[m], // returns median of k and d
			buildTree(keys, data, lo, m-1),	// runs method again for low to middle-1
			buildTree(keys, data, m+1, hi)); // runs method again for mid+1 to hi
    }

    private D find(K key, Node<K,D> x) { // find method from class
	if(x == null)
	    return null;
	int c = key.compareTo(x.key); 
	if(c == 0)
	    return x.data;
	else if(c < 0) 
	    return find(key, x.left);
	else // c > 0
	    return find(key, x.right);
    } 

    public D find(K key) {
	return find(key, root);
    }

    private Node<K,D> add(K key, D data, Node<K,D> root) { // returns the tree with the added record.
	if(root == null) // if nothing is at the root when an item is added
	    return new Node<K,D>(key,data);		// make new k and d the root
	int c = key.compareTo(root.key);  // compare new key with the root 
	if(c == 0) {
	    System.err.println("Error: duplicate key: "+key); // if same key print error return null
	    System.exit(1);
	    return null;
	}
	else if(c < 0) {  //  if new key is less then the root 
	    root.left =  add(key, data, root.left); // make it to the left of the root
	    return root;
	}
	else { // c > 0
	    root.right = add(key, data, root.right); // if greater than root make it right leaf
	    return root;
	}
    }

    public void add(K key, D data) { // add method from class
	root = add(key, data, root);
    }
    
    public String reverse(Node<K, D> root) { // reverse string method
    	if(root == null) {
    	    return "";
    	}
    	return reverse(root.right) // taken directly from toString method
    			+ "(" + root.key + ","  + root.data + ")" 
    			+ reverse(root.left); // but reversed the order of print
	}
    
    public String reverse() { // calls method but may be unneeded   
    	return reverse(root);
        }
    
    
    public K ceiling(K key, Node<K,D> root) { // ceiling method
    	if(root == null) // base case
    	    return null;
    	int c = key.compareTo(root.key); 
    	if(c == 0) // if keys ceiling equals the root 
    	    return root.key; // return that root key
    	else if(c < 0) {// if roof key is higher than key
    		K f = ceiling(key, root.left); // we must recurse through the left
    		if(f == null) // until the key equals the new root then just return that root
    			return root.key; 
    	    return f; // else keep recursing 
    	}
    	else // if key is greater than the root recurse right continuously
    		return ceiling(key, root.right); 
        }
    
    public K ceiling(K key) { // calls ceiling method
    	return ceiling(key, root);
        }
    
    public int diameter(Node<K, D> root) { // stackoverflow.com "Diameter of BST" gave me the idea and concept behind mainly the Math.max implementation
    	if (root != null) {
    		int rootDiameter = getDiameter(root.left) + getDiameter(root.right) + 1; // sets int to the diameter of both the left and right subtrees using getDiameter method
    		int leftDiameter = diameter(root.left); // sets int to diameter of the left of the tree
    		int rightDiameter = diameter(root.right); // sets in to diameter of the right of the tree
    		return Math.max(rootDiameter, Math.max(leftDiameter, rightDiameter)); // returns max diameter of either the root, left side, or right side
    	}
    	return 0; // base case
    }

    public int getDiameter(Node<K, D> root) { // used by diameter method obviously 
    	if (root != null)
			return Math.max(getDiameter(root.left), getDiameter(root.right)) + 1; // returns max diameter of either left or right side of the tree + 1
    	return 0; // base case
    }
    
   public int numBetween(K key1, K key2, Node<K,D> root) { 	// gets number of keys between 2 other keys
 	   int c, d; 
	   if (root != null){
		   c = key1.compareTo(root.key); // compare key1 to the root
		   d = key2.compareTo(root.key); // compare key2 to the root
		   if(c < 0 && d > 0) // if key1 is less than root and key2 is greater than
			   return 1 + numBetween(key1, key2, root.left) + numBetween(key1, key2, root.right); // add 1 and recurse to both left and right sides
		   else if (c < 0 && d < 0) // if both keys are to the left just recurse to the left to find the roots 
			   return numBetween(key1, key2, root.left);
		   else if (c > 0 && d > 0) // if both keys are to the right just recurse to the left to find the roots 
			   return numBetween(key1, key2, root.right);
		   else 
			   return 1; // if both keys found a root home return 1
 	   }
 	   return 0; // base case
    } 
   
   public int numBetween(K key1, K key2) { // calls the numBetween method
	   return numBetween(key1, key2, root);
   }
    
   private void modify(K key, D data, Node<K,D> root) { // modify method from class
	if(root == null) {
	    System.err.println("Error: key not found: "+key);
	    System.exit(1);
	}
	int c = key.compareTo(root.key);
	if(c == 0)
	    root.data = data;
	else if(c < 0) 
	    modify(key, data, root.left);
	else // c > 0
	    modify(key, data, root.right);
    }

    public void modify(K key, D data) { // calls modify
	modify(key, data, root);
    }

    private String toString(Node<K,D> root) { // toString method
	if(root == null)
	    return "";
	return toString(root.left) 
	    + "(" + root.key + "," 
	    + root.data + ")" 
	    + toString(root.right);
    }

    public String toString() { // calls method
	return toString(root);
    }

    private Node<K,D> findLeftmost(Node<K,D> root) { // from class
	// Assumes root != null.
	return root.left == null 
	    ? root
	    : findLeftmost(root.left);
    }

    private Node<K,D> removeLeftmost(Node<K,D> root) { // from class
	if(root.left == null) 
	    return root.right;
	else {
	    root.left = removeLeftmost(root.left);
	    return root;
	}
    }

    private Node<K,D> delete(K key, Node<K,D> root) { // from class
	if(root == null) {
	    System.err.println("Error: key not found");
	    System.exit(1);
	    return null;
	}
	int c = key.compareTo(root.key);
	if(c == 0) {
	    if(root.left == null)
		return root.right;
	    else if(root.right == null)
		return root.left;
	    else {
		Node<K,D> t = root;
		root = findLeftmost(root.right);
		root.right = removeLeftmost(t.right);
		root.left = t.left;
		return root;
	    }
	}
	else if(c < 0) {
	    root.left = delete(key, root.left);
	    return root;
	}
	else { // c > 0
	    root.right = delete(key, root.right);
	    return root;
	}
    }

    public void delete(K key) { // from class
	root = delete(key, root);
    }

    public static void main(String[] args) { // the output will explain each of these visually
    	Character[] keys = { 'A', 'G', 'K', 'P', 'R', 'S', 'V' };
    	String[] data = { "Atlanta", "Georgia", "Kansas City", "Philadelphia", "Reno", "Seattle", "Vancouver" }; // stuck with the state name example
    	Tree<Character, String> tree = new Tree<Character, String>(keys, data); // creates tree
    	System.out.println("Tree in use: " + tree);
    	tree.delete('P');
    	tree.delete('V');
    	tree.add('P', "Pittsburgh");
    	tree.modify('A', "Austin");
    	System.out.println("\nRemove & Modify Test: ");
    	System.out.println(tree);
    	System.out.println("\nReverse Test: ");
    	System.out.println(tree.reverse(tree.root));
    	System.out.println("\nCeiling Test: ");
    	System.out.println("B ceiling = " + tree.ceiling('B'));
    	System.out.println("S ceiling = " + tree.ceiling('S'));
    	System.out.println("Y ceiling = " + tree.ceiling('Y'));
    	System.out.println("\nDiameter Test: ");
    	System.out.println("The diameter for the tree in this order 'A G K P R S V' is [" + tree.diameter(tree.root) + "].");
    	System.out.println("\nSearch (find) Test: ");
    	System.out.println("Search for A: " + tree.find('A'));
    	System.out.println("Search for D: " + tree.find('D'));
    	System.out.println("Search for P: " + tree.find('P'));
    	System.out.println("\nTree Range Test: ");
    	System.out.println("Number of keys between A and V = " + tree.numBetween('A','V'));
    	System.out.println("Number of keys between A and Z = " + tree.numBetween('A','Z'));
    	System.out.println("Number of keys between B and Q = " + tree.numBetween('B','Q'));
    	System.out.println("Number of keys between H and J = " + tree.numBetween('H','J'));
    	System.out.println("Keep in mind the list has deleted one key.");
    }
}