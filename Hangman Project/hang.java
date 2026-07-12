import java.util.Scanner;

public class hang {
    public static void main(String[] args) {
   
        String[] words = {"stick", "fan", "clock", "tiger", "lahore", "lizard", "hammer", "gun", "horse"};
        String secretWord = words[(int) (Math.random() * words.length)];
        
        Scanner scanner = new Scanner(System.in);
        int lives = 6; 
        char[] display = new char[secretWord.length()];
        
        for (int i = 0; i < display.length; i++) {
            display[i] = '_';
        }

        System.out.println("--- Welcome to Hangman! ---");
        System.out.println("Guess the word! It has " + secretWord.length() + " letters.");

        while (lives > 0) {
          
            printHangman(lives);

            System.out.print("\nWord: ");
            for (char c : display) {
                System.out.print(c + " ");
            }
            
            System.out.println("\nLives left: " + lives);
            System.out.print("Enter your guess: ");
            
            String input = scanner.nextLine().toLowerCase();
            
            if (input.isEmpty()) {
                System.out.println("Enter a word:");
                continue;
            }
            
            char guess = input.charAt(0);
            boolean found = false;

            for (int i = 0; i < secretWord.length(); i++) {
                if (secretWord.charAt(i) == guess) {
                    display[i] = guess;
                    found = true;
                }
            }

            if (found) {
                System.out.println("BRoo! " + guess + " You Guessed right.");
            } else {
                lives--;
                System.out.println("Sorry '" + guess + "' is not in the word");
            }

            boolean won = true;
            for (char c : display) {
                if (c == '_') {
                    won = false;
                    break;
                }
            }

            if (won) {
                System.out.println("\n--- You Did It! ---");
                System.out.println("You Won the Game, The correct word was: " + secretWord);
                break;
            }
        }

        if (lives <= 0) {
            printHangman(0); 
            System.out.println("\n--- GAME OVER ---");
            System.out.println("So sorry you are out of your lives now, The correct word is: " + secretWord);
        }

        scanner.close();
    }

    // --- YEH HAI NAYA METHOD JO HANGMAN PRINT KAREGA ---
    public static void printHangman(int lives) {
        System.out.println("  +---+");
        System.out.println("  |   |");
        
        // 1. Head (O) print karna
        if (lives < 6) {
            System.out.println("  O   |");
        } else {
            System.out.println("      |");
        }
        
        // 2. Body aur Dono Baazu (/, |, \) print karna
        if (lives == 4) {
            System.out.println(" /    |"); // Sirf ek baazu
        } else if (lives == 3) {
            System.out.println(" /|   |"); // Body aur ek baazu
        } else if (lives <= 2) {
            System.out.println(" /|\\  |"); // Dono baazu aur body (\\ isliye kyunke Java mein single \ escape character hota hai)
        } else {
            System.out.println("      |"); // Kuch nahi
        }
        
        // 3. Dono Tangein (/, \) print karna
        if (lives == 1) {
            System.out.println(" /    |"); // Sirf ek tang
        } else if (lives == 0) {
            System.out.println(" / \\  |"); // Dono tangein (Game over!)
        } else {
            System.out.println("      |"); // Kuch nahi
        }
        
        System.out.println("=========");
    }
}
