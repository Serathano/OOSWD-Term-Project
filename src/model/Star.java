package model;

import view.GamePanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Star extends GameFigure { //formerly Ball class

    private final int radius;
    private final Color color;
    private int dx = 0;
    private int dy = 2;
    private static Rectangle2D.Double box = new Rectangle2D.Double(-50, -50, 0, 0);
    private boolean pastEdge;

    public Star(int x, int y, int radius, Color color) {
        super(x, y);
        this.radius = radius;
        this.color = color;
        pastEdge = false;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Override
    public void update() {
        super.x += dx;
        super.y += dy;

        if (super.x + radius > GamePanel.PWIDTH + 10) {
            pastEdge = true;
        } else if (super.x - radius < -10) {
            pastEdge = true;
        }

        if (super.y + radius > GamePanel.PHEIGHT + 50) {
            pastEdge = true;
        } else if (super.y - radius < -50) {
            pastEdge = true;
        }

        if (pastEdge) {
            hit();
        }
    }

    @Override
    public int hit() {
        alive = 2;
        return 0;//Unnecessary
    }

    @Override
    public boolean living() {
        return alive != 2;//Unnecessary
    }

    @Override
    public final void changeDirection() {//Unnecessary      
    }

    @Override
    public Rectangle2D.Double getCollisionBox() {
        return box;//Non-collider
    }

    @Override
    public boolean getIFF() {
        return false;//Unnecessary
    }
}
