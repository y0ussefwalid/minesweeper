import java.util.Scanner;

public class Player {
    private String name;
    private int n_moves;

    public Player(String name, int n_moves) {
        this.name = name;
        this.n_moves = n_moves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getN_moves() {
        return n_moves;
    }

    public void get_move(int[] move, Scanner scanner) {
        while (true) {
            System.out.print("Enter row and column separated by a space (ex: 2 3): ");
            String input = scanner.nextLine();
            String[] parts = input.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Invalid input. Enter exactly two numbers.");
                continue;
            }
            try {
                move[0] = Integer.parseInt(parts[0]);
                move[1] = Integer.parseInt(parts[1]);
                if (move[0] >= 0 && move[1] >= 0) { // Add board size limits if needed
                    break;
                } else {
                    System.out.println("Coordinates must be non-negative.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter two integers.");
            }
        }
    }

    public void increment() {
        n_moves++;
    }

    public void reset() {
        n_moves = 0;
    }
}
