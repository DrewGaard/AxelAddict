
public class CarMatches {
	private String Make;
	private String Model;
	private int occurence;
	public int getOccurence() {
		return occurence;
	}
	public void setOccurence(int occurence) {
		this.occurence = occurence;
	}
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	public String getMake() {
		return Make;
	}
	public void setMake(String make) {
		Make = make;
	}
	public String toString(){
		return "Make: "+Make+" Model: "+Model+" Occurences: "+occurence;
	}
}
