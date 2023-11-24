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
    boolean vsAI = false;    // Flagga för att avgöra om spelet är mot en AI eller en vän


    GUI() {
        // Initialisera huvudfönstret
        frame = new JFrame();
        frame.setTitle("TicTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Skapa en menyfält och menyalternativ
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game meny");
        JMenuItem vsFriendItem = new JMenuItem("Play vs Friend");
        JMenuItem vsAIItem = new JMenuItem("Play vs AI");

        // Lägg till ActionListener för menyalternativen
        vsFriendItem.addActionListener(this);
        vsAIItem.addActionListener(this);

        // Lägg till menyalternativen i menyn
        gameMenu.add(vsFriendItem);
        gameMenu.add(vsAIItem);

        menuBar.add(gameMenu);
        frame.setJMenuBar(menuBar);

        buttons = new JButton[9];
        currentPlayer = 'X';

        // Skapa JLabel för Vems tur det är
        turnLabel = new JLabel("Player: " + currentPlayer + " turn");
        frame.add(turnLabel, BorderLayout.SOUTH);

        layoutButtons();

        frame.setVisible(true);
    }



    // Metod för att sätta upp layouten för knapparna
    void layoutButtons() {
        // Skapa en panel med ett 3x3 rutnätslayout
        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        frame.add(panel, BorderLayout.CENTER);

       /*  Initialisera knappar, sätt egenskaper och lägg till dem i panelen,
         för var iteration sätts en JButton ut tills vi har 9 i ett grid 3X3*/
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

    // Metod som kollar vems tur det är och isåfall bytar till nästa spelare mellan 'X' och 'O'
    void switchTurn() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        turnLabel.setText("Player: " + currentPlayer + " turn");
    }

    // Metod för att kontrollera om det blev vinst eller oavgjort
    void winConCheck() {
        int[][] winCon = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

        // Kontrollera om det finns en vinnande kombination
        for (int[] winner : winCon) {
            if (buttons[winner[0]].getText().equals(String.valueOf(currentPlayer))
                    && buttons[winner[1]].getText().equals(String.valueOf(currentPlayer))
                    && buttons[winner[2]].getText().equals(String.valueOf(currentPlayer))) {
                JOptionPane.showMessageDialog(frame, "Player " + currentPlayer + " wins!");
                rstGame();
                return;
            }
        }

        // Öka antalet knapptryckningar
        btnCounter++;

        // Kontrollera om det är oavgjort
        if (btnCounter == 9) {
            JOptionPane.showMessageDialog(frame, "Draw no one wins!!!");
            rstGame();
        } else {
            switchTurn();
            // Om spelet är mot AI, gör AI:s drag
            if (vsAI && currentPlayer == 'O') {
                makeAIMove();
            }
        }
    }

    // Metod för att låta AI göra ett slumpmässigt drag
    void makeAIMove() {
        Random rand = new Random();
        int aiMove;
        do {
            aiMove = rand.nextInt(9);
        } while (!buttons[aiMove].getText().isEmpty());

        buttons[aiMove].setText("O");

        winConCheck();
    }

    // Metod för att starta om spelet
    void rstGame() {
        for (JButton button : buttons) {
            button.setText("");
        }

        // Slumpmässigt bestäm startspelaren för nästa omgång
        Random random = new Random();
        if (random.nextBoolean()) {
            currentPlayer = 'X' ;
        } else currentPlayer = 'O';
        turnLabel.setText("Player: " + currentPlayer + " turn");

        // Återställ antalet knapptryckningar
        btnCounter = 0;
    }


    // Actionlistener för knapptryckningar och menyalternativ
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source instanceof JMenuItem menuItem) {

            // Hantera åtgärder för menyalternativ
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

        // Om Ai börjar
        } else if (source instanceof JButton clickedButton) {

            if (clickedButton.getText().isEmpty()) {
                clickedButton.setText(String.valueOf(currentPlayer));
                winConCheck();
            }
        }
    }
}