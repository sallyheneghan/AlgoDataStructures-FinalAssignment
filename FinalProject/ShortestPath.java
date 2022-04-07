import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ShortestPath {

	
	ShortestPath() {
		//decision doc why Dijkstra? - as all costs are positive
		// floud warshall - only if you need everywhere to everywhere - as we are entering two specific stops, FW is not optimal

		
		
	}
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Enter start stop: " );
		String start = scanner.next();
		
		System.out.print("Enter destination stop: ");
		String destination = scanner.next();
		
		ShortestPath sp = new ShortestPath();
		//ShortestPath();
		
		
	}

}
