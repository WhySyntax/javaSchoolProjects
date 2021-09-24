import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class BinaryTranslator {
	public BinaryTranslator() {
		System.out.println("Please enter \"file\" to enter a file or \"input\" to use the console.");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		String numberInput = "";
		if (input.equals("file")) { //input from file
			try {
				System.out.println("Enter file name.");
				input = scanner.nextLine();
				Scanner fileScanner = new Scanner(new File(input));
				numberInput = fileScanner.nextLine();
			} catch (IOException ex) {
				System.out.println("404 File not found error");
				scanner.close();
				System.exit(1);
			}
		} else { //input from console
			System.out.println("Input the number of your choosing");
			numberInput = scanner.nextLine();
		}
		System.out.println("For decimal to binary type \"DTB\"|nFor binary to decimal type \"BTD\"");
		input = scanner.nextLine();
		if (input.equals("DTB")) {//Decimal to binary
			String answer = "";
			int number = Integer.parseInt(numberInput);
			while(number > 0) {
				if (number%2 == 1) {
					answer = "1" + answer;
				} else {answer = "0" + answer;}
				number = number/2;
			}
			System.out.println(numberInput+" in binary is "+answer);
		} else { //Binary to decimal
			int answer = 0;
			int multiplier = 0;
			for (int a  = numberInput.length() - 1; a >= 0; a--) {
				if (numberInput.charAt(a) == '1') {
					answer += (int)(Math.pow(2, multiplier));
				}
				multiplier ++;
			}
			System.out.println(numberInput+" from binary to base ten is: "+answer);
		}
		
		
		scanner.close();
	}
	public static void main(String[] args) {
		new BinaryTranslator();
	}
}
