import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Teleporter {
	
	private HashMap<String,City> adjList;
	
	Teleporter(HashMap<String,City> adjList){
		this.adjList = adjList;
	}
	
	public void printAdj() {
		String adj;
		Iterator<City> iter = adjList.values().iterator();
		while(iter.hasNext()) {
			adj = "";
			City c = iter.next();
			Iterator<String> neighborIter = c.getNeighbors().iterator();
			while(neighborIter.hasNext()) {
				String neighbor = neighborIter.next();
				adj += " " + neighbor + ",";
			}
			System.out.println("from " + c.getName() + " to:" + adj);
		}
	}
	
	//visited: nodes from startCities that have had all neighbors checked
	private HashSet<String> reachHelp(HashSet<String> startCities, int jumps,
			HashSet<String> visited){
		if(jumps <= 0)
			return startCities;
		else {
			HashSet<String> newStarts = new HashSet<String>();
			newStarts.addAll(startCities);
			Iterator<String> cityIter = startCities.iterator();
			while(cityIter.hasNext()) {
				String s = cityIter.next();
				HashSet<String> neighbors = adjList.get(s).getNeighbors();
				//If a neighbor has not been visited, add to startCities
				Iterator<String> neighIter = neighbors.iterator();
				while(neighIter.hasNext()) {
					String neighbor = neighIter.next();
					if(!visited.contains(neighbor)) {
						newStarts.add(neighbor);
					}
				}
				visited.add(s);
			}
			return reachHelp(newStarts, jumps - 1, visited);
		}
	}
	
	//made a helper because I feel like this should take a string and an int
	public void reachQuery(String startCity, int jumps){
		String returning = "cities from " + startCity + " in " 
				+ jumps + " jumps:";
		HashSet<String> startCities = new HashSet<String>();
		startCities.add(startCity);
		HashSet<String> reachable = reachHelp(startCities,jumps, 
				new HashSet<String>());
		reachable.remove(startCity);
		Iterator<String> iter = reachable.iterator();
		while(iter.hasNext()) {
			String curCity = iter.next();
			returning += " " + curCity + ",";
		}
		System.out.println(returning.substring(0,returning.length()-1));
	}
	
	//helper to see if a city is reachable from another
	private boolean reachableHelp(HashSet<String> startCities, 
			String goal, HashSet<String> visited) {
		if(startCities.contains(goal))
			return true;
		else {
			HashSet<String> origVisited = visited;
			HashSet<String> newStarts = new HashSet<String>();
			newStarts.addAll(startCities);
			Iterator<String> cityIter = startCities.iterator();
			while(cityIter.hasNext()) {
				String s = cityIter.next();
				HashSet<String> neighbors = adjList.get(s).getNeighbors();
				//If a neighbor has not been visited, add to newStarts
				Iterator<String> neighIter = neighbors.iterator();
				while(neighIter.hasNext()) {
					String neighbor = neighIter.next();
					if(!visited.contains(neighbor)) {
						newStarts.add(neighbor);
					}
				}
				visited.add(s);
			}
			//if nothing changing, return false to prevent infinite recursion
			if(startCities.containsAll(newStarts) && 
					origVisited.containsAll(visited))
				return false;
			return reachableHelp(newStarts, goal, visited);
		}
	}
	
	//made helper so this takes strings
	public void reachableQuery(String startCity, String endCity) {
		String returning = "can I teleport from " + startCity + " to " 
				+ endCity + ": ";
		HashSet<String> startCities = new HashSet<String>();
		startCities.add(startCity);
		returning += (reachableHelp(startCities, endCity, 
				new HashSet<String>()) ? "yes" : "no");
		System.out.println(returning);
	}
	
	//follows single chain of teleporters one neighbor per call, dfs
	//visited should only be goal and the neighbor used to get to curCity 
	//on first call, and curCity must be a second degree neighbor
	
	public boolean loopHelp(String curCity, String goal, 
			HashSet<String> visited) {
		if(curCity.equals(goal) || 
				adjList.get(curCity).getNeighbors().contains(goal))
			return true;
		else {
			visited.add(curCity);
			HashSet<String> neighbors = adjList.get(curCity).getNeighbors();
			Iterator<String> cityIter = neighbors.iterator();
			while(cityIter.hasNext()) {
				String s = cityIter.next();
				if(!visited.contains(s))
					return loopHelp(s, goal, visited); 
			}
			//if doesn't have any more neighbors, not possible on this call
			return false;
		}
	}
	
	//made helper so this only takes a string
	//has to be minimum of three connections, so first two are done manually
	public void loopQuery(String city) {
		String returning = "loop possible from " + city + ": ";
		boolean possible = false;
		HashSet<String> neighbors1 = new HashSet<String>();
		HashSet<String> neighbors2 = new HashSet<String>();
		//need below line because can't change something you're iterating over
		HashSet<String> visited = new HashSet<String>();
		neighbors1.addAll(adjList.get(city).getNeighbors());
		Iterator<String> iter1 = neighbors1.iterator();
		//iterate all first degree neighbors
		while(iter1.hasNext()) {
			visited = new HashSet<String>();
			String s1 = iter1.next();
			visited.add(city);
			visited.add(s1);
			neighbors2 = adjList.get(s1).getNeighbors();
			Iterator<String> iter2 = neighbors2.iterator();
			//iterate all second degree neighbors and check if it is possible
			//to get back to 'city' without using 'city' or 's1'
			while(iter2.hasNext()) {
				String s2 = iter2.next();
				if(!s2.equals(city))
					possible = possible || loopHelp(s2, city, visited);
			}
		}
		returning += possible;
		System.out.println(returning);
	}
}