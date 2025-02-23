import java.util.Scanner;
public class Gamemanager {
    Board board;
    Player player;
    long time = 0;
    boolean endgame = false;
    int difficulty = 1;
    //----------methods----------//
    public Gamemanager(Player player) {
        this.player = player;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Minesweeper!");
        System.out.println("Choose difficulty level:");
        System.out.println("1. Easy (5x5, 5 mines)");
        System.out.println("2. Medium (10x10, 15 mines)");
        System.out.println("3. Hard (15x15, 30 mines)");
        int choice = 0;
        while (true) {
            System.out.print("Enter your choice (1-3): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) break;
            }
            else {
                scanner.next();
            }
            System.out.println("Invalid input. Please enter a number between 1 and 3.");
        }
        int rows, cols,mines;
        rows = cols = mines = 0;
        switch (choice) {
            case 1:
                rows = 5;
                cols = 5;
                mines = 5;
                break;
            case 2:
                rows = 10;
                cols = 10;
                mines = 15;
                break;
            case 3:
                rows = 15;
                cols = 15;
                mines = 30;
                break;
        }
        board = new Board(rows,cols,mines);
        System.out.println("Starting game with a " + rows + "x" + cols + " board and " + mines + " mines.");
        board.initialize();
        int x,y;
        x = y = 0;
        board.displayBoard();
        player.get_move(x,y);
        while (!board.first_move(x,y)) {
            System.out.println("Invalid input");
            player.get_move(x,y);
        }
    }

//    public void endGame() {
//
//    }
    public void playGame() {

    }
}
