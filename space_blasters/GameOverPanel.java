package space_blasters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverPanel extends JPanel implements ActionListener {
    private SpaceBlasters game;
    private int finalScore;

    public GameOverPanel(SpaceBlasters game, int score) {
        this.game = game;
        this.finalScore = score;

        setBackground(Color.BLACK);
        setLayout(new GridLayout(4, 1));

        JLabel gameOverLabel = new JLabel("Game Over!", SwingConstants.CENTER);
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));

        JLabel scoreLabel = new JLabel("Final Score: " + finalScore, SwingConstants.CENTER);
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton retryButton = createButton("Retry");
        JButton mainMenuButton = createButton("Main Menu");

        retryButton.addActionListener(this);
        mainMenuButton.addActionListener(this);

        add(gameOverLabel);
        add(scoreLabel);
        add(retryButton);
        add(mainMenuButton);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.GRAY);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.DARK_GRAY);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.LIGHT_GRAY);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.GRAY);
            }
        });

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Retry")) {
            game.startGame("Challenge Mode");
            removeGameOverPanel();
        } else if (e.getActionCommand().equals("Main Menu")) {
            game.remove(this);
            game.add(new MainMenu(game));
            game.revalidate();
            game.repaint();
        }
    }

    private void removeGameOverPanel() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.remove(this);
    }
}