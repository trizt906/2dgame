package space_blasters;

import java.awt.*;

public class PowerUp {
    private int x, y;
    private String type;

    public PowerUp(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw(Graphics g) {
        switch(type) {
            case "health":
                g.setColor(Color.GREEN);
                g.fillOval(x, y, 20, 20); // Green circle for health power-up
                break;
            case "speed":
                g.setColor(Color.BLUE);
                g.fillOval(x, y, 20, 20); // Blue circle for speed power-up
                break;
            case "spread":
                g.setColor(Color.YELLOW);
                g.fillOval(x, y, 20, 20); // Yellow circle for spread bullets power-up
                break;
            case "shield":
                g.setColor(Color.CYAN);
                g.fillOval(x, y, 20, 20); // Cyan circle for shield power-up
                break;
            default:
                g.setColor(Color.YELLOW);
                g.fillOval(x, y, 20, 20); // Default color for other types
                break;
        }
    }

    public void update() {
       y += 2; // Power-up falls downwards.
    }

    public boolean isOffScreen() {
       return y > 600;
    }

    public boolean isCollected(Player player) {
       return player.getX() < x + 20 && player.getX() + player.getWidth() > x &&
              player.getY() < y + 20 && player.getY() + player.getHeight() > y;
    }

    public String getType() {
       return type;
    }
}