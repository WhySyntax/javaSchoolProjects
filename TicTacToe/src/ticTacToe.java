import java.util.Arrays;
import java.util.Scanner;

//if I forget to remove this it is so that the program 
//doesn't yell at me whenever I test before using a variable
@SuppressWarnings({ "unused", "resource" })

public class ticTacToe {
	static int input(String horvert, boolean failed) {
		//this is a method so that I don't have to write the input code multiple times
		Scanner scanner = new Scanner(System.in);
		if (failed) {
			System.out.println("do a valid input, a, b, or c for horizontal and 1, 2, or 3 for vertical");
		} else if (horvert.equals("horizontal")) {
			System.out.println("which row will you put your symbol in");
		} else {
			System.out.println("which column will you put your symbol in");
		}
		if (horvert.equals("horizontal")) {
			String input = scanner.nextLine();
			if (input.equals("a")) {
				return 1;
			} else if (input.equals("b")) {
				return 2;
			} else if (input.equals("c")) {
				return 3;
			} else {return input(horvert,true);}
		} else {
			int numInput = Integer.parseInt(scanner.nextLine());
			if ((numInput!=1)&&(numInput!=2)&&(numInput!=3)) {
				return input(horvert,true);
			} else {
				return numInput;
			}
		}
	}
	static String[] wonYet(String[][] gameArray) {
		//this is a method to check if anyone has won yet
		
		// checks if there is a diagonal win
		if ((((gameArray[1][1]==gameArray[2][2])&&(gameArray[2][2]==gameArray[3][3]))||((gameArray[1][3]==gameArray[2][2])&&(gameArray[2][2]==gameArray[3][1])))&&(!(gameArray[2][2]==" "))) {
			String[] output = {"0",gameArray[2][2]};
			return output;
		}
		//checks for horizontal or vertical wins
		for (int x=1;x<=3;x++) {
			if ((gameArray[x][1]==gameArray[x][2])&&(gameArray[x][2]==gameArray[x][3])&&(gameArray[x][2]!=" ")) {
				String[] output = {"0",gameArray[x][3]};
				return output;
			}else if ((gameArray[1][x]==gameArray[2][x])&&(gameArray[2][x]==gameArray[3][x])&&(gameArray[2][x]!=" ")) {
				String[] output = {"0",gameArray[1][x]};
				return output;
			}
		}
		//if nobody has won
		String[] output = {"1"};
		return output;
	}
	static int ticTacToe() {
		// the game is a method so that it can tell the main function who won if there is a way to return values
		//without making it a function I don't know it, and I didn't want to search many things up for this project
		//so I went with what I knew, in fact the only thing in this project that I didn't know how to do was print out the array
		//creating the array that the game will contain
		//and a variable to check if someone has won
		boolean won = false;
		String winner = "";
		String[][] game = {{"x","1","2","3"},{"a"," "," "," "},{"b"," "," "," "},{"c"," "," "," "}};
		System.out.println(Arrays.toString(game[0])+"\n"+Arrays.toString(game[1])+"\n"+Arrays.toString(game[2])+"\n"+Arrays.toString(game[3]));
		
		while (!won) {
			//this code is the game itself
			//x's turn
			int x_horizontal = input("horizontal",false);
			int x_vertical = input("vertical",false);
			//if move is legal it places, else it threatens to skip their turn
			if (game[x_horizontal][x_vertical]==" ") {
				game[x_horizontal][x_vertical]="x";
			} else {
				System.out.println("illegal move, already taken, do it right or your turn is skipped");
				x_horizontal = input("horizontal",false);
				x_vertical = input("vertical",false);
				if (game[x_horizontal][x_vertical]==" ") {
					game[x_horizontal][x_vertical]="x";
				} else {System.out.println("already taken, your turn is skipped");}
			}
			//indicates that it is now o's turn in top left corner
			game[0][0]="o";
			//reprints the board
			System.out.println(Arrays.toString(game[0])+"\n"+Arrays.toString(game[1])+"\n"+Arrays.toString(game[2])+"\n"+Arrays.toString(game[3]));
			
			//checks if all the spaces are full
			for (String[] element:game) {
				for (String blank:element) {
					if (blank.equals(" ")) {
						won = false;
						break;
					} else {
						won = true;
						winner = "nobody";
					}
				}
			}
			//check if someone has won
			if (wonYet(game)[0]=="0") {
				won = true;
				winner = wonYet(game)[1];
				break;
			}
			
			if (won) {
				break;
			}
			//o's turn
			int o_horizontal = input("horizontal",false);
			int o_vertical = input("vertical",false);
			//same as with x
			if (game[o_horizontal][o_vertical]==" ") {
				game[o_horizontal][o_vertical]="o";
			} else {
				System.out.println("illegal move, already taken, do it right or your turn is skipped");
				o_horizontal = input("horizontal",false);
				o_vertical = input("vertical",false);
				if (game[o_horizontal][o_vertical]==" ") {
					game[o_horizontal][o_vertical]="o";
				} else {System.out.println("already taken, your turn is skipped");}
			}
			game[0][0]="x";
			System.out.println(Arrays.toString(game[0])+"\n"+Arrays.toString(game[1])+"\n"+Arrays.toString(game[2])+"\n"+Arrays.toString(game[3]));
			
			//check if someone has won
			if (wonYet(game)[0]=="0") {
				won = true;
				winner = wonYet(game)[1];
				break;
			}
		}
		//states the winner
		System.out.println(winner+" wins!!!");
		if (winner=="x") {
			return 0;
		} else if (winner=="o") {return 1;} else {return 2;}
	}
	public static void main(String[] args) {
		//tracks wins
		int x_wins = 0;
		int o_wins = 0;
		int ties  = 0;
		boolean playAgain=true;
		Scanner scanner = new Scanner(System.in);
		while (playAgain) {
			//plays games
			int a = ticTacToe();
			if (a==1) {
				o_wins++;
			} else if (a==0){x_wins++;} else {ties++;}
			//checks if you wish to play again
			System.out.println("x has won "+x_wins+" times\no has won "+o_wins+" times");
			System.out.println("do you want to play again, \"yes\" or \"no\"");
			String checkAgain = scanner.nextLine();
			if (checkAgain.equals("no")) {
				playAgain = false;
			}
		}
	}
}