import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
    JFrame frame;
    JPanel panel;
    JButton[] buttons;
    char currentPlayer;
    int btnCounter = 0;

    GUI() {
        frame = new JFrame();
        frame.setTitle("TicTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        buttons = new JButton[9];
        currentPlayer = 'X';
        layoutButtons();

        frame.setVisible(true);
    }

    void layoutButtons() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        frame.add(panel, BorderLayout.CENTER);
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].addActionListener(this);
            buttons[i].setFocusable(false);
            buttons[i].setBackground(Color.GREEN);
            buttons[i].setOpaque(true);
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 40));
            panel.add(buttons[i]);
        }
    }

    void switchTurn() {
        if (currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
    }

    void winConCheck() {
        int[][] winCon = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

        for (int[] winner : winCon) {
            if (buttons[winner[0]].getText().equals(String.valueOf(currentPlayer))
                    && buttons[winner[1]].getText().equals(String.valueOf(currentPlayer))
                    && buttons[winner[2]].getText().equals(String.valueOf(currentPlayer))) {
                JOptionPane.showMessageDialog(frame, "Player " + currentPlayer + " wins!");
                rstGame();
                return;
            }
        }

        btnCounter++;

        if (btnCounter == 9) {
            JOptionPane.showMessageDialog(frame, "Draw no one wins!!!");
            rstGame();
        } else {
            switchTurn();
        }
    }

    void rstGame() {
        for (JButton button : buttons) {
            button.setText("");
        }
        currentPlayer = 'X';
        btnCounter = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (clickedButton.getText().isEmpty()) {
            clickedButton.setText(String.valueOf(currentPlayer));
            winConCheck();
        }
    }
}