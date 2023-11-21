import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons; // 2D array to represent the Tic-Tac-Toe grid
    private char currentPlayer;   // Represents the current player ('X' or 'O')

    // Constructor for the TicTacToeGUI class
    public TicTacToeGUI() {
        buttons = new JButton[3][3]; // Initialize the 2D array for buttons
        currentPlayer = 'X';         // Start with player 'X'

        initializeUI(); // Set up the graphical user interface
    }

    // Method to initialize the graphical user interface
    private void initializeUI() {
        setTitle("Tic Tac Toe");          // Set the window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed
        setLayout(new GridLayout(3, 3));  // Use a 3x3 grid layout for the buttons

        // Create buttons for each cell in the grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");  // Create a button with empty text
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40)); // Set the font for the button text
                buttons[i][j].setFocusPainted(false); // Remove focus painting
                buttons[i][j].addActionListener(new ButtonClickListener(i, j)); // Add ActionListener to handle button clicks

                add(buttons[i][j]); // Add the button to the JFrame
            }
        }

        setSize(300, 300);           // Set the size of the window
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true);            // Make the window visible
    }

    // Method to switch the current player between 'X' and 'O'
    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    // Method to check if the current player has won the game
    private void checkWinner(int row, int col) {
        // Check for a win in the current row, column, or diagonals
        if (checkRow(row) || checkColumn(col) || checkDiagonals()) {
            JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!"); // Display winner message
            resetGame(); // Reset the game after a win
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a draw!"); // Display draw message
            resetGame(); // Reset the game after a draw
        } else {
            switchPlayer(); // Switch to the next player if no winner or draw
        }
    }

    // Method to check if the current player has won in the specified row
    private boolean checkRow(int row) {
        return buttons[row][0].getText().equals(String.valueOf(currentPlayer))
                && buttons[row][1].getText().equals(String.valueOf(currentPlayer))
                && buttons[row][2].getText().equals(String.valueOf(currentPlayer));
    }

    // Method to check if the current player has won in the specified column
    private boolean checkColumn(int col) {
        return buttons[0][col].getText().equals(String.valueOf(currentPlayer))
                && buttons[1][col].getText().equals(String.valueOf(currentPlayer))
                && buttons[2][col].getText().equals(String.valueOf(currentPlayer));
    }

    // Method to check if the current player has won in any diagonal
    private boolean checkDiagonals() {
        return (buttons[0][0].getText().equals(String.valueOf(currentPlayer))
                && buttons[1][1].getText().equals(String.valueOf(currentPlayer))
                && buttons[2][2].getText().equals(String.valueOf(currentPlayer)))
                || (buttons[0][2].getText().equals(String.valueOf(currentPlayer))
                && buttons[1][1].getText().equals(String.valueOf(currentPlayer))
                && buttons[2][0].getText().equals(String.valueOf(currentPlayer)));
    }

    // Method to check if the game board is full
    private boolean isBoardFull() {
        // If any cell in the grid is still empty, the board is not full
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true; // All cells are filled, indicating a draw
    }

    // Method to reset the game board
    private void resetGame() {
        // Set the text of all buttons to empty and reset the current player to 'X'
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        currentPlayer = 'X';
    }

    // ActionListener implementation for handling button clicks
    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        // Constructor to store the row and column of the clicked button
        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // Method called when a button is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            // If the clicked button is empty, set its text to the current player's symbol
            if (buttons[row][col].getText().equals("")) {
                buttons[row][col].setText(String.valueOf(currentPlayer));
                // Check for a winner or draw after each move
                checkWinner(row, col);
            }
        }
    }
}