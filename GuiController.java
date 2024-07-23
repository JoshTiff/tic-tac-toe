import java.awt.CardLayout;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GuiController implements PropertyChangeListener {
    // Data Fields
    private JFrame frame;
    private JPanel visiblePanel;
    private CardLayout cardLayout;
    private Menu menu;
    private Board board;
    private String currentPanel;

    // Constructor
    public GuiController() {
        menu = new Menu();
        board = new Board();

        visiblePanel = new JPanel(new CardLayout());
        visiblePanel.add(menu.getPanel(), "menu");
        visiblePanel.add(board.getPanel(), "board");

        cardLayout = (CardLayout)visiblePanel.getLayout();

        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(visiblePanel);
        frame.setSize(400, 400);
        frame.setVisible(true);

        openMenu();
    }

    // Methods
    private void openMenu() {
        menu.addPropertyChangeListener(this);
        currentPanel = "menu";
        menu.setAction("");
        cardLayout.show(visiblePanel, currentPanel);
    }

    private void openBoard() {
        currentPanel = "board";
        board.setAction("");
        menu.removePropertyChangeListener(this); // This line is included here because the menu propertyChangeListener will always be initialized when this function is called
        board.addPropertyChangeListener(this);
        cardLayout.show(visiblePanel, currentPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (currentPanel.equals("menu")) {
            switch (menu.getAction()) {
                case "onePlayerActive":
                    board.initializeSingleGame(menu.getDifficulty(), menu.getPlayerSelection());
                    openBoard();
                    break;
                
                case "twoPlayerActive":
                    board.initializeTwoPlayerGame();
                    openBoard();
                    break;
    
                case "quitting":
                    System.exit(0);
                    break;
            }
        } else {
            if (board.getAction().equals("openMenu")) {
                board.removePropertyChangeListener(this); // This line is included here instead of in openMenu so it is not called before the board propertyChangeListener is initialized
                openMenu();
            }
            
        }
    }
}