package space_blasters;

import java.awt.*;
import java.util.ArrayList;

public class Enemy {
    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;
    private int x, y;
    private int health;

    public Enemy(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.health = 3; // Starting health for enemies
    }

   public void draw(Graphics g) {
       g.setColor(Color.RED);
       g.fillRect(x, y, WIDTH, HEIGHT);

       // Draw health bar in red above the enemy
       g.setColor(Color.RED);
       g.fillRect(x, y -10 , WIDTH * health /3 ,5 );// Health bar length based on current health

   }

   public void update() {
       y += 1; // Move downwards at a constant speed.
   }

   public void shoot(ArrayList<Bullet> enemyBullets, Player player) {
       int bulletX = x + WIDTH / 2 - Bullet.WIDTH / 2;
       int bulletY = y + HEIGHT; 
       Bullet bullet = new Bullet(bulletX, bulletY, false); 
       enemyBullets.add(bullet);
   }

   public boolean collidesWith(Player player) {
       return x < player.getX() + player.getWidth() && x + WIDTH > player.getX() &&
              y < player.getY() + player.getHeight() && y + HEIGHT > player.getY();
   }

   public boolean isAlive() {
       return health > 0;
   }

   public void takeDamage() {
       health--;
   }

   public int getX() { return x; }
   
   public int getY() { return y; }
   
   public boolean isOffScreen() {
       return y > 600; 
   }
}
