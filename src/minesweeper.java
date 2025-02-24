import java.util.Scanner;

public class minesweeper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();
        System.out.println("\nHello, " + userName + "! Welcome to Minesweeper.");
        System.out.println("Your goal is to clear the board without triggering any mines!");
        System.out.println("Good luck, and have fun!\n");
        Player player = new Player(userName,0);
        Gamemanager gm = new Gamemanager(player);
        gm.startGame(scanner);
        scanner.close();
    }
}
