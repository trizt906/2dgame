package space_blasters;

import java.awt.*;
import java.util.ArrayList;

public class Asteroid {
    private int x, y;
    private int health;
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;

    public Asteroid(int x, int y) {
        this.x = x;
        this.y = y;
        this.health = 2;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillOval(x, y, WIDTH, HEIGHT);
    }

    public void update() {
        y += 2;
    }

    public boolean isOffScreen() {
        return y > 600;
    }

    public boolean collidesWith(Player player) {
        return x < player.getX() + player.getWidth() && x + WIDTH > player.getX() &&
               y < player.getY() + player.getHeight() && y + HEIGHT > player.getY();
    }

    public void takeDamage() {
        health--;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}