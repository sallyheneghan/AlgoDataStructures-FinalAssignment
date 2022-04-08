import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ShortestPath {


	ShortestPath(int start, int destination) {
		//decision doc why Dijkstra? - as all costs are positive
		// floud warshall - only if you need everywhere to everywhere - as we are entering two specific stops, FW is not optimal

		ArrayList<String> stops = new ArrayList<String>();

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



		ArrayList<Integer> idList = new ArrayList<Integer>();
		ArrayList<String> names = new ArrayList<String>();
		for(int i = 1, j=0; i < stops.size(); i++, j++) {
			String[] str = stops.get(i).split(",");
			idList.add(Integer.parseInt(str[0]));
			names.add(str[2]);

		}

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
					//System.out.print(stopA[j]);
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

		/*
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
		*/

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

				//System.out.println("StopA[k]: " + stopA[k]);
				//stopB[k] = b;
				cost[k] = 1;
				k++;
			}



		}


		int E = stopA.length;

		System.out.println("Number of stops: " + n);
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


		System.out.println("Source stop id: " + s);
		System.out.println("Destination stop id: " + d);

		DijkstraSP sp = new DijkstraSP(G, s);

		//for (int t = 0; t < G.V(); t++) {
		if (sp.hasPathTo(d)) {
			System.out.printf("%d to %d (%.2f)  ", start, destination, sp.distTo(d));
			//Change so that actual edges (not edge indexes) are printed. 
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
			System.out.printf("%d to %d         no path\n", s, d);
		}
	}
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Enter start stop: " );
		int start = scanner.nextInt();
		
		System.out.print("Enter destination stop: ");
		int destination = scanner.nextInt();
		
		ShortestPath sp = new ShortestPath(start, destination);

		
		
	}

}
