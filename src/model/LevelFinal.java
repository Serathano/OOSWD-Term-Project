package model;

import controller.Main;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import view.MainWindow;

public class LevelFinal implements Level {

    private int health;
    private String name;
    public int enemyCount;
    private int points;
    public final static List<Enemy> emptyList = new ArrayList<>();
    private final List<Enemy> myEnemies = new ArrayList<>();
    private List<List<Enemy>> pattern;
    private double time;
    private int row;
    private boolean triggered;
    private final double delta;
    private double delay;
    private final double moveDelay;
    private boolean inPosition;
    private int moveDistance;
    private boolean shiftLeft;
    private int countBosses;
    int xChange;
    
    LevelFinal() {
        this.health = 15;
        this.name = "pheonix_0001.png";
        this.enemyCount = 3;
        this.points = 5000;
        this.pattern = null;
        this.time = 0;
        this.row = 0;
        this.triggered = false;
        this.delta = 1.0 / 40.0;
        this.delay = 0.300;
        this.moveDelay = 0.500;
        this.moveDistance = 0;
        this.shiftLeft = true;
        MainWindow.keyListener.noFire = true;
    }

    @Override
    public void makeLevel() {//one row of ten enemies with one hp each
        if (Main.gameData.figures != null) {
            synchronized (Main.gameData.figures) {
                Main.gameData.figures.stream().forEach((f1) -> {
                    if (f1.getClass() == Bullet.class && f1.living()) {
                        f1.hit();
                    }
                });
                for (int addedCount = 0; addedCount < enemyCount; addedCount++) {
                    final Enemy enemy = new Enemy(Main.gamePanel.getWidth() / 2, -180 + addedCount * 50, health, name, points);
                    Main.gameData.figures.add(enemy);
                    myEnemies.add(enemy);
                }
            }
            pattern = new ArrayList<>();
            pattern.add(linkListEnemies(myEnemies.get(0)));
            pattern.add(linkListEnemies(myEnemies.get(1)));
            pattern.add(linkListEnemies(myEnemies.get(2)));
            pattern.add(linkListEnemies(myEnemies.get(0), myEnemies.get(1)));
            pattern.add(linkListEnemies(myEnemies.get(1), myEnemies.get(2)));
            pattern.add(linkListEnemies(myEnemies.get(0), myEnemies.get(2)));
            pattern.add(linkListEnemies(myEnemies.get(0), myEnemies.get(1), myEnemies.get(2)));
            inPosition = false;
        }
    }

    private void update() {
        time += delta;

        if (!inPosition) {
            if (time > moveDelay) {
                translate(0, 3);
                moveDistance += 3;
                if (moveDistance >= 200) {
                    inPosition = true;
                    MainWindow.keyListener.noFire = false;
                    moveDistance = 0;
                }
            }
        } else {
            if (time > delay) {
                row++;
                triggered = true;
                time = 0;
                translate();
            }

            if (row >= pattern.size()) {
                row = 0;
            }
        }
    }

    private void translate(int dx, int dy) {
        synchronized (Main.gameData.figures) {
            Main.gameData.figures.stream().forEach((f1) -> {
                if (f1.getClass() == Enemy.class && f1.alive < 1) {
                    Enemy enemy = (Enemy) f1;
                    enemy.translate(dx, dy);
                    if (moveDistance >= 200) {
                        enemy.goNext();
                    }
                    f1 = enemy;
                }
            });
        }
    }

    private void translate() {
        countBosses = 0;
        synchronized (Main.gameData.figures) {
            //attempt to track the hero, somewhat
            Hero hero = (Hero) Main.gameData.figures.get(0);
            Main.gameData.figures.stream().forEach((f1) -> {
                if (f1.getClass() == Enemy.class && f1.alive < 1) {
                    Enemy enemy = (Enemy) f1;
                    if(enemy.x >= 0 && enemy.x <= Main.gamePanel.getWidth() - 30){
                        if(enemy.x <= hero.x - 100)
                            shiftLeft = false;
                        else if(enemy.x >= hero.x + 130)
                            shiftLeft = true;
                    }
                    else if(enemy.x <= 0)
                        shiftLeft = false;
                    else if(enemy.x >= Main.gamePanel.getWidth() - 30)
                        shiftLeft = true;
                    switch(countBosses){
                        case 0:
                            xChange = 3;
                            break;
                        case 1:
                            xChange = 6;
                            break;
                        case 2:
                            xChange = 9;
                            break;
                        default:
                            xChange = 0;
                    }
                    if(shiftLeft)
                        xChange = -xChange;
                    enemy.translate(xChange, 0);
                    f1 = enemy;
                    countBosses++;
                }
            });
        }
    }

    @Override

    public int enemyCount() {
        return this.enemyCount;
    }

    @Override
    public List<Enemy> getFiringEnemies() {
        update();
        if (this.triggered) {
            this.triggered = false;
            return this.pattern.get(row);
        }

        return this.emptyList;
    }

    public static List<Enemy> linkListEnemies(Enemy... enemyInput) {
        final List<Enemy> enemies = new LinkedList<>();
        for (final Enemy enemy : enemyInput) {
            enemies.add(enemy);
        }
        return enemies;

    }
}
