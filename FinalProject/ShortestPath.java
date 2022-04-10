import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ShortestPath {


	ShortestPath(int start, int destination, ArrayList<String> stops, ArrayList<String> names, ArrayList<Integer> idList) {
		//decision doc why Dijkstra? - as all costs are positive
		// floud warshall - only if you need everywhere to everywhere - as we are entering two specific stops, FW is not optimal

		int n = -1;

		Scanner scanner;
		try {
			File file = new File("stops.txt");
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				stops.add(scanner.nextLine());
				n++;

			}
		}
		catch (FileNotFoundException e) {
			System.out.print("File Not Found.");
		}


		for(int i = 1, j=0; i < stops.size(); i++, j++) {
			String[] str = stops.get(i).split(",");
			idList.add(Integer.parseInt(str[0]));
			names.add(str[2]);

		}
		

	}
	
	public boolean doStopsExist(int start, int destination, ArrayList<Integer> idList) {
		
		if(idList.contains(start)) {
			System.out.println("Start bus stop exists.");
		}
		else {
			System.out.println("Start bus stop does not exist.");
			return false;
		}
		
		if(idList.contains(destination)) {
			System.out.println("Destination bus stop exists.");
		}
		else {
			System.out.println("Destination bus stop does not exist.");
			return false;
		}
		
		return true;
	}
	
	public void pathFinder(int start, int destination, ArrayList<String> stops, ArrayList<String> names, ArrayList<Integer> idList) {
		
		Scanner scanner;
		ArrayList<String> tripTransfers = new ArrayList<String>();

		try {
			File file = new File("transfers.txt");
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				tripTransfers.add(scanner.nextLine());

			}
		}
		catch (FileNotFoundException e) {
			System.out.print("File Not Found.");
		}
		
		ArrayList<String> stopTransfers = new ArrayList<String>();

		try {
			File file = new File("stop_times.txt");
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				stopTransfers.add(scanner.nextLine());
			}
		}
		catch (FileNotFoundException e) {
			System.out.print("File Not Found.");
		}
		
		int edges = (tripTransfers.size() - 1) + (stopTransfers.size() - 1);

		int[] stopA = new int[edges];
		int[] stopB = new int[edges];
		double[] cost = new double[edges];



		for(int i = 1, j = 0; i<tripTransfers.size(); i++, j++) {

			String[] str = tripTransfers.get(i).split(",");

			for(int k = 0; k<idList.size(); k++) {
				if(Integer.parseInt(str[0]) == idList.get(k)) {
					stopA[j] = k;
				}
				if(Integer.parseInt(str[1]) == idList.get(k)) {
					stopB[j] = k;
				}


			}

			if(Integer.parseInt(str[2]) == 0) {
				cost[j] = 2;
			}
			if(Integer.parseInt(str[2]) == 2) {
				cost[j] = Integer.parseInt(str[3])/100;
			}
		}

		int k = tripTransfers.size()-1;

		for(int i = 1, j=2; j < stopTransfers.size(); i++, j++) {
			String s = stopTransfers.get(i);
			String t = stopTransfers.get(j);
			String[] sArray = s.split(",");
			String[] tArray = t.split(",");

			int a = 0, b = 0;

			if(Integer.parseInt(sArray[0]) == Integer.parseInt(tArray[0])) {
				for(int m = 0; m<idList.size(); m++) {
					if(Integer.parseInt(sArray[3]) == idList.get(m)) {
						stopA[k] = m;
					}
					if(Integer.parseInt(tArray[3]) == idList.get(m)) {
						stopB[k] = m;
					}

				}

				cost[k] = 1;
				k++;
			}



		}


		int E = stopA.length;
		
		int n = stops.size() -1;

		EdgeWeightedDigraph G = new EdgeWeightedDigraph(n);

		for (int i = 0; i < E; i++) {
			int v = stopA[i];
			int w = stopB[i];
			double weight = cost[i];
			G.addEdge(new DirectedEdge(v, w, weight));
		}

		int s = 0;
		int d = 0;


		for(int i = 0; i<idList.size(); i++) {
			if(idList.get(i) == start) {
				s = i;
			}

			if(idList.get(i) == destination) {
				d = i;
			}
		}

		DijkstraSP sp = new DijkstraSP(G, s);

		if (sp.hasPathTo(d)) {
			System.out.printf("%d to %d (%.2f)  ", start, destination, sp.distTo(d));
			
			for (DirectedEdge e : sp.pathTo(d)) {
				//System.out.println(e + "   ");
				//System.out.print(idList.get(e.to()) + " to " + idList.get(e.from()) + " (" + String.format("%.2f",e.weight()) +") ");
				System.out.print(idList.get(e.from()) + "->" + idList.get(e.to()) + " " + String.format("%5.2f",e.weight()) + "   ");
			}

			System.out.println();

			for (DirectedEdge e : sp.pathTo(d)) {
				if((e.weight() == 2) || e.weight() == 0){
					System.out.println("TRANSFER: " + names.get(e.from()) + " to " + names.get(e.to()));
				}
				else {
					System.out.println(names.get(e.from()) + " to " + names.get(e.to()));
				}

			}

			System.out.println();
		}
		else {
			System.out.printf("%d to %d         no path exists.\n", s, d);
			
		}
	}

}
