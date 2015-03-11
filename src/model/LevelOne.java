package model;

import controller.Main;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LevelOne implements Level {

    private int health;
    private String name;
    public int enemyCount;
    private int points;
    public final static List<Enemy> emptyList = new ArrayList<>();
    private final List<Enemy> myEnemies = new ArrayList<>();
    private List<List<Enemy>> pattern;
    double time;
    int row;
    boolean triggered;
    final double delta;
    final double delay;
    final double deathDelay;

    LevelOne() {
        this.health = 1;
        this.name = "Green_Catcher.png";
        this.enemyCount = 10;
        this.points = 100;
        this.pattern = null;
        this.time = 0;
        this.row = 0;
        this.triggered = false;
        this.delta = 1.0 / 40.0;
        this.delay = 0.300;
        this.deathDelay = 0.300;
    }

    @Override
    public void makeLevel() {//one row of ten enemies with one hp each
        if (Main.gameData.figures != null) {
            synchronized (Main.gameData.figures) {
                for (int addedCount = 0; addedCount < enemyCount; addedCount++) {
                    final Enemy enemy = new Enemy(60 + 50 * addedCount, 50, health, name, points);
                    Main.gameData.figures.add(enemy);
                    myEnemies.add(enemy);
                }
            }
            pattern = new ArrayList<>();
            pattern.add(linkListEnemies(myEnemies.get(0), myEnemies.get(9)));
            pattern.add(linkListEnemies(myEnemies.get(1), myEnemies.get(8)));
            pattern.add(linkListEnemies(myEnemies.get(2), myEnemies.get(7)));
            pattern.add(linkListEnemies(myEnemies.get(3), myEnemies.get(6)));
            pattern.add(linkListEnemies(myEnemies.get(4), myEnemies.get(5)));
            pattern.add(linkListEnemies(myEnemies.get(3), myEnemies.get(6)));
            pattern.add(linkListEnemies(myEnemies.get(2), myEnemies.get(7)));
            pattern.add(linkListEnemies(myEnemies.get(1), myEnemies.get(8)));
        }
    }
    
    private void update() {
        time += delta;

        if (time > delay) {
            row++;
            triggered = true;
            time = 0;
        }

        if (row >= pattern.size()) {
            row = 0;
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
