package model;

import controller.Main;
import java.util.List;
import view.MainWindow;

public class LevelGameOver implements Level {

    private double time;
    private final double delta;
    private final double moveDelay;
    private boolean inPosition;
    private int moveDistance;
    
    LevelGameOver(){
        time = 0;
        delta = 1.0 / 40.0;
        moveDelay = .500;
        inPosition = false;
        moveDistance = 0;
    }
    
    @Override
    public void makeLevel() {
        MainWindow.stopGame();

        if (Main.gameData.figures != null) {
            synchronized (Main.gameData.figures) {
                Main.gameData.figures.stream().forEach((f1) -> {
                    if (f1.getClass() == Bullet.class && f1.living()) {
                        f1.hit();
                    }
                });
                if (Main.gameData.healthBar.living()) {
                    final EndGameText finish = new EndGameText(Main.gamePanel.getWidth() / 2, -180, "You_Win.png");
                    Main.gameData.figures.add(finish);
                }
                else{
                    final EndGameText finish = new EndGameText(Main.gamePanel.getWidth() / 2, -180, "You_Died.png");
                    Main.gameData.figures.add(finish);
                }
            }
        }
        
    }
    
    private void update() {
        time += delta;

        if (!inPosition) {
            if (time > moveDelay) {
                translate(0, 2);
                moveDistance += 2;
                if (moveDistance >= 300) {
                    inPosition = true;
                    MainWindow.keyListener.noFire = false;
                    moveDistance = 0;
                }
            }
        }
    }
    
    private void translate(int dx, int dy) {
        synchronized (Main.gameData.figures) {
            Main.gameData.figures.stream().forEach((f1) -> {
                if (f1.getClass() == EndGameText.class && f1.alive < 1) {
                    EndGameText finish = (EndGameText) f1;
                    finish.translate(dx, dy);
                    f1 = finish;
                }
            });
        }
    }

    @Override
    public int enemyCount() {
        return 0;
    }

    @Override
    public List<Enemy> getFiringEnemies() {
        update();
        List<Enemy> emptyList = null;
        return emptyList;
    }
}
