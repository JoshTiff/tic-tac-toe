import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board {
    // Data Fields
    private static final int SIZE = Game.getSize();
    
    private JPanel panel;
    private GameButton[][] gameButtons = new GameButton[SIZE][SIZE];
    private Game game;

    private PropertyChangeSupport support;
    private String action;

    // Constructor
    public Board() {
        panel = new JPanel(new GridLayout(SIZE, SIZE));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                gameButtons[x][y] = new GameButton(x, y);
                gameButtons[x][y].setFont(new Font("Arial", Font.PLAIN, 40));
                panel.add(gameButtons[x][y], x, y);
            }
        }

        // Creates PropertyChangeSupport for GuiController to listen to
        support = new PropertyChangeSupport(this);
        action = "";
    }

    // Methods
    public void initializeSingleGame(int diff, char player) {
        game = new SingleGame(diff, player);

        // If the first player is computer, computer makes a move
        if (!((SingleGame)game).playerIsActive()) {
            computerMove();
        }
    }
    
    public void initializeTwoPlayerGame() {
        game = new Game();
    }

    // Disables all buttons and allows the user to play again or return to the menu
    private void endGame() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                gameButtons[x][y].setEnabled(false);
            }
        }

        int input = JOptionPane.showConfirmDialog(panel, game.getEndGameMessage() + "\nPlay again?", "Tic-Tac-Toe", JOptionPane.YES_NO_OPTION);
        if (input != JOptionPane.YES_OPTION) {
            setAction("openMenu");
        }
        reset();
    }

    public void computerMove() {
        // Verifies that it is a SingleGame to avoid errors when casting to SingleGame
        if (game instanceof SingleGame) {

            checkActivePlayer(); // Disables buttons while computer calculates move
            char currentPlayer = game.getCurrentPlayer();
            ((SingleGame)game).computerMove();
            gameButtons[((SingleGame)game).getXCoordinate()][((SingleGame)game).getYCoordinate()].setText(String.valueOf(currentPlayer));

            // If move causes a win the game ends. Otherwise buttons are re-enabled
            if (!game.getGameInProgress()) {
                endGame();
            } else {
                checkActivePlayer(); // Enables unused buttons
            }
        }
    }

    // Resets the board for the next game
    private void reset() {
        // Resets the text of and enables each button
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                gameButtons[x][y].setText("");
                gameButtons[x][y].setEnabled(true);
            }
        }

        game.reset(); // Resets the Game object for a new game

        // Makes a computer move if the computer is going first and the player chooses to play another game
        if (game instanceof SingleGame) {
            // Checking that action is not set to openMenu stops a move from being played when the player starts a new game from the menu
            if (!((SingleGame)game).playerIsActive() && !action.equals("openMenu")) {
                computerMove();
            }
        }
    }

    // Checks which player is active
    // Disables buttons when it is the computer's turn
    // Re-enables buttons when it is the player's turn
    private void checkActivePlayer() {
        // Verifies that it is a SingleGame to avoid errors when casting to SingleGame
        if (game instanceof SingleGame) {

            // Enables unused buttons if it is the player's turn
            if (((SingleGame)game).playerIsActive()) {
                for (int x = 0; x < SIZE; x++) {
                    for (int y = 0; y < SIZE; y++) {
                        if (gameButtons[x][y].getText().equals("")) {
                            gameButtons[x][y].setEnabled(true);
                        }
                    }
                }
            } else {
                
                // Disables buttons if it is the computer's turn
                for (int x = 0; x < SIZE; x++) {
                    for (int y = 0; y < SIZE; y++) {
                        gameButtons[x][y].setEnabled(false);
                    }
                }
            }
        }
        
    }

    // Changes the action variable and prompts the controller class to fulfill the requested action
    public void setAction(String newAction) {
        String old = action;
        action = newAction;
        support.firePropertyChange("action", old, action);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getAction() {
        return action;
    }

    private class GameButton extends JButton {
        private int x;
        private int y;
        public GameButton(int x, int y) {
            super(new ButtonAction(""));
            this.x = x;
            this.y = y;
        }
    }

    private class ButtonAction extends AbstractAction {
        public ButtonAction(String name) {
           super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            GameButton button = (GameButton) e.getSource();
            button.setText(String.valueOf(game.getCurrentPlayer()));
            button.setEnabled(false);
            
            game.move(button.x, button.y);

            if (!game.getGameInProgress()) {
                endGame();
            } else if (game instanceof SingleGame) {
                if (!((SingleGame)game).playerIsActive()) {
                    computerMove();
                }
            }
        }
    }
}
