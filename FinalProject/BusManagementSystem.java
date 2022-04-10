import java.util.ArrayList;
import java.util.Scanner;

public class BusManagementSystem {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		String input = "";

		while(!input.equalsIgnoreCase("exit")) {
			System.out.print("Enter name of service you would like to use "
					+ "['shortestPathFinder', 'busStopFinder', 'tripSearchFinder'] OR "
					+  "'exit' to finish: ");
			input = scanner.next();

			if(!input.equalsIgnoreCase("exit")) {
				if(input.equalsIgnoreCase("shortestPathFinder")) {
					shortestPathFinder();
					System.out.println();
				}

				else if(input.equalsIgnoreCase("busStopFinder")) {
					busStopFinder();
					System.out.println();
				}

				else if(input.equalsIgnoreCase("tripSearchFinder")) {
					tripSearchFinder();
					System.out.println();
				}

				else {
					System.out.println("Invalid input.");
				}
			}

		}
		
		System.out.print("Bus Stop Management Exited. The End.");

	}
	
	

	private static void shortestPathFinder() {
		
		ArrayList<String> stops = new ArrayList<String>();
		ArrayList<Integer> idList = new ArrayList<Integer>();
		ArrayList<String> names = new ArrayList<String>();

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter start stop: " );
		int start = scanner.nextInt();

		System.out.print("Enter destination stop: ");
		int destination = scanner.nextInt();

		ShortestPath sp = new ShortestPath(start, destination, stops, names, idList);
		if(sp.doStopsExist(start, destination, idList) == true) {
			sp.pathFinder(start, destination, stops, names, idList);
		}
		else {
			System.out.print("As stop does not exist, input is invalid.");
		}

	}

	private static void busStopFinder() {
		ArrayList<String> busStrings = new ArrayList<String>();
		ArrayList<String> stopNames = new ArrayList<String>();
		ArrayList<String> returnedStops = new ArrayList<String>();

		TST<Integer> busTST = new TST<Integer>(); 

		BusStopSearch bss = new BusStopSearch(busStrings, stopNames, busTST);

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter stop name: ");
		String stopNameInput = scanner.nextLine();
		
		if(bss.checkForBusStop(stopNameInput, stopNames, busStrings, busTST, returnedStops) == true) {
			bss.printStopInfo(busStrings, stopNames, returnedStops);
		}
		else {
			System.out.print("Stop does not exist.");
		}
	
	}

	private static void tripSearchFinder() {
		
		ArrayList<String> arrivalTimes = new ArrayList<String>();
		ArrayList<String> tripStrings = new ArrayList<String>();
		ArrayList<String> arrivalsStrings = new ArrayList<String>();
		ArrayList<Integer> correctIdIndex = new ArrayList<Integer>();
		ArrayList<Integer> correctIds = new ArrayList<Integer>();
		ArrayList<Integer> tripID = new ArrayList<Integer>();

		TripSearch ts = new TripSearch(arrivalTimes, tripStrings, tripID);

		Scanner input = new Scanner(System.in);
		System.out.print("Enter an arrival time in the form hh:mm:ss  : ");
		String userInput = input.next();
		if(!userInput.contains(":")) {
			System.out.print("Wrong Format Entered.");
		}
		else {
			String[] timeCheck = userInput.split(":");
			try {
				Integer.parseInt(timeCheck[0]);
				Integer.parseInt(timeCheck[1]);
				Integer.parseInt(timeCheck[2]);

				if ((Integer.parseInt(timeCheck[0]) > 23) || (Integer.parseInt(timeCheck[1]) > 59)
						|| (Integer.parseInt(timeCheck[2]) > 59) || (timeCheck[0].length() > 2) || (timeCheck.length > 3) 
						|| (userInput.toCharArray().length > 8 ) || (userInput.toCharArray().length < 7 )) {
					System.out.print("Wrong Format Entered.");
				}
				else {

					System.out.println("Arrival Time entered: " + userInput);

					boolean check = ts.doesTripExist(userInput, arrivalTimes,  tripStrings, arrivalsStrings, 
							correctIds, tripID, correctIdIndex);
					if (check == true) {
						System.out.println("Arrival Time Exists!");
					}
					else {
						System.out.println("Arrival Time DOES NOT Exist - No buses arrive at the time entered.");
					}

					correctIdIndex = ts.insertionSort(correctIds, correctIdIndex);
					ts.printOrganisedStrings(correctIdIndex, tripStrings);

					System.out.print("The End.");
				}
			}
			catch (NumberFormatException e) {
				System.out.println("Wrong Format Entered.");
			}

		}

	}

}
