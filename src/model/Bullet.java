package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import view.GamePanel;

public class Bullet extends GameFigure {

    private final int radius;
    private final Color color;
    private int dy = -3;
    public boolean pastEdge;
    boolean IFF; //Indicating Friend or Foe = IFF, not if and only if

    public Bullet(int x, int y, Color color, boolean IFF) {
        super(x, y);
        this.radius = 4;
        this.color = color;
        this.pastEdge = false;
        if (IFF) {
            this.changeDirection();
        }
        this.IFF = IFF;
    }

    @Override
    public void changeDirection() {
        dy = -dy;
    }

    @Override
    public int hit() {
        this.alive = 2;
        return 0;
    }

    @Override
    public boolean living() {
        return alive != 2;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval(x - radius, y - radius, radius, radius);
    }

    public boolean getOutOfBounds() {
        return pastEdge;
    }

    @Override
    public void update() {
        super.y += dy;
        if (super.y + radius > GamePanel.PHEIGHT + 50) {
            this.pastEdge = true;
        } else if (super.y - radius < -50) {
            this.pastEdge = true;
        }

        if (this.pastEdge) {
            hit();
        }
    }

    @Override
    public Rectangle2D.Double getCollisionBox() {
        return new Rectangle2D.Double(super.x - radius,
                super.y,
                radius * 1.1, radius * 1.1);
    }

    @Override
    public boolean getIFF() {
        return IFF;
    }
}
