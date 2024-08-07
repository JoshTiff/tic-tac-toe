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

public class Menu {
    // Data Fields
    private JPanel panel;
    private JButton singleButton;
    private JButton twoButton;
    private JButton quitButton;

    private PropertyChangeSupport support;

    private String action;
    private int difficulty;
    private char playerSelection;

    // Constructor
    public Menu() {
        panel = new JPanel(new GridLayout(3, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        singleButton = new JButton(new ButtonAction("1 Player"));
        singleButton.setFont(new Font("Arial", Font.PLAIN, 25));
        singleButton.setActionCommand("onePlayer");
        panel.add(singleButton);

        twoButton = new JButton(new ButtonAction("2 Player"));
        twoButton.setFont(new Font("Arial", Font.PLAIN, 25));
        twoButton.setActionCommand("twoPlayer");
        panel.add(twoButton);

        quitButton = new JButton(new ButtonAction("Quit"));
        quitButton.setFont(new Font("Arial", Font.PLAIN, 25));
        quitButton.setActionCommand("quit");
        panel.add(quitButton);

        // Creates PropertyChangeSupport for GuiController to listen to
        support = new PropertyChangeSupport(this);
        action = "";
    }

    // Methods
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

    public int getDifficulty() {
        return difficulty;
    }

    public char getPlayerSelection() {
        return playerSelection;
    }

    private class ButtonAction extends AbstractAction {
        public ButtonAction(String name) {
           super(name);
        }
  
        // Determines what each button does based on its actionCommand
        // Actions are passed to controller class for fulfillment
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            switch (button.getActionCommand()) {
                case "onePlayer":
                    // Prompts user to select a difficulty and if they want to play as X or O
                    String[] diffOptions = {"Easy", "Medium", "Hard", "Impossible", "Cancel"};
                    difficulty = JOptionPane.showOptionDialog(panel, "Select a difficulty:", "Tic-Tac-Toe", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, diffOptions, diffOptions[0]);
                        if (difficulty != 4) {
                            String[] playerOptions = {"X", "O"};
                            playerSelection = playerOptions[JOptionPane.showOptionDialog(panel, "Who would you like to play as?", "Tic-Tac-Toe", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, playerOptions, playerOptions[0])].charAt(0);
                            setAction("onePlayerActive");
                        }
                    break;

                case "twoPlayer":
                    setAction("twoPlayerActive");
                    break;

                case "quit":
                    // Displays confirmation message to verify user wants to quit
                    int input = JOptionPane.showConfirmDialog(panel, "Are you sure you want to quit?", "Tic-Tac-Toe", JOptionPane.YES_NO_OPTION);
                    if (input == JOptionPane.YES_OPTION) {
                        setAction("quitting");
                    }
                    break;    
                
            }
        }
    }
}
