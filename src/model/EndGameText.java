
package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import static model.Enemy.getImage;


    public class EndGameText extends GameFigure{
        
    private final Image finishImage;
    private static Rectangle2D.Double box = new Rectangle2D.Double(-50, -50, 0, 0);
    private String imagePath = System.getProperty("user.dir");
    // separator: Windows '\', Linux '/'
    private String separator = System.getProperty("file.separator");
    
    EndGameText(int x, int y, String name){
    super(x, y);
        finishImage = getImage(imagePath + separator + "ImagesFolder" + separator
                + name);
    }
    
    public void translate(int dx, int dy) {
        super.x += dx;
        super.y += dy;
    }
    
    @Override
    public void changeDirection() {//Unnecessary
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(finishImage, super.x - 200, super.y, 400, 100, null);
    }

    @Override
    public void update() {//Unnecessary
    }

    @Override
    public Rectangle2D.Double getCollisionBox() {//Unnecessary
        return box;
    }

    @Override
    public int hit() {//Unnecessary
        return 0;
    }

    @Override
    public boolean living() {//Unnecessary
        return true;
    }

    @Override
    public boolean getIFF() {//Unnecessary
        return false;
    }
    
}
