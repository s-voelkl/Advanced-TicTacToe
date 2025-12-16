# Aufgabe 1: Tic-Tac-Toe Basic

Enhanced TicTacToe game implementation in Java with command-line interface, supporting N×M board sizes, K players, and J tiles in a row to win. Made by Simon Völkl in 2025.

## Requirements for the Build and Runtime Environment

This project was tested with the following configurations. I recommend, using the same setup:

- OS: Linux (per SSH) with access by Windows_NT x64 10.0.26100.
- IDE: VS Code 1.107.0 (user setup).
- Encoding: UTF-8.
- Java JDK: JavaSE-11.
- Folder: Go into the project repository. You should see the folders _.vscode_, _src_, the testing library and this ReadMe.
- VS Code Settings: Ensure, that the file _.vscode/settings.json_ has the parameter `java.project.sourcePaths": ["src/main/java", "src/test/java"]` with the source code locations set. You may also change the parameter `java.project.referencedLibraries` to fit your folder structure.
- Start the project, by clicking the run button. The game should start.

## Documentation

### General Task

This project implements a **Tic-Tac-Toe game** with command-line interface. The classic version of Tic-Tac-Toe is played on a 3x3 grid where two players take turns placing their marks (typically X and O). The first player to get three of their marks in a row (horizontally, vertically, or diagonally) wins the game. If all cells are filled without a winner, the game ends in a draw.

The program provides:

- Interactive gameplay via command-line input
- Visual board representation with ANSI color codes
- Input validation and error handling
- Win condition checking after each move
- Draw detection when the board is full

### Extension

This implementation extends the classic Tic-Tac-Toe game with configurable parameters:

- **N×M Board**: The board size is flexible and not limited to 3×3. You can play on rectangular grids of any dimension (e.g., 4×5, 6×6, etc.).
- **K Players**: The game supports more than two players, each with their own unique symbol. Players take turns in a round-robin fashion.
- **J Tiles in a Row**: The win condition is customizable. Instead of always requiring 3 marks in a row, you can specify a minimum number J of consecutive marks needed to win. This allows for variations like "4 in a row" or "5 in a row".

These extensions make the game highly flexible and allow for different difficulty levels and game variants beyond traditional Tic-Tac-Toe.

### Object-Oriented Programming Structure

The project follows object-oriented design principles with clear separation of concerns:

**Core Classes:**

- **Board**: Manages the game board state with configurable dimensions (N×M grid). Handles tile management using a one-dimensional array in row-major order, coordinate-to-position conversions, and tracking of empty tiles. Implements the `IBoard` interface for abstraction.
- **IBoard**: Interface defining the contract for board operations, enabling loose coupling and easier testing through dependency injection.
- **Figure**: Represents a player's game symbol with uniqueness enforcement. Maintains a registry of used symbols and provides utility methods for symbol management, including automatic disposal and free symbol retrieval from a predefined pool.
- **Player** (abstract): Base class for all player types, managing figure ownership, turn counting, and move execution. Enforces the Template Method pattern through the abstract `actMove()` method.
- **Human**: Concrete player implementation that accepts moves via command-line input through `CmdInput`. Maintains a count of human player instances.
- **Bot**: Concrete player implementation that makes moves automatically by randomly selecting from available empty tiles. Maintains a count of bot instances.
- **PlayerFactory**: Factory class implementing the Factory Pattern to create player instances. Provides static methods for creating Human and Bot players with automatic symbol assignment and naming conventions. Supports both interactive (user-prompted) and non-interactive player creation.
- **Game**: Orchestrates the complete game flow including player management, turn order (round-robin), move execution, win/draw condition checking, and game state transitions. Checks win conditions efficiently after each move by examining only affected rows, columns, and diagonals.
- **GameCondition** (enum): Represents possible game states (WIN, DRAW, NOT_FINISHED).
- **PlayerType** (enum): Represents player types (Human, Bot) for factory creation.
- **CmdInput**: Handles all command-line input operations with validation, including player configuration, move coordinates, and game flow control.
- **CmdOutput**: Manages all console output with ANSI color codes for enhanced visualization, including board display, player information, and game status messages.

**Design Patterns Used:**

- **Factory Pattern**: The `PlayerFactory` class encapsulates player object creation, providing a centralized and flexible way to instantiate different player types with appropriate configurations.
- **Template Method Pattern**: The `Player` abstract class defines the `move()` workflow while delegating the actual move logic to subclasses via `actMove()`.
- **Strategy Pattern**: Different player types (Human, Bot) implement distinct move strategies through polymorphism.
- **Interface Segregation**: The `IBoard` interface abstracts board operations, allowing for potential alternative board implementations.
- **Encapsulation**: Each class manages its own state and exposes only necessary public methods.
- **Single Responsibility**: Each class has a well-defined purpose (e.g., Board handles board logic, CmdInput/CmdOutput handles I/O operations).
- **Enum Types**: Used for representing game states (`GameCondition`) and player types (`PlayerType`), providing type safety and clarity.

**Testing Coverage:**

The project includes comprehensive unit tests with approximately **68%** code coverage.

The modular design allows for easy testing, maintenance, and future extensions of the game logic.

## Sources

1. (Scanner Exceptions)[https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html]
2. (Scanner NextLine)[https://www.geeksforgeeks.org/java/scanner-nextline-method-in-java-with-examples/]
3. (Java Enum)[https://docs.oracle.com/javase/8/docs/api/java/lang/Enum.html]
4. (Best Practices Enum Tests)[https://codingtechroom.com/question/how-to-test-enums-using-junit]
5. (Java Stream for Pipelining Operations, equal to C#)[https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html]
6. (Magic Square by WOlfram Alpha)[https://mathworld.wolfram.com/MagicSquare.html] - Would be possible for optimizing the win condition search.
7. (TicTacToe WinCondition Check Idea 1)[https://stackoverflow.com/questions/18548265/testing-tic-tac-toe-win-condition]
8. (TicTacToe WinCondition Check Idea 2)[https://iq.opengenus.org/check-win-in-tic-tac-toe/]
9. (TicTacToe WinCondition Check Idea 3)[https://www.baeldung.com/cs/tic-tac-toe-winning-combinations] - That is why checking the win condition after each move() change is way better than checking all tiles.
10. (Java classpath configuration in VS Code){https://code.visualstudio.com/docs/java/java-project} - Classpath needs to be configured for src/main/java and for src/test/java in order to have packages like project.tictactoe._board_.
11. (Package-private Classes and Methods for Testing)[https://www.javaspring.net/blog/package-private-java/] - This helped understanding, how to grant the test methods access to the tested methods.
12. (ANSI Color Codes in Java)[https://www.w3schools.blog/ansi-colors-java]
13. (Clear screen for different systems)[https://stackoverflow.com/questions/2979383/how-to-clear-the-console-using-java]
14. (Closing an input scanner)[https://www.baeldung.com/java-scanner-close]
15. (Java Docstrings)[https://www.javaspring.net/blog/java-docstrings/]
16. For generating Java Docstrings and the ReadMe documentation, the VSCode Extension "GitHub Copilot Chat" with the Model "Claude Sonnet 4.5" was used. ALl outputs were reviewed and edited if needed.
