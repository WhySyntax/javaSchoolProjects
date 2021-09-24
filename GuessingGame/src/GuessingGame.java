import java.util.Scanner;

public class GuessingGame {
    public GuessingGame() {
        int RandomNum = (int)(Math.random()*50);
        Scanner scanner = new Scanner(System.in);
        System.out.println("A number between 1 and 50 has been chosen for you to guess");
        boolean win = false;
        System.out.println("make a Guess");
        int guesses = 0;
        while (!win) {
            String input = scanner.nextLine();
            int guess = Integer.parseInt(input);
            guesses += 1;
            if (guess == RandomNum) {
                System.out.println("Correct, the number was "+RandomNum+"\nIt took you "+guesses+" guesses to find the right number");
                win = true;
            } else if (guess > RandomNum) {
                System.out.println("Incorrect, Too High");
            } else if (guess < RandomNum) {
                System.out.println("Incorrect, Too Low");
            }
        }

    }
    public static void main(String[] args) {
        boolean playAgain = true;
        Scanner scanner = new Scanner(System.in);
        while (playAgain) {
            new GuessingGame();
            System.out.println("Do you want to play again? yes or no");
            String input = scanner.nextLine();
            if (input.equals("no")) {
                playAgain = false;
            }
        }


    }
}
