package space_blasters;

import java.awt.*;

public class Bullet {
    public static final int WIDTH = 5;
    public static final int HEIGHT = 10;

    private int x, y;
    private boolean isPlayerBullet; 
    private int speed; // Speed of the bullet

    public Bullet(int x, int y, boolean isPlayerBullet) { 
        this.x = x; 
        this.y = y; 
        this.isPlayerBullet = isPlayerBullet;
        this.speed = isPlayerBullet ? 8 : 4; // Default speed for player bullets (faster)
    }

    public void draw(Graphics g) { 
        g.setColor(isPlayerBullet ? Color.YELLOW : Color.RED); 
        g.fillRect(x, y, WIDTH, HEIGHT);  
    }

    public void update() {  
        if (isPlayerBullet) {
            y -= speed; // move up for player bullets
        } else {
            // Enemy bullets should move towards the player's position
            // Assuming you have access to the player's position here
            // You might want to modify this in the GamePanel or Enemy class
            y += speed; // move down for enemy bullets
        }
    } 

    public boolean isOffScreen() {  
        return y < -HEIGHT || y > 600; 
    } 

    public boolean collidesWith(Enemy enemy) {  
        return x < enemy.getX() + Enemy.WIDTH && x + WIDTH > enemy.getX() &&
               y < enemy.getY() + Enemy.HEIGHT && y + HEIGHT > enemy.getY(); 
    } 

    public boolean collidesWith(Player player) {  
        return x < player.getX() + player.getWidth() && x + WIDTH > player.getX() &&
               y < player.getY() + player.getHeight() && y + HEIGHT > player.getY();  
    }

    public boolean collidesWith(Asteroid asteroid) {
        return x < asteroid.getX() + Asteroid.WIDTH && x + WIDTH > asteroid.getX() &&
               y < asteroid.getY() + Asteroid.HEIGHT && y + HEIGHT > asteroid.getY();
    }
}