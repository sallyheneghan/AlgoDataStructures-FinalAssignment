import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TripSearch {

   

	TripSearch(ArrayList<String> arrivalTimes) {

		ArrayList<String> tripStrings = new ArrayList<String>();

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
		}
		
	}
	



	public boolean doesTripExist(String userInput, ArrayList<String> arrivalTimes) {
		for(int i = 0; i<arrivalTimes.size(); i++) {
			String s = arrivalTimes.get(i);
			if(s.contains(userInput)) {
				return true;
			}
		}
		return false;
	}




	public static void main(String[] args) {
		ArrayList<String> arrivalTimes = new ArrayList<String>();
		
		TripSearch ts = new TripSearch(arrivalTimes);

		Scanner input = new Scanner(System.in);
		System.out.print("Enter an arrival time in the form hh:mm:ss  : ");
		String userInput = input.next();
		System.out.println("Arrival Time entered: " + userInput);
		
		boolean check = ts.doesTripExist(userInput, arrivalTimes);
		if (check == true) {
			System.out.print("Arrival Time Exists!");
		}
		else {
			System.out.print("Arrival Time DOES NOT Exist!");
		}
	
	}


} 
