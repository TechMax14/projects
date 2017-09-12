public class databaseInfo {
	private String word;
	private Integer freq;	
	public databaseInfo(String word, Integer freq) {
		this.word = word;
		this.freq = freq;
	}	
	public String getWord() {
		return word;
	}
	public Integer getFreq() {
		return freq;
	}
}