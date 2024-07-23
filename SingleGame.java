import java.util.Random;

public class SingleGame extends Game {
    // Data Fields
    private int difficulty;
    private char player;
    private char computer;
    private Coordinates selectedMove;

    private Random rand = new Random(System.currentTimeMillis());

    public SingleGame(int diff, char play) {
        super();
        difficulty = diff;
        player = play;
        if (player == 'X') {
            computer = 'O';
        } else {
            computer = 'X';
        }
    }

    // Determines if the computer's move will be a random or best move based on difficulty and calls the corresponding method
    public void computerMove() {    
        // Easy will always play a random move, medium will play the best move 1/3 of the time, hard will play the best move 2/3 of the time, impossible will always play the best move
        if (difficulty > rand.nextInt(3)) { 
            findBestMove();
        } else {
            findRandomMove();
        }
        move(selectedMove.x, selectedMove.y);
    }

    // Finds the best move by using the minimax algorithm
    private void findBestMove() {
        int best = Integer.MIN_VALUE; 
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                // Tests each open space in the minimax algorithm to determine the best move
                if (getBoard()[x][y] == ' ') {
                    getBoard()[x][y] = computer;
                    int moveVal = minimax(1, getMoves() + 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    getBoard()[x][y] = ' ';

                    // If a new best move is found, its coordinates are saved
                    if (moveVal > best) {
                        selectedMove = new Coordinates(x, y);
                        best = moveVal;
                    }
                }
            }
        }
    }

    // Algorithm that uses recursion to determine the best possible move if the opposing player also makes the best possible move
    private int minimax(int depth, int moves, boolean isMax, int alpha, int beta) {
        // Checks if the last move causes a win
        int score = getScore(getBoard(), depth, moves, isMax);

        // Base case if a win occurs or if all spaces are filled
        if (score != 0) {
            return score;
        } else if (moves == getSize() * getSize() || depth > 9) { // depth > 9 is included to prevent extremely long calculations on larger boards
            return 0;
        }

        int best;
        
        // Finds best move for maximizing player
        if (isMax) {

            best = Integer.MIN_VALUE;

            for (int x = 0; x < getSize(); x++) {
                for (int y = 0; y < getSize(); y++) {
                    // Checks all possible moves and finds the best move based on all possible continuations
                    if (getBoard()[x][y] == ' ') {
                        getBoard()[x][y] = computer;
                        best = Math.max(best, minimax(depth + 1, moves + 1, false, alpha, beta));
                        alpha = Math.max(alpha, best);
                        getBoard()[x][y] = ' ';

                        // Stops checking possibilities if a better move has been found
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }

        } else {
            // Finds best move for minimizing player

            best = Integer.MAX_VALUE;

            for (int x = 0; x < getSize(); x++) {
                for (int y = 0; y < getSize(); y++) {
                    // Checks all possible moves and finds the best move based on all possible continutations
                    if (getBoard()[x][y] == ' ') {
                        getBoard()[x][y] = player;
                        best = Math.min(best, minimax(depth + 1, moves + 1, true, alpha, beta));
                        beta = Math.min(beta, best);
                        getBoard()[x][y] = ' ';

                        // Stops checking possibilities if a better move has been found
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
        }
        return best;
    }

    // Calculates the score of a move for minimax algorithm
    private int getScore(char[][] board, int depth, int moves, boolean isMax) {

        // Checks who made the last move for checkWinState parameters
        char lastPlayer;
        if (isMax) {
            lastPlayer = player;
        } else {
            lastPlayer = computer;
        }

        if (checkWinState(getBoard(), lastPlayer)) {
            int score;
            // Including depth prioritizes faster wins
            // Depth cannot be more than the number of moves + 1 so there is no chance of a sign change
            if (lastPlayer == computer) {
                score = (getSize() * getSize() + 1) - depth; // Returns a positive value to show a win
            } else {
                score = depth - (getSize() * getSize() + 1); // Returns a negative value to show a loss
            }
            return score;
        } 
        return 0;
    }

    // Checks all possible moves and selects a random one
    private void findRandomMove() {
        Coordinates[] availableSpaces = new Coordinates[getSize() * getSize() - getMoves()];
        int arrayLocation = 0;

        // Finds all possible moves and stores them in an array
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                if (getBoard()[x][y] == ' ') {
                    availableSpaces[arrayLocation] = new Coordinates(x, y);
                    arrayLocation++;
                }
            }
        }
        
        selectedMove = availableSpaces[rand.nextInt(availableSpaces.length)];
    }

    // Checks if it is currently the player's turn
    public boolean playerIsActive() {
        return (getCurrentPlayer() == player);
    }

    public int getXCoordinate() {
        return selectedMove.x;
    }

    public int getYCoordinate() {
        return selectedMove.y;
    }

    private class Coordinates {
        private int x;
        private int y;
        Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
