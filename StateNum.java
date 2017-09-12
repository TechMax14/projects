public class StateNum { 
	// sets key and value for the hash map which uses K,V
	// allows the scores to sort and keeps the names associated with them 
    private String key;
    private Double value;
    
    public StateNum(String key, Double value) {
        this.key = key; this.value = value;
    }

    public Double getValue() {
    	return this.value; 
    	}

    public String getKey() {
    	return this.key; 
    }

    public int compareTo(StateNum other) {    
	  return (int) (other.value - this.value);
    }

}

