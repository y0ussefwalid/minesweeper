import java.util.ArrayList;
import java.util.Random;

public class Board {
//    int difficulty = 1;
    private int rows;
    private int cols;
    private char[][] board;
    private char[][] ref_board;
    private boolean[][] visited;
    private boolean[][] safe_board;
    private int mines;
    private int flags;
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String GRAY = "\u001B[90m";

    //----------methods----------//
    public Board(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.flags = 0;
        board = new char[rows][cols];
        ref_board = new char[rows][cols];
        visited = new boolean[rows][cols];
        safe_board = new boolean[rows][cols];
    }

    public void initialize(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                board[i][j] = '-';
                visited[i][j] = false;
            }
        }
    }

    public boolean first_move(int x, int y) {
        if (x < 0 || x >= rows || y < 0 || y >= cols) return false;
        ref_board[x][y] = '0';
        safe_board[x][y] = true;
        // Mark the safe zone (first move and adjacent cells)
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && nx < rows && ny >= 0 && ny < cols) {
                    safe_board[nx][ny] = true; // Mark as visited (safe)
                }
            }
        }
        setMines();
        calculateNumbers();
        updateBoard(x, y);
        return true;
    }

    public void setMines() {
        Random rand = new Random();
        int placedMines = 0;
        while (placedMines < mines) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (ref_board[r][c] != 'M' && !safe_board[r][c]) { // Ensure no duplicate mines
                ref_board[r][c] = 'M';
                placedMines++;
            }
        }
    }

    public void calculateNumbers() {
        // Directions for adjacent cells
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // If the cell is a mine, continue
                if (ref_board[i][j] == 'M') {
                    continue;
                }
                int count = 0;
                // Check all 8 neighboring cells
                for (int d = 0; d < 8; d++) {
                    int ni = i + dx[d];
                    int nj = j + dy[d];
                    // Ensure indices are within bounds
                    if (ni >= 0 && ni < rows && nj >= 0 && nj < cols) {
                        if (ref_board[ni][nj] == 'M') {
                            count++;
                        }
                    }
                }
                // Set the cell value to the count if there are adjacent mines
                if (count > 0) {
                    ref_board[i][j] = (char) ('0' + count);
                }
                else {
                    ref_board[i][j] = '0'; // Empty cell
                }
            }
        }
    }
    public void displayBoard() {
        // Print column indices with spacing
        System.out.print("    ");
        for (int col = 0; col < cols; col++) {
            System.out.printf("%2d  ", col);
        }
        System.out.println("\n   +" + "---+".repeat(cols));

        // Print board rows with indices and grid
        for (int row = 0; row < rows; row++) {
            System.out.printf("%2d |", row);
            for (int col = 0; col < cols; col++) {
                char cell = board[row][col];
                String color;
                // Choose color based on cell type
                if (cell == '-') {
                    color = WHITE;
                }
                else if (cell == 'M' || cell == 'f') {
                    color = RED;
                }
                else if (cell == '0') {
                    color = GRAY;
                }
                else if (cell == '1') {
                    color = BLUE;
                }
                else if (cell == '2') {
                    color = GREEN;
                }
                else if (cell == '3') {
                    color = YELLOW;
                }
                else {
                    color = CYAN;
                }

                System.out.print(color + " " + cell + " " + RESET + "|");
            }
            System.out.println("\n   +" + "---+".repeat(cols));
        }
    }


    public void stats(){
        System.out.printf("Mines: %d\n", mines);
        System.out.printf("Flags: %d\n", flags);
    }
    public void displayBoard2() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print(ref_board[row][col] + " ");
            }
            System.out.println();
        }
    }

    public boolean updateBoard(int x, int y) {
        if (x < 0 || x >= rows || y < 0 || y >= cols) return false;
        // If already visited, return false
        if (visited[x][y]) return false;
        visited[x][y] = true;
        board[x][y] = ref_board[x][y];
        // If it's an empty cell ('0'), trigger flood fill
        if (ref_board[x][y] == '0') floodFill(x, y);
        return true;
    }

    public void floodFill(int x, int y) {
        ArrayList<Integer> Qx = new ArrayList<>();
        ArrayList<Integer> Qy = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};
        int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};
        // Enqueue starting position
        Qx.add(x);
        Qy.add(y);
        visited[x][y] = true;
        board[x][y] = ref_board[x][y];
        int index = 0;
        while (index < Qx.size()) {
            int curX = Qx.get(index);
            int curY = Qy.get(index);
            index++;
            for (int d = 0; d < 8; d++) {
                int newX = curX + dx[d];
                int newY = curY + dy[d];
                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols) {
                    if (!visited[newX][newY] && ref_board[newX][newY] != 'M') {
                        board[newX][newY] = ref_board[newX][newY];
                        visited[newX][newY] = true;
                        if (ref_board[newX][newY] == '0') {
                            Qx.add(newX);
                            Qy.add(newY);
                        }
                    }
                }
            }
        }
    }

    public void toggleFlag(int x, int y) {
        if(board[x][y] != 'f') {
            flags++;
            board[x][y] = 'f';
            visited[x][y] = true;
        }
        else {
            flags--;
            board[x][y] = '?';
            visited[x][y] = false;
        }
    }

    public boolean checkWin() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (ref_board[i][j] != 'M' && !visited[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkLose(int  x, int y) {
        if(board[x][y] == 'M') {
            return true;
        }
        return false;
    }

    public void reveal(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (ref_board[i][j] == 'M') {
                    board[i][j] = 'M';
                }
            }
        }
    }
}
