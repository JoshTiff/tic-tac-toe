# Tic-Tac-Toe Using Minimax and Alpha Beta Pruning

This project is the game of tic-tac-toe with both singleplayer and two-player modes using a GUI made in Java Swing.

I developed this project to learn to use Java Swing to create GUIs and how events and listeners can be used in a variety of ways. Additionally, I utilized class cohesion principles and effectively managed interactions between classes to allow each class remain relatively simple while working together to complete more complicated tasks. I also worked on some optimizations to make move calculations more efficient.


In this project, the GuiController class instantiates both the Menu and Board GUIs and handles interactions between the two by using listeners. The menu consists of a singleplayer button, a two-player button, and a quit button. The board displays the game of tic-tac-toe and updates as the game progresses. The board class also initializes a Game or SingleGame object to handle game logic. In singleplayer mode, users select a difficulty that determines how often the computer will play random moves and how often it will play best moves, calculated by a minimax algorithm with alpha-beta pruning for efficiency. They also choose to play as X or O, which determines whether they will go first or second. In two-player mode, users make moves by clicking open squares until someone wins or there are no more valid moves. Once the game ends, users have the option to play again or return to the menu.

## Video Demos:
3-by-3 Demo: https://youtu.be/QFDI2HDPbGo
<br/>4-by-4 Demo: https://youtu.be/uE9wHw-BonM

## Features of the project include:
Minimax algorithm with alpha-beta pruning for fast and effective move calculations: the minimax algorithm allows for the best move to be found from any board. Using alpha beta pruning improves efficiency by not checking a move to completion as soon as it is determined to be worse than an already calculated move. In testing of finding the first move on a 3-by-3 board with the user playing as O, alpha-beta pruning took an average of 14.9 ms vs the original calculation time of 42.2 ms, making it much more efficient.

Dynamic board size: By changing the static final variable SIZE in the Game class, the rest of the code is set up to allow for any size of board. Despite optimizations, a board size of 4-5 has some delays in move generation and anything larger takes too long to be playable.

Intuitive GUI: The GUI is simple and its functions are easy for users to understand.

Simple navigation between the menu and the game board: After each game, users are able to return to the menu.

Replayability and ability to change game modes: The program is set up to be played as many times as a user wants. After a game is finished, they can either play again or return to the menu, where they are able to switch between singleplayer and multiplayer or to switch difficulties.

## How to install and run the project:
This project can be run by downloading all of the Java files (Board, Game, GuiController, Main, Menu, and SingleGame) and running the main function in Main.java. This function creates a GuiController object and the rest is handled by the other classes.

## Some challenges and solutions from development include:
While working on the menu and board GUIs, I initially didn't know how I could switch between the two and considered using both panels simultaneously and switching between them by making one visible and the other invisible. After looking into other options, I determined that the CardLayout class met my goals in a much simpler way and decided to use that to manage switching between the two GUIs.

While developing the GUI, I originally had everything in one class. However, after creating both the menu and board GUIs in the same class and programming interactions between the two, I realized the class was overcomplicated and that it would make much more sense and improve class cohesion if I split the two GUIs into two separate classes and used a controller class to facilitate interactions between them. Although the GUI was functional as one class, splitting it into multiple classes will simplify future development.

After splitting the GUI into multiple classes, I needed to find a way for the menu and board objects to communicate action requests to the controller. Although a while loop could be used for the controller to constantly check a variable in the active GUI class, this would be extremely inefficient and would use way too much processing power. This could be improved by using the sleep function to slow the loop, but this would still be relatively inefficient. I considered passing the controller object as a parameter in the menu and board constructors so they could call a notifier method in the controller class, but this would reverse the roles of the controller and the other GUI classes. After researching more options, I came across the Observer and Observable classes. Although those are now deprecated, I found something similar with PropertyChangeSupport and PropertyChangeListener. By setting up PropertyChangeSupports in my GUI classes and having the controller listen for changes, I found an effective way for my controller to be notified when it needs to complete an action.

When developing singleplayer mode, the computer was initially making moves that didn't make sense and were sometimes impossible. I was able to trace this back to the initialization of the gameButtons in the Board class and the problem was occuring because I was using the JPanel add method without giving coordinates, so the x and y variables were opposite between the array in the Board class and the array in the Game class. I found a different constructor that included coordinates for the gameButtons and used those to make the x and y line up between my classes.

In boards larger than 3-by-3, it was nearly unplayable when the computer was searching for the best possible move, even after implementing alpha-beta pruning. This was because the amount of positions that the program had to search were growing exponentially with the board size. To help handle this, I decided to limit search depth to 9 in the minimax algorithm. This made no change to 3-by-3 boards, which are typically used for tic-tac-toe. In larger boards, this could potentially make the move calculator less effective because it would not be able to check every move to completion. However, it would make the game playable in larger boards, making it a necessary change.
