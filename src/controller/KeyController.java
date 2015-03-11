package controller;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.Bullet;
import model.Hero;

public class KeyController implements KeyListener {

    public boolean stillHeld = false;
    public boolean goLeft = false;
    public boolean goRight = false;
    public boolean noFire = false;
    public boolean noMove = false;

    @Override
    public void keyPressed(KeyEvent e) {

        Hero hero = (Hero) Main.gameData.figures.get(0);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (hero.x > 0) {
                    goLeft = true;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (hero.x + 30 < Main.gamePanel.getWidth()) {
                    goRight = true;
                }
                break;
            case KeyEvent.VK_SPACE:
                if (!stillHeld && !noFire) {
                    Main.gameData.figures.add(new Bullet(hero.x + 17, hero.y, Color.CYAN, false));
                    stillHeld = true;
                }
        }
        Main.gameData.figures.set(0, hero);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        this.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                goLeft = false;
                break;
            case KeyEvent.VK_RIGHT:
                goRight = false;
                break;
            case KeyEvent.VK_SPACE:
                stillHeld = false;
                break;
        }
    }

}
