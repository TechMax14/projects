import java.util.Comparator; // used to compare scores for the priority queue

class NameComparator<S extends StateNum> implements Comparator<S> { 

	public int compare(S c1, S c2) { // assign it variables to compare
		return c1.compareTo(c2);
	}
}
