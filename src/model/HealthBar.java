package model;

import controller.Main;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class HealthBar extends GameFigure {

    private int lives;
    private final Image launcherImage;
    private static Rectangle2D.Double box = new Rectangle2D.Double(-50, -50, 0, 0);

    public HealthBar(int x, int y, int numberOfLives) {
        super(x, y);
        String imagePath = System.getProperty("user.dir");
        // separator: Windows '\', Linux '/'
        String separator = System.getProperty("file.separator");
        launcherImage = getImage(imagePath + separator + "ImagesFolder" + separator
                + "Ship_White.png");
        this.lives = numberOfLives;
        this.alive = 0;
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
    public int hit() {
        this.lives--;
        if (lives <= 0) {
            this.alive = 2;
        }
        return this.alive;
    }

    @Override
    public void render(Graphics g) {
        for (int x = 0; x < lives; x++) {
            g.drawImage(launcherImage, super.x + (x * 15 + 20), super.y, 15, 15, null);
        }
    }

    public int getLives() {
        return this.lives;
    }

    @Override
    public boolean living() {
        return alive != 2;
    }

    @Override
    public void changeDirection() {//Unnecessary
    }

    @Override
    public void update() {//Unnecessary
    }

    @Override
    public Rectangle2D.Double getCollisionBox() {
        return box;//Unnecessary
    }

    @Override
    public boolean getIFF() {
        return false;//Unnecessary
    }

}
