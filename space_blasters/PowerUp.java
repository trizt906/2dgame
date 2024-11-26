package space_blasters;

import java.awt.*;

public class PowerUp {
     private int x, y;

     public PowerUp(int x, int y) { 
          this.x = x; 
          this.y = y; 
      }

      public void draw(Graphics g) { 
          g.setColor(Color.GREEN); 
          g.fillOval(x, y, 20, 20);
      }

      public void update() { 
          y += 2;
      }

      public boolean isOffScreen() { 
          return y > 600;
      }

      public boolean isCollected(Player player) { 
          return player.getX() < x + 20 && player.getX() + player.getWidth() > x &&
                 player.getY() < y + 20 && player.getY() + player.getHeight() > y; 
      }
}