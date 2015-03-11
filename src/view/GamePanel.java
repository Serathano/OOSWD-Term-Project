package view;

import controller.Main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JPanel;
import model.GameFigure;

public class GamePanel extends JPanel {

    public static final int PWIDTH = 600; // size of the game panel
    public static final int PHEIGHT = 400;

    // off screen rendering
    private Graphics graphics;
    private Image dbImage = null;

    public GamePanel() {
        this.setBackground(Color.black);
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
        //this.setForeground(Color.BLACK);
    }

    public void gameRender() {
        //this.setBackground(Color.BLACK);
        if (dbImage == null) {
            dbImage = createImage(PWIDTH, PHEIGHT);
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            } else {
                graphics = dbImage.getGraphics();
            }
        }

        graphics.clearRect(0, 0, GamePanel.PWIDTH, GamePanel.PHEIGHT);
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, GamePanel.PWIDTH, GamePanel.PHEIGHT);
        synchronized (Main.gameData.figures) {
            Main.gameData.figures.stream().forEach((f) -> {
                f.render(graphics);
            });
        }
        Main.gameData.healthBar.render(graphics);

    }
    
    

    // use active rendering to put the buffered image on-screen
    public void printScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
            if (g != null) {
                g.dispose();
            }
        } catch (Exception e) {
            System.out.println("Graphics error: " + e);
        }
    }
}
