package model;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class GameFigure {

    public int x; // for a faster access
    public int y;
    protected int alive; //0 = alive, 1 = dead but dont delete 2 = dead, delete

    public GameFigure(int x, int y) {
        this.x = x;
        this.y = y;
        alive = 0;
    }

    public abstract void changeDirection();

    public abstract void render(Graphics g);

    public abstract void update();

    public abstract Rectangle2D.Double getCollisionBox();

    public abstract int hit();

    public abstract boolean living();

    public abstract boolean getIFF();
}
