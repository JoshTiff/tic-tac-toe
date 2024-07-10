This project is a two-player version of tic-tac-toe using a GUI made in Java Swing.

I am developing this project to learn how to use Java Swing to create a GUI and how events and listeners can be used in a variety of ways. Additionally, I am utilizing class cohesion principles and effectively using interactions between classes to allow each class remain relatively simple while working

In the current version of the project, the GuiController class instantiates both the Menu and Board GUIs and handles interactions between the two by using listeners. The menu consists of a singleplayer button (still in development), a two-player button, and a quit button. The board displays the actual game of tic-tac-toe and updates as the game progresses. Users make moves by clicking open squares until someone wins or there are no more valid moves. Additionally, the board class creates a TicTacToe object that manages the game logic and detects when game-ending conditions are met. Once the game ends, users have the option to play again or return to the menu.

Features of the project include:
Intuitive GUI.
Easy navigation between menu and game.
Option for replayability.

This project can be run by downloading all of the Java files (Board, GuiController, Main, Menu, and TicTacToe) and running the main function in Main.java. This function creates a GuiController object, and the rest is handled by the other classes.

Some challenges and solutions from development include:
While working on the menu and board GUIs, I didn't initially know how I could effectively switch between the two and considered using both panels simultaneously and switching between them by making one visible and the other invisible. After further consideration, I determined that the CardLayout class met my goals in a much simpler manner and decided to use that to manage shifts between the two GUIs.
While developing the GUI, I originally had everything in one class. However, after creating both the menu and board GUIs in the same class and programming interactions between the two, I realized the class was overcomplicated and that it would make much more sense and improve class cohesion if I split the two GUIs into two separate classes and used a controller class to facilitate interactions between them. Although the GUI was functional as one class, splitting it into multiple classes will simplify future development.
After splitting the GUI into multiple classes, I needed to find a way for the menu and board objects to communicate action requests to the controller. Although a while loop could be used for the controller to constantly check a variable in the active class, this would be extremely inefficient and would use way too much processing power. This could be improved by using the sleep function to slow the loop, but this would add a delay following user input while remaining inefficient. I considered passing the controller object as a parameter in the menu and board constructors so they could call a notifier method in the controller class, but this would reverse the roles of the controller and the other GUI classes. After researching more options, I came across the Observer and Observable classes. Although those are now deprecated, I found something similar with PropertyChangeSupport and PropertyChangeListener. By setting up PropertyChangeSupports in my GUI classes and having the controller listen for changes, I found an effective way for my controller to be notified when it is needed to complete an action.

Future plans for the project:
1. Use the minimax algorithm to create a bot that always plays the best move.
2. Implement alpha-beta pruning in the minimax algorithm to improve bot efficiency.
3. Add bots with different difficulty levels that the player can choose from.
