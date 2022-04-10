import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BusStopSearch {

	public BusStopSearch(ArrayList<String> busStrings, ArrayList<String> stopNames,
			TST<Integer> busTST) {

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
					stopName = stopName + stopN[y];
				}

				stopName = stopName + stopN[0] + stopN[1];
			}
			else if ((stopN[0].equalsIgnoreCase("WB")) || (stopN[0].equalsIgnoreCase("NB"))|| 
					(stopN[0].equalsIgnoreCase("SB")) || (stopN[0].equalsIgnoreCase("EB"))){
				for(int y = 1; y<stopN.length; y++) {
					stopName = stopName + stopN[y];
				}
				stopName = stopName + stopN[0];
			}
			else {
				for(int y = 0; y<stopN.length; y++) {
					stopName = stopName + stopN[y];
				}

			}
			//Adds the current stopName to the ArrayList<String> stopNames
			stopNames.add(i + " " + stopName);

		}

		
		for(int i=0; i<stopNames.size(); i++) {
			String str = stopNames.get(i);
			String[] currentStr = str.split(" ");
			for(int k = 0; k<currentStr.length; k++) {
				if(!currentStr[k].equals(""))
				{
					busTST.put(currentStr[k], k);
				}
			}
		
		}
	}
	
	

	public boolean checkForBusStop(String stopNameInput, ArrayList<String> busStrings,
			ArrayList<String> stopNames, TST<Integer> busTST, ArrayList<String> returnedStops){


		int i = 0;
		for (String t : busTST.keysWithPrefix(stopNameInput)) {
			returnedStops.add(t);
			i++;
	
		}
		if(i > 0) {
			return true;
		}
		
		return false;
	}
	
	
	
	public void printStopInfo(ArrayList<String> busStrings, ArrayList<String> stopNames, ArrayList<String> returnedStops) {
		
		int n = 0;
		
		for(int index = 0; index < stopNames.size(); index++) {
			for(int ind2 = 0; ind2 < returnedStops.size(); ind2++) {
				if(stopNames.get(index).contains(returnedStops.get(ind2))) {
					String[] stopString = stopNames.get(index).split(" ");
					n = Integer.parseInt(stopString[0]);
					System.out.println(busStrings.get(n));
				}
			}
			
		}
		
	}
	
}
