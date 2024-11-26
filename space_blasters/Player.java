package space_blasters;

import java.awt.*;
import java.util.ArrayList;

public class Player {

    private int x, y;
    private int width = 50, height = 50; // size of the spaceship
    private int screenWidth = 800;

    public Player(int startX, int startY) {
        x = startX; // starting position
        y = startY;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    public void update() {
    }

    public void moveToMouse(int mouseX) {
        x = mouseX - width / 2;
        if (x < 0) x = 0;
        if (x > screenWidth - width) x = screenWidth - width;
    }

    public void autoShoot(ArrayList<Bullet> bullets) {
        bullets.add(new Bullet(x + width / 2 - Bullet.WIDTH / 2, y, true));
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
