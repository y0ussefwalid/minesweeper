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
            if (ref_board[r][c] != 'M' && !safe_board[r][c]) {
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
                if (ref_board[i][j] == 'M') {
                    continue;
                }
                int count = 0;
                for (int d = 0; d < 8; d++) {
                    int ni = i + dx[d];
                    int nj = j + dy[d];
                    if (ni >= 0 && ni < rows && nj >= 0 && nj < cols) {
                        if (ref_board[ni][nj] == 'M') {
                            count++;
                        }
                    }
                }
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
        System.out.print("    ");
        for (int j = 0; j < cols; j++) {
            System.out.printf("%2d  ", j);
        }
        System.out.println("\n   +" + "---+".repeat(cols));
        for (int i = 0; i < rows; i++) {
            System.out.printf("%2d |", i);
            for (int j = 0; j < cols; j++) {
                char cell = board[i][j];
                String color = Colors.RESET;
                switch (cell) {
                    case '-':
                        color = Colors.WHITE; break;
                    case 'M':
                    case 'F':
                        color = Colors.RED; break;
                    case '0':
                        color = Colors.GRAY; break;
                    case '1':
                        color = Colors.BLUE; break;
                    case '2':
                        color = Colors.GREEN; break;
                    case '3':
                        color = Colors.YELLOW; break;
                    default:
                        color = Colors.CYAN; break;
                }
                System.out.print(color + " " + cell + " " + Colors.RESET + "|");
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
        if (visited[x][y]) return false;
        visited[x][y] = true;
        board[x][y] = ref_board[x][y];
        if (ref_board[x][y] == '0') floodFill(x, y);
        return true;
    }

    public void floodFill(int x, int y) {
        ArrayList<Integer> Qx = new ArrayList<>();
        ArrayList<Integer> Qy = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};
        int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};
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
        if(board[x][y] != 'F') {
            flags++;
            board[x][y] = 'F';
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
