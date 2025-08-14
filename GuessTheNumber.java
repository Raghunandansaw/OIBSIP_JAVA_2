import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class GuessTheNumber extends JFrame {
    private int numberToGuess;
    private int attemptsLeft = 7;
    private int totalScore = 0;
    private int round = 1;
    private final int totalRounds = 3;
    private final Random rand = new Random();

    private JLabel messageLabel;
    private JTextField guessField;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;

    public GuessTheNumber() {
        setTitle("🎯 Guess The Number Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("🎯 Guess The Number!", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.ORANGE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(30, 30, 30));
        centerPanel.setLayout(new GridLayout(4, 1, 5, 5));

        messageLabel = new JLabel("Round " + round + ": Guess between 1 and 100", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setForeground(Color.CYAN);
        centerPanel.add(messageLabel);

        guessField = new JTextField();
        guessField.setHorizontalAlignment(JTextField.CENTER);
        guessField.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(guessField);

        attemptsLabel = new JLabel("Attempts Left: " + attemptsLeft, JLabel.CENTER);
        attemptsLabel.setForeground(Color.YELLOW);
        centerPanel.add(attemptsLabel);

        scoreLabel = new JLabel("Score: " + totalScore, JLabel.CENTER);
        scoreLabel.setForeground(Color.GREEN);
        centerPanel.add(scoreLabel);

        add(centerPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 30, 30));

        JButton guessButton = new JButton("Guess");
        guessButton.setBackground(Color.ORANGE);
        guessButton.setForeground(Color.BLACK);
        guessButton.setFont(new Font("Arial", Font.BOLD, 14));
        guessButton.addActionListener(e -> checkGuess());

        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(guessButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        startNewRound();
    }

    private void startNewRound() {
        numberToGuess = rand.nextInt(100) + 1;
        attemptsLeft = 7;
        messageLabel.setText("Round " + round + ": Guess between 1 and 100");
        messageLabel.setForeground(Color.CYAN);
        attemptsLabel.setText("Attempts Left: " + attemptsLeft);
        guessField.setText("");
    }

    private void showRoundResult(String title, String message, Color color) {
        // Create a custom result window
        JDialog resultDialog = new JDialog(this, title, true);
        resultDialog.setSize(450, 250);
        resultDialog.setLayout(new BorderLayout());
        resultDialog.setLocationRelativeTo(this);
        resultDialog.getContentPane().setBackground(new Color(40, 40, 40));

        JLabel msg = new JLabel(message, JLabel.CENTER);
        msg.setForeground(color);
        msg.setFont(new Font("Arial", Font.BOLD, 16));
        resultDialog.add(msg, BorderLayout.CENTER);

        JButton nextButton = new JButton("Next Round");
        nextButton.setBackground(Color.ORANGE);
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.addActionListener(e -> resultDialog.dispose());

        resultDialog.add(nextButton, BorderLayout.SOUTH);
        resultDialog.setVisible(true);
    }

    private void checkGuess() {
        String input = guessField.getText().trim();
        int guess;

        try {
            guess = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            messageLabel.setText("⚠ Enter a valid number!");
            messageLabel.setForeground(Color.RED);
            return;
        }

        attemptsLeft--;
        attemptsLabel.setText("Attempts Left: " + attemptsLeft);

        if (guess == numberToGuess) {
            int points = attemptsLeft * 10 + 50;
            totalScore += points;
            scoreLabel.setText("Score: " + totalScore);

            messageLabel.setText("🎉 Correct! The number was " + numberToGuess);
            messageLabel.setForeground(Color.GREEN);

            showRoundResult("Round " + round + " Result",
                    "✅ Correct! The number was " + numberToGuess +
                            "\nYou earned " + points + " points!",
                    Color.GREEN);

            if (round < totalRounds) {
                round++;
                startNewRound();
            } else {
                JOptionPane.showMessageDialog(this, "🏆 Game Over! Final Score: " + totalScore);
                System.exit(0);
            }

        } else if (guess < numberToGuess) {
            messageLabel.setText("📈 Higher!");
            messageLabel.setForeground(Color.MAGENTA);
        } else {
            messageLabel.setText("📉 Lower!");
            messageLabel.setForeground(Color.PINK);
        }

        if (attemptsLeft == 0 && guess != numberToGuess) {
            messageLabel.setText("❌ Out of attempts! The number was " + numberToGuess);
            messageLabel.setForeground(Color.RED);

            showRoundResult("Round " + round + " Result",
                    "❌ Out of attempts! The number was " + numberToGuess,
                    Color.RED);

            if (round < totalRounds) {
                round++;
                startNewRound();
            } else {
                JOptionPane.showMessageDialog(this, "🏆 Game Over! Final Score: " + totalScore);
                System.exit(0);
            }
        }

        guessField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuessTheNumber game = new GuessTheNumber();
            game.setVisible(true);
        });
    }
}
