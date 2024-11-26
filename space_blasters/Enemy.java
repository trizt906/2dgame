package space_blasters;

import java.awt.*;
import java.util.ArrayList;

public class Enemy {
     int x;
	int y;

     public Enemy(int x, int y) { 
          this.x = x; 
          this.y = y; 
     }

     public void draw(Graphics g) { 
          g.setColor(Color.RED); 
          g.fillRect(x, y, 40, 40);
     }

     public void update() { 
          y += 2; 
     }
     
     public boolean isOffScreen() { 
          return y > 600; 
     }

     public void shoot(ArrayList<Bullet> enemyBullets) { 
    	    enemyBullets.add(new Bullet(x + 20 - Bullet.WIDTH / 2, y + 40, false)); // Shoot from bottom of the enemy.
    	}
}