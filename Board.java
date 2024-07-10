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
    private static final int SIZE = 3;
    private JPanel panel;
    private JButton[][] gameButtons = new JButton[SIZE][SIZE];
    private TicTacToe game;

    private PropertyChangeSupport support;

    private String action;

    // Constructor
    public Board() {
        panel = new JPanel(new GridLayout(3, 3));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                gameButtons[x][y] = new JButton(new ButtonAction(""));
                gameButtons[x][y].setFont(new Font("Arial", Font.PLAIN, 40));
                panel.add(gameButtons[x][y]);
            }
        }

        support = new PropertyChangeSupport(this);
        action = "";
    }

    // Methods
    public void initializeTwoPlayerGame() {
        game = new TicTacToe();
    }

    private void reset() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                gameButtons[x][y].setText("");
                gameButtons[x][y].setEnabled(true);
            }
        }
        game.reset();
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

    private class ButtonAction extends AbstractAction {
        public ButtonAction(String name) {
           super(name);
        }
  
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            button.setText(game.getCurrentPlayer());
            button.setEnabled(false);
            
            // Dividing by 100 gives the correct coordinates for the TicTacToe class's board
            String tmp = game.move(button.getX() / 100, button.getY() / 100);

            if (!game.getGameInProgress()) {
                for (int x = 0; x < SIZE; x++) {
                    for (int y = 0; y < SIZE; y++) {
                        gameButtons[x][y].setEnabled(false);
                    }
                }
                int input = JOptionPane.showConfirmDialog(panel, tmp + "\nPlay again?", "Tic-Tac-Toe", JOptionPane.YES_NO_OPTION);
                reset();
                if (input == JOptionPane.NO_OPTION) {
                    setAction("openMenu");
                }
            }
        }
    }
}
