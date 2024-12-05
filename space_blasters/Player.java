package space_blasters;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Player {
    private int x, y;
    private int width = 50, height = 50; // size of the spaceship
    private int screenWidth = 800; // Update screen width to match portrait mode
    private int screenHeight = 600; // Update screen height to match portrait mode
    private int health; // Player's health
    private boolean hasShield; // Shield status
    private int bulletSpread; // Number of bullets to fire when spread
    private static final int MAX_BULLET_SPREAD = 5; // Maximum bullet spread
    private Timer shieldTimer; // Timer for shield duration
    private Timer bulletSpreadTimer; // Timer for bullet spread duration

    public Player(int startX, int startY) {
        x = startX; // starting position
        y = startY;
        health = 3; // Starting health
        hasShield = false; // No shield initially
        bulletSpread = 1; // Normal firing mode
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);

        // Draw health bar in green
        g.setColor(Color.GREEN);
        for (int i = 0; i < health; i++) {
            g.fillRect(x + i * 20, y - 10, 15, 5);
        }

        if (hasShield) { // Draw shield if active
            g.setColor(Color.CYAN);
            g.drawRect(x - 5, y - 5, width + 10, height + 10); // Simple shield representation
        }
    }

    public void update() {}

    public void moveToMouse(int mouseX, int mouseY) {
        // Calculate new x position
        x = mouseX - width / 2;

        // Ensure player does not go off-screen
        if (x < 0) x = 0; // Prevent moving off the left side
        if (x > screenWidth - width) x = screenWidth - width; // Prevent moving off the right side

        // Calculate new y position
        y = mouseY - height / 2;

        // Ensure player does not go off-screen vertically
        if (y < 0) y = 0; // Prevent moving off the top
        if (y > screenHeight - height) y = screenHeight - height; // Prevent moving off the bottom
    }

    public void autoShoot(ArrayList<Bullet> bullets) {
        if (bulletSpread == 1) { 
            bullets.add(new Bullet(x + width / 2 - Bullet.WIDTH / 2, y, true)); // single bullet
        } else { 
            for (int i = -bulletSpread; i <= bulletSpread; i++) { 
                bullets.add(new Bullet(x + width / 2 - Bullet.WIDTH / 2 + i * Bullet.WIDTH * 2, y, true)); // spread bullets
            }
        }
    }

    public void takeDamage() {
        if (!hasShield) { 
            health--;
        }
    }

    public void restoreHealth() {
        if (health < 3) { 
            health++;
        }
    }

    public void activateShield() {
        hasShield = true; // Activate shield
        scheduleShieldDeactivation(); // Schedule deactivation after a duration
    }

    private void scheduleShieldDeactivation() {
        if (shieldTimer != null) {
            shieldTimer.cancel(); // Cancel any existing timer
        }
        
        shieldTimer = new Timer();
        shieldTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                hasShield = false; // Deactivate shield after duration
            }
        }, 5000); // Duration in milliseconds (e.g., 5000 ms = 5 seconds)
    }

    public void increaseBulletSpread(int amount) {
        bulletSpread += amount;
        if (bulletSpread > MAX_BULLET_SPREAD) { 
            bulletSpread = MAX_BULLET_SPREAD;
        }
        
        scheduleBulletSpreadDeactivation(); // Schedule deactivation after a duration
    }

    private void scheduleBulletSpreadDeactivation() {
        if (bulletSpreadTimer != null) {
            bulletSpreadTimer.cancel(); // Cancel any existing timer
        }
        
        bulletSpreadTimer = new Timer();
        bulletSpreadTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                bulletSpread = 1; // Reset to normal firing mode after duration
            }
        }, 5000); // Duration in milliseconds (e.g., 5000 ms = 5 seconds)
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getHealth() { return health; }
}
