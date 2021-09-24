import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class Sort {
	//sets up the objects
	Scanner consoleInput = new Scanner(System.in);
	String fileName;
	ArrayList<Integer> unsortedNumbers = new ArrayList<Integer>();
	ArrayList<Integer> sortedNumbers = new ArrayList<Integer>();
	
	//does the various functions in the right order
	public Sort() {
		readFile();
		sorts();
	}
	
	//main function just starts up the program
	public static void main(String[] args) {
		new Sort();
	}
	
	public void readFile() {
		try {
			//gets the file from the user
			System.out.println("Which file would you like to sort");
			System.out.println("1,2,3,or 4");
			fileName = "input"+consoleInput.nextLine()+".txt";
			System.out.println(fileName);
			Scanner fileScanner = new Scanner(new File(fileName));
			String numberInput = fileScanner.nextLine();
			listNums(numberInput);
			System.out.println("There are "+unsortedNumbers.size()+" numbers in this file");
		} catch (IOException ex) {
			System.out.println("404 File not found error");
			consoleInput.close();
			System.exit(1);
		}
	}
	
	//puts the numbers from the file into an ArrayList
	public void listNums(String numberString) {
		String[] unsortedSlices = numberString.split(",");
		for (int i=0;i<unsortedSlices.length;i++) {
			unsortedNumbers.add(Integer.parseInt(unsortedSlices[i]));
		}
	}
	
	//checks which sorting method to use
	public void sorts() {
		System.out.println("Which sorting method do you want to use");
		System.out.println("\"Bubble\",\"Selection\", \"Table\"/\"Counting\", or \"Quick\"");
		String sortingMethod = consoleInput.nextLine();
		while ((!sortingMethod.equals("Bubble"))&&(!sortingMethod.equals("Selection"))&&(!sortingMethod.equals("Table"))&&(!sortingMethod.equals("Counting"))&&(!sortingMethod.equals("Quick"))) {
			System.out.println("Write \"Bubble\",\"Selection\", \"Table\", or \"Quick\"");
			sortingMethod = consoleInput.nextLine();
		}
		Instant timerStart=Instant.now();
		if (sortingMethod.equals("Bubble")) {
			bubbleSort();
		} else if (sortingMethod.equals("Selection")) {
			comparisonSort();
		} else if (sortingMethod.equals("Quick")){
			int[] sortedNumbers = quicksort(unsortedNumbers);
			for (int i=sortedNumbers.length-1;i>=0;i--) {
				System.out.println(sortedNumbers[i]);
			}
		} else {
			countingSort();
		}
		Instant timerStop=Instant.now();
		System.out.println(sortingMethod+" sort took "+Duration.between(timerStart, timerStop).toMillis()+" milliseconds to sort "+fileName);
	}
	
	//Bubble Sort
	public void bubbleSort() {
		//creates a comparison int and sets the unsorted numbers in an array
		int rightNum;
		int[] sortedNumbers = new int[unsortedNumbers.size()];
		boolean sorted=false;
		for (int i=0;i<sortedNumbers.length;i++) {
			sortedNumbers[i]=unsortedNumbers.get(i);
		}
		//keeps on running the algorithm until it makes it through the entire array without a single change
		while (!sorted) {
			sorted=true;
			for (int i=0;i<sortedNumbers.length-1;i++) {
				if (sortedNumbers[i]>sortedNumbers[i+1]) {
					sorted=false;
					rightNum=sortedNumbers[i];
					sortedNumbers[i]=sortedNumbers[i+1];
					sortedNumbers[i+1]=rightNum;
				}
			}
		}
		//prints the array
		for (int i=0;i<sortedNumbers.length;i++) {
			System.out.println(sortedNumbers[i]);
		}
	}
	
	//Comparison Sort
	//this method constantly comparison sorts out the smallest number from the unsorted numbers
	public void comparisonSort() {
		//keeps taking the smallest and puts it to the front of a new ListArray
		int nextNumber;
		for (int i=unsortedNumbers.size();i>0;i--) {
			nextNumber=comparisonSort(i);
			sortedNumbers.add(nextNumber);
			unsortedNumbers.remove(unsortedNumbers.indexOf(nextNumber));
			System.out.println(sortedNumbers.get(sortedNumbers.size()-1));
		}
	}
	
	//this method goes through all the items in unsorted numbers and finds the smallest
	public int comparisonSort(int range) {
		//this goes through all the numbers and compares them to find the smallest
		int nextNumber = unsortedNumbers.get(0);
		for (int i=0;i<range;i++) {
			if (nextNumber>unsortedNumbers.get(i)) {
				nextNumber=unsortedNumbers.get(i);
			}
		}
		
		return nextNumber;
	}
	
	//table/counting sort
	public void countingSort() {
		//turns the unsorted numbers into an array
		int[] unsortedNums = new int[unsortedNumbers.size()];
		int highestNum=unsortedNumbers.get(0);
		for (int i=0;i<unsortedNumbers.size();i++) {
			unsortedNums[i]=unsortedNumbers.get(i);
			if (unsortedNums[i]>highestNum) {highestNum=unsortedNums[i];}
		}
		//sets up the keys list which says the amount of each number
		int[] keys = new int[highestNum+1];
		for (int i=0;i<keys.length;i++) {
			keys[i]=0;
		}
		//further setting up of the keys list
		for (int i=0;i<unsortedNums.length;i++) {
			keys[unsortedNums[i]]=keys[unsortedNums[i]]+1;
		}
		//printing out the result
		for (int i=0;i<keys.length;i++) {
			if (keys[i]!=0) {
				System.out.println(keys[i]+":"+i);
			}
		}
	}
	
	//quicksort
	/*
	 * to find out how to quicksort I took a quick glance at the provided wiki page and then I just made my best guess about how it worked,
	 * I did not take the code from anywhere, I just took a wild guess and it somehow worked, this is actually the process I used for all the 
	 * sorting methods, I did not look at the videos I just saw how it was supposed to work and took a wild guess
	*/
	public int[] quicksort(ArrayList<Integer> disorganizedNums) {
		//splits the Arraylist of unsorted numbers into two groups surrounding one number and then keeps on calling itself until it is just a list of numbers
		if (disorganizedNums.size()==1) {return new int[] {disorganizedNums.get(0)};}
		int[] sortedNumbers;
		int pivotNum = disorganizedNums.get(0);
		ArrayList<Integer> biggerNums = new ArrayList<Integer>();
		ArrayList<Integer> smallerNums = new ArrayList<Integer>();
		for (int i=1;i<disorganizedNums.size();i++) {
			if (disorganizedNums.get(i)>pivotNum) {
				biggerNums.add(disorganizedNums.get(i));
			} else {
				smallerNums.add(disorganizedNums.get(i));
			}
		}
		sortedNumbers=new int[biggerNums.size()+smallerNums.size()+1];
		if (!(biggerNums.size()==0)) {
			int[] sortedBiggerNums = quicksort(biggerNums);
			for (int i=0;i<sortedBiggerNums.length;i++) {
				sortedNumbers[i] = sortedBiggerNums[i];
			}
		}
		sortedNumbers[biggerNums.size()]=pivotNum;
		if (!(smallerNums.size()==0)) {
			int[] sortedSmallerNums = quicksort(smallerNums);
			for (int i=0;i<sortedSmallerNums.length;i++) {
				sortedNumbers[i+1+biggerNums.size()] = sortedSmallerNums[i];
			}
		}
		//returns sorted list, it can't print it out within the method itself because it calls itself
		//so if it were to keep on printing there would be many more times the amount of words in the terminal
		return sortedNumbers;
	}
}
