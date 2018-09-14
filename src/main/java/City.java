import java.util.HashSet;

public class City{
	private String name;
	private HashSet<String> neighbors = new HashSet<String>();
	
	City(String name, String neighbor){
		this.name = name; 
		neighbors.add(neighbor);
	}
	
	public void addNeighbor(String neighbor) {
		neighbors.add(neighbor);
	}

	public String getName() {
		return name;
	}

	public HashSet<String> getNeighbors() {
		return neighbors;
	}	
}