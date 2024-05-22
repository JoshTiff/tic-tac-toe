import java.lang.StringBuilder;
import java.util.Scanner;

public class TicTacToe {
    // Data Fields
    private static final int SIZE = 3;
    private char[][] board;
    private char currentPlayer = 'X';
    private int moves = 0;
    private boolean gameInProgress;

    // Constructor
    public TicTacToe() {
        board = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = ' ';
            }
        }
        gameInProgress = true;
        System.out.println(printBoard());
        System.out.println("It is " + currentPlayer + "'s turn");
    }

    // Methods
    public void move(int x, int y) {
        x--;
        y--;
        if (x < 0 || x > SIZE - 1 || y < 0 || y > SIZE - 1) {
            System.out.println("Coordinates out of range, enter a different square.");
            return;
        }
        if (board[x][y] == ' ') {
            board[x][y] = currentPlayer;
            moves++;
            System.out.println(printBoard());
            // SIZE * 2 - 1 equals the smallest number of moves needed for a win
            if (moves >= (SIZE * 2 - 1)) {
                System.out.println(checkWinState());
            }
            if (gameInProgress) {
                changePlayer();
            }
            return;
        }
        else {
            System.out.println("This square is already taken, enter a different square.");
        }
    }

    private String checkWinState() {
        boolean winState = false;
        // Check for vertical win
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (board[x][y] != currentPlayer) {
                    break;
                }
                if (y == SIZE - 1) {
                    winState = true;
                }
            }
        }
        if (!winState) {
            // Check for horizontal win
            for (int y = 0; y < SIZE; y++) {
                for (int x = 0; x < SIZE; x++) {
                    if (board[x][y] != currentPlayer) {
                        break;
                    }
                    if (x == SIZE - 1) {
                        winState = true;
                    }
                }
            }
            if (!winState) {
                // Check for forward diagonal win
                for (int i = 0; i < SIZE; i++) {
                    if (board[i][i] != currentPlayer) {
                        break;
                    }
                    if (i == SIZE - 1) {
                        winState = true;
                    }
                }
                if (!winState) {
                    // Check for backward diagonal win
                    for (int i = 0; i < SIZE; i++) {
                        if (board[i][SIZE - 1 - i] != currentPlayer) {
                            break;
                        }
                        if (i == SIZE - 1) {
                            winState = true;
                        }
                    }
                }
            }
        }

        if (winState == true) {
            gameInProgress = false;
            return "Player " + currentPlayer + " wins!";
        } else if (moves == (SIZE * SIZE)) { //SIZE * SIZE is the number of spaces, or the maximum number of possible moves 
            gameInProgress = false;
            return "It's a tie!";
        } else {
            return "";
        }
    }

    private void changePlayer() {
        if (currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
        System.out.println("It is " + currentPlayer + "'s turn");
    }

    private String printBoard() {
        /* Board should appear as:
        3| | | |
        2| | | |
        1| | | |
         |1|2|3| */
        StringBuilder str = new StringBuilder();
        for (int y = SIZE - 1; y >= 0; y--) {
            str.append((y + 1) + "|");
            for (int x = 0; x < SIZE; x++) {
                str.append(board[x][y] + "|");
            }
            str.append("\n");
        }
        str.append(" |");
        for (int x = 1; x <= SIZE; x++) {
            str.append(x + "|");
        }
        return str.toString();
    }

    public boolean getGameInProgress() {
        return gameInProgress;
    }

    public static void main(String[] args) {
        boolean playAgain = false;
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to tic-tac-toe!");
        do {
            TicTacToe game = new TicTacToe();
            while (game.getGameInProgress()) {
                System.out.println("Enter the x and y coordinates of the square you would like to place a marker in.");
                int x = scan.nextInt();
                int y = scan.nextInt();
                game.move(x, y);
            }
            System.out.println("Would you like to play again? Enter \"Y\" for yes or \"N\" for no");
            String input = scan.next();
            if (input.equalsIgnoreCase("Y")) {
                playAgain = true;
            } else if (input.equalsIgnoreCase("N")) {
                playAgain = false;
            }
        } while (playAgain);
        scan.close();
    }
}