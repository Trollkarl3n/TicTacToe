import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GUI implements ActionListener {
    JFrame frame;
    JPanel panel;
    JLabel turnLabel;
    JButton[] buttons;
    char currentPlayer;
    int btnCounter = 0;
    boolean vsAI = false;


    GUI() {
        frame = new JFrame();
        frame.setTitle("TicTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game meny");
        JMenuItem vsFriendItem = new JMenuItem("Play vs Friend");
        JMenuItem vsAIItem = new JMenuItem("Play vs AI");

        vsFriendItem.addActionListener(this);
        vsAIItem.addActionListener(this);

        gameMenu.add(vsFriendItem);
        gameMenu.add(vsAIItem);

        menuBar.add(gameMenu);
        frame.setJMenuBar(menuBar);

        buttons = new JButton[9];
        currentPlayer = 'X';

        turnLabel = new JLabel("Player: " + currentPlayer + " turn");
        frame.add(turnLabel, BorderLayout.SOUTH);

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
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        turnLabel.setText("Player: " + currentPlayer + " turn");
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

            if (vsAI && currentPlayer == 'O') {
                makeAIMove();
            }
        }
    }

    void makeAIMove() {
        Random rand = new Random();
        int aiMove;
        do {
            aiMove = rand.nextInt(9);
        } while (!buttons[aiMove].getText().isEmpty());

        buttons[aiMove].setText("O");
        winConCheck();
    }

    void rstGame() {
        for (JButton button : buttons) {
            button.setText("");
        }

        Random random = new Random();
        if (random.nextBoolean()) {
            currentPlayer = 'X' ;
        } else currentPlayer = 'O';

        btnCounter = 0;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source instanceof JMenuItem) {
            JMenuItem menuItem = (JMenuItem) source;

            if (menuItem.getText().equals("Play vs Friend")) {
                vsAI = false;
                JOptionPane.showMessageDialog(frame, "You selected to play vs friend");
                rstGame();
            } else if (menuItem.getText().equals("Play vs AI")) {
                vsAI = true;
                JOptionPane.showMessageDialog(frame, "You selected to play vs AI");
                rstGame();

                if (currentPlayer == 'O') {
                    makeAIMove();
                }
            }

        } else if (source instanceof JButton) {
            JButton clickedButton = (JButton) source;

            if (clickedButton.getText().isEmpty()) {
                clickedButton.setText(String.valueOf(currentPlayer));
                winConCheck();
            }
        }
    }
}