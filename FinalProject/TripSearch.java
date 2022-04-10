import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TripSearch {

	TripSearch(ArrayList<String> arrivalTimes, ArrayList<String> tripStrings, ArrayList<Integer> tripID) {



		Scanner scanner;
		try {
			File file = new File("stop_times.txt");
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				tripStrings.add(scanner.nextLine());

			}
		}
		catch (FileNotFoundException e) {
			System.out.print("File Not Found.");
		}

		// forms an arrayList of only arrival_times


		for(int y = 0; y<tripStrings.size(); y++) {
			String s = tripStrings.get(y);
			String[] currentTripArray = s.split(",");
			arrivalTimes.add(currentTripArray[1]);
			if(currentTripArray[0].contains("trip_id")) {
				tripID.add(0);
			}
			else {
				tripID.add(Integer.parseInt(currentTripArray[0]));	
			}
		}
	}

	
	public boolean doesTripExist(String userInput, ArrayList<String> arrivalTimes, ArrayList<String> tripStrings, 
			ArrayList<String> arrivalsStrings, ArrayList<Integer> correctIds, ArrayList<Integer> tripId, ArrayList<Integer> correctIdIndex){
		boolean correct = false;
		for(int i = 0; i<arrivalTimes.size(); i++) {
			String s = arrivalTimes.get(i);
			if(s.contains(" " + userInput) || s.contentEquals(userInput)) {
				arrivalsStrings.add(tripStrings.get(i));
				correctIds.add(tripId.get(i));
				correctIdIndex.add(i);
				correct = true;

			}
		}
		if(correct == true) {

			return true;
		}
		return false;
	}

	
	public void printOrganisedStrings(ArrayList<Integer> correctIdIndex,
			ArrayList<String> tripStrings){

		for(int i = 0; i < correctIdIndex.size(); i++) {
			for(int t = 0; t < tripStrings.size(); t++) {
				if(correctIdIndex.get(i) == t) {
					System.out.println(tripStrings.get(t));
				}
			}
		}

	}

	
	static ArrayList<Integer> insertionSort (ArrayList<Integer> correctIds, ArrayList<Integer> correctIdIndex){

		ArrayList<Integer> returnArray = new ArrayList<Integer>();
		int[] tempA = new int[correctIds.size()];

		for(int t=0; t<tempA.length; t++) {
			tempA[t] = correctIds.get(t);
		}

		int[] tempIndexA =  new int[correctIds.size()];
		for(int t=0; t<tempA.length; t++) {
			tempIndexA[t] = correctIdIndex.get(t);
		}


		int tempInt;
		int tempIndexInt;
		for (int i = 1; i < tempA.length; i++) {
			for (int j = i ; j > 0 ; j--) {
				if(tempA[j] < tempA[j-1]) {
					tempInt = tempA[j];
					tempIndexInt = tempIndexA[j];
					tempA[j] = tempA[j-1];
					tempIndexA[j] = tempIndexA[j-1];
					tempA[j-1] = tempInt;
					tempIndexA[j-1] = tempIndexInt;
				}
			}
		}

		for(int t = 0; t<tempIndexA.length; t++) {
			returnArray.add(tempIndexA[t]);
		}

		return returnArray;
	}


} 