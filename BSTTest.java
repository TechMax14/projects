public class BSTTest<Key extends Comparable<Key>, Value extends Comparable<Value>> {
	public Node root;

	public class Node {
		public Key key;
		public Value val;
		Node left, right;

		Node(Key key, Value val) {
			this.key = key;
			this.val = val;
		}

		public String toString() {
			return key + " " + val + " ";
		}
	}

	public void delete(Key key) {
		root = delete(root, key);
	}

	private Node delete(Node x, Key key) {
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			x.left = delete(x.left, key);
		else if (cmp > 0)
			x.right = delete(x.right, key);
		else {
			if (x.right == null)
				return x.left;
			if (x.left == null)
				return x.right;
			Node t = x;
			x = min(t.right);
			x.right = deleteMin(t.right);
			x.left = t.left;
		}
		return x;
	}

	private Node deleteMin(Node x) {
		if (x.left == null)
			return x.right;
		x.left = deleteMin(x.left);
		return x;
	}

	private Node min(Node x) {
		return x.left == null ? x : min(x.left);
	}

	public Key min() {
		return min(root).key;
	}

	public void deleteMin() {
		root = deleteMin(root);
	}

	public void put(Key key, Value val) {
		if (val == null) {
			delete(key);
			return;
		}
		root = put(root, key, val);
	}
	
	public Node put(Node x, Key key, Value val) {

		if (x == null) {
			return new Node(key, val);
		}
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			x.left = put(x.left, key, val);
		else if (cmp > 0)
			x.right = put(x.right, key, val);
		else
			x.val = val;
		// x.N = 1 + size(x.left) + size(x.right);
		return x;
	}

	public void show() {
		show(root, 0);
		System.out.println();
	}

	public void show(Node n, int i) {
		if (n != null) {
			show(n.right, i + 1);
			for (int j = 0; j < i; j++)
				System.out.print(" ");
			System.out.println(n);
			show(n.left, i + 1);
		}
	}
	public void DFS(){
		root=DFS(root);
	}
	public Node DFS(Node root) {

		if (root == null)
			return null;
		root.left = DFS(root.left);
		root.right = DFS(root.right);
		if (checkproperty(root)) {
			if (root.left != null && root.right != null) {
				if ((root.left.val.compareTo(root.right.val) > 0)) {
		
					Node x =testRotateRight(root);
					x.right =DFS(x.right);
					return x;

				} else {

					root=testRotateLeft(root);
					root.left=DFS(root.left);
					//root=x;
					return root;

				}
			} else if (root.left != null) {

				Node x = rotateRight(root);
				DFS(x.right);
				return x;

			} else {

				Node x = rotateLeft(root);
				
				DFS(x.right);
				return x;

			}
		}
		return root;
	}

public Node testRotateLeft(Node p){
	Node x = p.right;
	p.right=p.right.left;
	x.left=p;
	return x;
}
public Node testRotateRight(Node p){
	Node x = p.left;
	p.left=p.left.right;
	x.right=p;
	return p;
	
}
	

	public boolean checkproperty(Node root) {
		// System.out.println("root > " + root);

		if (root.left != null && (root.left.val.compareTo(root.val) > 0)) {
			return true;
		} else if (root.right != null && (root.right.val.compareTo(root.val) > 0)) {
			return true;
		} else
			return false;

	}

	public Node rotateLeft(Node root) {
		Node x = root.right;
		root.right = x.left;
		x.left = root;
		return x;
	}

	public Node rotateRight(Node root) {
		Node x = root.left;
		root.left = x.right;
		x.right = root;
		return x;
	}

	public Value search(Key key) {
		Node x = root;
		int count = 0;
		while (x != null) {
			int cmp = key.compareTo(x.key);
			if (cmp == 0) {
				System.out.println("It made > " + count + " comparisons in BST to find a word > "+ key);
				return x.val;
			} else if (cmp < 0) {
				count++;
				x = x.left;
			} else if (cmp > 0) {
				count++;
				x = x.right;
			}
			// System.out.println("Number of comparisons is > "+count);
		}
		// System.out.println("Number of comparisons is > "+count);
		return null;
	}

}
