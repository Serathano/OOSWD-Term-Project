package model;

import controller.Main;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import view.GamePanel;
import view.MainWindow;

public class Hero extends GameFigure {

    private Image launcherImage;
    private ShipState state;
    public double deathTimer;
    String imagePath = System.getProperty("user.dir");
    // separator: Windows '\', Linux '/'
    String separator = System.getProperty("file.separator");

    public Hero(int x, int y) {
        super(x, y);

        this.state = new ShipStateImpervious();
        this.launcherImage = getImage(imagePath + separator + "ImagesFolder" + separator
                + state.getImage(getIFF()));
        this.deathTimer = 0;
    }

    @Override
    public void render(Graphics g) {

        launcherImage = getImage(imagePath + separator + "ImagesFolder" + separator
                + state.getImage(this.getIFF()));
        if (state.getClass() == ShipStateDead.class) {
            Main.gameData.figures.set(0, new Hero((GamePanel.PWIDTH / 2) - 16, GamePanel.PHEIGHT - 60));
            MainWindow.keyListener.noMove = false;
            MainWindow.keyListener.noFire = false;
            return;
        }
        g.drawImage(launcherImage, super.x, super.y, 30, 30, null);
    }

    @Override
    public int hit() {
        //if ship state is impervious, nothing happens
        if (!this.state.getClass().equals(ShipStateAlive.class)) {
            return 0;
        }
        this.state = state.goNext();
        MainWindow.keyListener.noMove = true;
        MainWindow.keyListener.noFire = true;
        //invulnerability happens here
        int check = Main.gameData.healthBar.hit();
        /*if (check > 0) {//remove comment markers to make mortal
            while (!Main.gameData.leveller.gameOver()) {
                Main.gameData.leveller.setLevel(new LevelGameOver());
                //Main.gameData.leveller.createLevel();
                MainWindow.stopGame();
            }
        }*/
        return 0;
    }

    public ShipState getState() {
        return this.state;
    }

    public void goNext() {
        this.state = this.state.goNext();
    }

    @Override
    public boolean living() {
        return alive != 2;
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
        return new Rectangle2D.Double(super.x, super.y + 15,
                launcherImage.getWidth(null) * 1.5, launcherImage.getHeight(null));
    }

    @Override
    public boolean getIFF() {
        return false;
    }
}
