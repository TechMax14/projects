public class Records {
	
	String judgeName;
	String states;
	String category;
	int score;
	
	public Records(String judgeName, String states, String category, int score) { // records constructor
		this.judgeName = judgeName;
		this.states = states;
		this.category = category;
		this.score = score;	
	}
		
	public String getJN() { return judgeName; }
	
	public String getState() { return states; }
	
	public String getPop() { return category; }
	
	public int getArea() { return score; }
	
	public String toString() { return judgeName + "," + states + ","  //toString method for city info
								+ category + "," + score; }
	
}