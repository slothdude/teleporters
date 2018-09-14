import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

//Runner class, contains parser and some setup
public class TeleporterRunner{
	
	//data structure for mapping the city name to the corresponding city object
	private static HashMap<String, City> cities;
	//instance of teleporter for calling queries
	private static Teleporter t;
	
	private static void parseNewConnection(String curLine) {
		String city1 = "";
		String city2 = "";
		Pattern p = Pattern.compile("(-)");
		Matcher m = p.matcher(curLine);
		while(m.find()) {
			city1 = (curLine.substring(0, m.start()).trim());
			city2 = (curLine.substring(m.end(), curLine.length()).trim());
		}
		//updates 'cities' data structure so city 1 and city 2 are 
		//neighbors and vice versa
		if(cities.containsKey(city1)) {
			cities.get(city1).addNeighbor(city2);
			if(cities.containsKey(city2)) {
				cities.get(city2).addNeighbor(city1);
			} else {
				cities.put(city2, new City(city2, city1));
			}
		} else {
			cities.put(city1, new City(city1, city2));
			if(cities.containsKey(city2)) {
				cities.get(city2).addNeighbor(city1);
			} else {
				cities.put(city2, new City(city2, city1));
			}
		}
		t = new Teleporter(cities);
	}
	
	private static void parseReachQuery(String curLine) {
		Pattern p = Pattern.compile("from (.+) in (\\d+)");
		Matcher m = p.matcher(curLine);
		while(m.find()) {
			t.reachQuery(m.group(1), Integer.parseInt(m.group(2)));
		}
	}
	
	private static void parseReachableQuery(String curLine) {
		Pattern p = Pattern.compile("from (.+) to (\\w+)");
		Matcher m = p.matcher(curLine);
		while(m.find()) {
			t.reachableQuery(m.group(1), m.group(2));
		}
	}
	
	private static void parseLoopQuery(String curLine) {
		Pattern p = Pattern.compile("from (.+)");
		Matcher m = p.matcher(curLine);
		while(m.find()) {
			t.loopQuery(m.group(1));
		}
	}
	
	public static void main(String[] args) {
		cities = new HashMap<String,City>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File("input.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (sc.hasNextLine()) {
			String curLine = sc.nextLine();
			if(curLine.contains("-"))
				parseNewConnection(curLine);
			else if(curLine.contains("jumps"))
				parseReachQuery(curLine);
			else if(curLine.contains("teleport"))
				parseReachableQuery(curLine);
			else if(curLine.contains("loop"))
				parseLoopQuery(curLine);
			else System.out.println("Unable to process line: " + curLine);
		}
	}
	
}

