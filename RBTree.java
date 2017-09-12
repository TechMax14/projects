import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class RBTree<Key extends Comparable<Key>,Value extends Comparable<Value>> {

    private Node root;     // root of the BST

    // BST helper node data type
    private class Node {
        private Key key;           // key
        private Value val;         // associated data
        private Node left, right;  // links to left and right subtrees

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }

    }



    public boolean isEmpty() {
        return root == null;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return get(root, key);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Value get(Node x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;
        }
       
        return null;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        root = deleteMin(root);
    }

    // delete the key-value pair with the minimum key rooted at h
    private Node deleteMin(Node h) { 
        if (h.left == null)
            return h.right;

        h.left = deleteMin(h.left);
        return h;
    }


    public void delete(Key key) { 
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;
        root = delete(root, key);
    }

    private Node delete(Node h, Key key) { 
        // assert get(h, key) != null;

        if (key.compareTo(h.key) < 0)  {
            h.left = delete(h.left, key);
        }
        else if (key.compareTo(h.key) < 0)  {
        	h.right = delete(h.right, key);
        }
        else {
        	if (h.right == null)
				return h.left;
        	if (h.left == null)
				return h.right;
        	
        	
                Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                h.right = deleteMin(h.right);
            
            
        }
        return h;
    }

    private Node rotateRight(Node h) {
        // assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        return x;
    }

    private Node rotateLeft(Node h) {
        // assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        return x;
    }





    public int height() {
        return height(root);
    }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
        return min(root).key;
    } 

    private Node min(Node x) { 
        // assert x != null;
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 


    
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }

        root = put(root, key, val);
    }


	private Node put(Node h, Key key, Value val) { 
        if (h == null) return new Node(key, val);

        int cmp = key.compareTo(h.key);
        if      (cmp < 0) h.left  = put(h.left,  key, val); 
        else if (cmp > 0) h.right = put(h.right, key, val); 
        else              h.val   = val;


        return h;
    }
	
	public boolean greaterThan(Node node1, Node node2) {
		return node1.val.compareTo(node2.val) > 0;  // if left child greater than parent = true		
	}


	private Node optimize(Node node) {
		
		if(node.left != null) optimize(node.left);
		if(node.right != null) optimize(node.right);

		if ((node.left==null||node.right==null)||greaterThan(node, node.left) && greaterThan(node, node.right)) return node;
		
		if(greaterThan(node.left, node.right)) {
				rotateRight(node);
				optimize(node.right);
		}
		else if(greaterThan(node.right, node.left)) {
				rotateLeft(node);
				optimize(node.left);
		}		
		return node;
	}

	private Node findWord(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return findWord(root, key);
		
	}

	private Node findWord(Node node, Key key) {
		if(node != null){
	        if(node.key.equals(key)){
	           return node;
	        } 
	        int comp = node.key.compareTo(key);
			if(comp > 0) 
	        	return findWord(node.left, key);
	        else {
	            return findWord(node.right, key);
	        }
	         
	    } else {
	        return null;
	    }
	}

    public static void main(String[] args) throws IOException { 
    	System.out.println("Enter 'done' to finsih search.\n");
    	RBTree.run();
       
    }
    
    public static void run() throws FileNotFoundException {
    	RBTree<String, Integer> st = new RBTree<String, Integer>();
    	File fileReader =  new File("/Users/Max/Documents/Com Sci/Dictionary Search/src/Words.txt"); // call file
		Scanner sc = new Scanner(fileReader); // create bufferedReader
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter a word to search for: ");
		String wordSearch = kb.next();
		if(wordSearch.compareTo("done") == 0) {
			System.out.println("Terminated");
			System.exit(0);  
		}
        while(sc.hasNext())
        {
        	String[] read = sc.nextLine().split("\\s+");
        	st.put(read[0], Integer.parseInt(read[1]));
        }       
       st.optimize(st.root);
       RBTree.Node n = st.findWord(wordSearch);
       if(n == null) { System.out.println("Word not found\n"); RBTree.run(); }
       System.out.println("Freq: " +  n.val + "\n");
       RBTree.run();
       sc.close(); kb.close();
    }

}



