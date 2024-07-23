public class Game {
    // Data Fields
    private static final int SIZE = 4;
    private char[][] board;
    private char currentPlayer;
    private int moves;
    private boolean gameInProgress;
    private String endGameMessage;

    // Constructor
    public Game() {
        currentPlayer = 'X';
        moves = 0;
        board = new char[SIZE][SIZE];
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                board[x][y] = ' ';
            }
        }
        gameInProgress = true;
    }

    // Methods
    // Places the current player's marker on the board at coordinates x, y
    public void move(int x, int y) {
        // Prevents a move from being made on a space that is already taken or is out of range
        if (board[x][y] != ' ' || x > SIZE - 1 || y > SIZE - 1 || x < 0 || y < 0) {
            System.out.println("Invalid move!");
            return;
        }

        moves++;
        board[x][y] = currentPlayer;
        if (moves >= (SIZE * 2 - 1)) { // SIZE * 2 - 1 equals the smallest number of moves needed for a win
            checkEndGame();
        }
        if (gameInProgress) {
            changePlayer();
        }
    }

    private void changePlayer() {
        if (currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
    }

    // Checks if game over conditions are met and ends the game if conditions are met
    public void checkEndGame() {
        if (checkWinState() == true) {
            gameInProgress = false;
            endGameMessage = "Player " + currentPlayer + " wins!";
        } else if (moves == (SIZE * SIZE)) { // SIZE * SIZE is the number of spaces, or the maximum number of possible moves 
            gameInProgress = false;
            endGameMessage = "It's a tie!";
        }
    }

    // Checks if the conditions for a win have been met and stops searching after the first win is found
    // Default method for use after a move is made
    public boolean checkWinState() {
        return checkWinState(board, currentPlayer);
    }

    // Parameterized for use in checking possible moves in singleplayer games
    public boolean checkWinState(char[][] board, char currentPlayer) {
        // Check for vertical win
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (board[x][y] != currentPlayer) {
                    break;
                }
                if (y == SIZE - 1) {
                    return true;
                }
            }
        }
        
        // Check for horizontal win
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                if (board[x][y] != currentPlayer) {
                    break;
                }
                if (x == SIZE - 1) {
                    return true;
                }
            }
        }
        
        // Check for forward diagonal win
        for (int i = 0; i < SIZE; i++) {
            if (board[i][i] != currentPlayer) {
                break;
            }
            if (i == SIZE - 1) {
                return true;
            }
        }
        
        // Check for backward diagonal win
        for (int i = 0; i < SIZE; i++) {
            if (board[i][SIZE - 1 - i] != currentPlayer) {
                break;
            }
            if (i == SIZE - 1) {
                return true;
            }
        }
        return false;
    }

    // Resets the game board and all variables for a new game
    public void reset() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                board[x][y] = ' ';
            }
        }
        moves = 0;
        currentPlayer = 'X';
        gameInProgress = true;
    }

    public static int getSize() {
        return SIZE;
    }

    public char[][] getBoard() {
        return board;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public int getMoves() {
        return moves;
    }

    public boolean getGameInProgress() {
        return gameInProgress;
    }

    public String getEndGameMessage() {
        return endGameMessage;
    }
}