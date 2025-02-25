import java.util.Scanner;
public class minesweeper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userName = null;
        Player player = null;
        Gamemanager gm = null;
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Enter Player Name");
            System.out.println("2. Play Game");
            System.out.println("3. View Rules");
            System.out.println("4. Change Player Name");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                case "4":
                    userName = getPlayerName(scanner);
                    player = new Player(userName, 0);
                    System.out.println("\nHello, " + userName + "! Welcome to Minesweeper.");
                    System.out.println("Your goal is to clear the board without triggering any mines!");
                    System.out.println("Good luck, and have fun!\n");
                    break;
                case "2":
                    if (player == null) {
                        System.out.println("Please enter a player name first (Option 1 or 4).\n");
                    }
                    else {
                        gm = new Gamemanager(player);
                        gm.startGame(scanner);
                    }
                    break;
                case "3":
                    displayRules();
                    break;
                case "5":
                    System.out.println("Thanks for playing Minesweeper!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, 3, 4, or 5.");
            }
        }
        scanner.close();
    }

    private static String getPlayerName(Scanner scanner) {
        System.out.print("Enter your name: ");
        return scanner.nextLine();
    }

    private static void displayRules() {
        System.out.println("\nMinesweeper Rules:");
        System.out.println("- The goal is to clear the board without hitting a mine.");
        System.out.println("- Numbers on the board indicate how many mines are adjacent.");
        System.out.println("- Use logic to determine where mines are and mark them.");
        System.out.println("- Enjoy and be careful!\n");
        System.out.println("\nGuide:");
        System.out.println(Colors.WHITE + "'-' = Unrevealed" + Colors.RESET);
        System.out.println(Colors.RED + "'M' = Mine" + Colors.RESET);
        System.out.println(Colors.GRAY + "'0' = Empty Revealed Cell" + Colors.RESET);
        System.out.println(Colors.BLUE + "'1' = 1 Adjacent Mine" + Colors.RESET);
        System.out.println(Colors.GREEN + "'2' = 2 Adjacent Mines" + Colors.RESET);
        System.out.println(Colors.YELLOW + "'3' = 3 Adjacent Mines" + Colors.RESET);
        System.out.println(Colors.CYAN + "'4+' = 4+ Adjacent Mines" + Colors.RESET);
    }

}
