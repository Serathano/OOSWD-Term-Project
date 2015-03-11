package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Enemy extends GameFigure {

    private Image enemyImage;
    private int health;
    private int points;
    private ShipState state;
    public double deathTimer;
    private String imageName;
    private String imagePath = System.getProperty("user.dir");
    // separator: Windows '\', Linux '/'
    private String separator = System.getProperty("file.separator");
    
    public Enemy(int x, int y, int health, String name, int points) {
        super(x, y);
        imageName = name;
        enemyImage = getImage(imagePath + separator + "ImagesFolder" + separator
                + imageName);
        this.health = health;
        this.points = points;
        this.state = new ShipStateImpervious();
        this.deathTimer = 0;
    }

    @Override
    public int hit() {
        if(state.getClass().equals(ShipStateImpervious.class))
            return 0;
        health--;
        if (health <= 0) {
            alive = 1;
            this.state = state.goNext();
            return points;
        }
        return 0;
    }

    public ShipState getState() {
        return state;
    }

    public void goNext() {
        state = state.goNext();
    }

    @Override
    public boolean living() {
        return alive != 2;
    }

    @Override
    public void render(Graphics g) {
        if (state.getClass() != ShipStateImpervious.class && state.getClass() != ShipStateAlive.class) {
            enemyImage = getImage(imagePath + separator + "ImagesFolder" + separator
                    + state.getImage(this.getIFF()));
        }
        if (state.getClass() == ShipStateDead.class){
            this.alive = 2;
        }
        g.drawImage(enemyImage, super.x, super.y, 30, 30, null);
    }

    @Override
    public void update() {//Unnecessary
    }

    public void translate(int dx, int dy) {
        super.x += dx;
        super.y += dy;
    }

    public static Image getImage(String fileName) {
        Image image = null;
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException ioe) {
            System.out.println("Error: Cannot open image:" + fileName);
            JOptionPane.showMessageDialog(null, "Error: Cannot open image:" + fileName);
        }
        return image;
    }

    @Override
    public String toString() {
        return super.x + ", " + super.y;
    }

    @Override
    public void changeDirection() {/*not needed*/

    }

    @Override
    public Rectangle2D.Double getCollisionBox() {
        return new Rectangle2D.Double(super.x + 2, super.y,
                enemyImage.getWidth(null) * 1.3, enemyImage.getHeight(null));
    }

    @Override
    public boolean getIFF() {
        return true;//Unnecessary
    }

}
