public class TicTacToe {
    // Data Fields
    private static final int SIZE = 3;
    private String[][] board;
    private String currentPlayer = "X";
    private int moves = 0;
    private boolean gameInProgress;

    // Constructor
    public TicTacToe() {
        board = new String[SIZE][SIZE];
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                board[x][y] = "";
            }
        }
        gameInProgress = true;
    }

    // Methods
    public String move(int x, int y) {
        String returnVal = "";

        board[x][y] = currentPlayer;
        moves++;
        // SIZE * 2 - 1 equals the smallest number of moves needed for a win
        if (moves >= (SIZE * 2 - 1)) {
            returnVal = checkWinState();
        }
        if (gameInProgress) {
            changePlayer();
        }
        return returnVal;
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
        if (currentPlayer == "X") {
            currentPlayer = "O";
        } else {
            currentPlayer = "X";
        }
    }

    public void reset() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                board[x][y] = "";
            }
        }
        moves = 0;
        currentPlayer = "X";
        gameInProgress = true;
    }

    public boolean getGameInProgress() {
        return gameInProgress;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }
}