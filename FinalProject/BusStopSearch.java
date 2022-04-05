import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BusStopSearch {
	
	public BusStopSearch(ArrayList<String> busStrings) {
		
		TST<String> busTST = new TST<String>(); 
		
		Scanner scanner;
		try {
			File file = new File("stops.txt");
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				busStrings.add(scanner.nextLine());

			}
		}
		catch (FileNotFoundException e) {
			System.out.print("File Not Found.");
		}


		for(int i=0; i < busStrings.size(); i++) {
			String s = busStrings.get(i);
			String[] currentStop = s.split(",");
			String[] stopN = currentStop[2].split(" ");
			String stopName = "";
			if(stopN[0].equalsIgnoreCase("FLAGSTOP")) {
				for(int y = 2; y<stopN.length; y++) {
					stopName = stopName + " " + stopN[y];
				}
				stopName = stopName + " " + stopN[0] + " " + stopN[1];
			}
			else if ((stopN[0].equalsIgnoreCase("WB")) || (stopN[0].equalsIgnoreCase("NB"))|| 
					(stopN[0].equalsIgnoreCase("SB")) || (stopN[0].equalsIgnoreCase("EB"))){
				for(int y = 1; y<stopN.length; y++) {
					stopName = stopName + " " + stopN[y];
				}
				stopName = stopName + " " + stopN[0];
			}
			else {
				for(int y = 0; y<stopN.length; y++) {
					stopName = stopName + " " + stopN[y];
				}
				
			}
			System.out.println(stopName);
		}


	}
	
	public static void main(String[] args) {
		ArrayList<String> busStrings = new ArrayList<String>();
		
		BusStopSearch bss = new BusStopSearch(busStrings);
	}

	
}
