package space_blasters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {

    private Timer timer;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> enemyBullets;
    private String mode;

    public GamePanel(String mode) {
        this.mode = mode;
        player = new Player(375, 500);
        enemies = new ArrayList<>();
        powerUps = new ArrayList<>();
        bullets = new ArrayList<>();
        enemyBullets = new ArrayList<>();

        setFocusable(true);
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                player.moveToMouse(e.getX()); 
            }
        });

        // fires every 500 ms for automatic shooting
        timer = new Timer(16, this); 
    }

    public void startGame() {
        timer.start();
        spawnEnemies(); 

        // shoot bullets automatically every 500ms
        new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.autoShoot(bullets);
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // background/stars
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        drawStars(g);

        draw(g);
    }

    private void drawStars(Graphics g) {
        g.setColor(Color.WHITE);
        for (int i = 0; i < 100; i++) {
            int x = (int)(Math.random() * getWidth());
            int y = (int)(Math.random() * getHeight());
            g.fillRect(x, y, 2, 2);
        }
    }

    private void draw(Graphics g) {
        player.draw(g);

        for (Bullet bullet : bullets) {
            bullet.draw(g); // player bullet
        }

        for (Bullet bullet : enemyBullets) {
            bullet.draw(g); // enemy bullet
        }

        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        for (PowerUp powerUp : powerUps) {
            powerUp.draw(g);
        }

        // display current mode on screen
        g.setColor(Color.WHITE);
        g.drawString("Mode: " + mode, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame();
        repaint();
    }

    private void updateGame() {
        player.update();

        // Update bullets
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update();

            if (bullet.isOffScreen()) {
                bullets.remove(i);
                i--;
            }
        }

        // enemy bullets
        for (int i = 0; i < enemyBullets.size(); i++) {
            Bullet bullet = enemyBullets.get(i);
            bullet.update();
            if (bullet.isOffScreen()) {
                enemyBullets.remove(i);
                i--;
            } else if (bullet.collidesWith(player)) {
                System.out.println("Player hit!");
                enemyBullets.remove(i);
                i--;
                // player hit logic here
            }
        }

        // enemy update
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.update();

            if (Math.random() < 0.02) {
                enemy.shoot(enemyBullets);
            }

            for (int j = 0; j < bullets.size(); j++) {
                Bullet bullet = bullets.get(j);
                if (bullet.collidesWith(enemy)) {
                    enemies.remove(i);
                    bullets.remove(j);
                    i--;
                    break;
                }
            }

            if (enemy.isOffScreen()) {
                enemies.remove(i);
                i--;
            }
        }

        // power-ups
        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp powerUp = powerUps.get(i);
            powerUp.update();

            if (powerUp.isCollected(player)) {
                powerUps.remove(i);
                i--;
                // power-up effect here
            }

            if (powerUp.isOffScreen()) {
                powerUps.remove(i);
                i--;
            }
        }

        // spawn
        if (Math.random() < 0.02) spawnEnemies();
        if (Math.random() < 0.01) spawnPowerUps();
    }

    private void spawnEnemies() {
        int x = (int)(Math.random() * 750);
        enemies.add(new Enemy(x, 0));
    }

    private void spawnPowerUps() {
        int x = (int)(Math.random() * 750);
        powerUps.add(new PowerUp(x, 0));
    }
}
