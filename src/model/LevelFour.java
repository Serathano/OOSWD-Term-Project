package model;

import controller.Main;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import view.MainWindow;

public class LevelFour implements Level {

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
    final double delta;
    private double delay;
    final double moveDelay;
    private boolean inPosition;
    private int moveDistance;
    private boolean shiftLeft;
    private int downDistance;
    private boolean floorReached;

    LevelFour() {
        this.health = 2;
        this.name = "catcher_blue_1.png";
        this.enemyCount = 20;
        this.points = 230;
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
        this.downDistance = 0;
        floorReached = false;
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
                    if (addedCount < enemyCount / 2) {
                        final Enemy enemy = new Enemy(60 + 50 * addedCount, -110, health, name, points);
                        //final Enemy enemy = new Enemy(60 + 50 * addedCount, 30, health, name, points);
                        Main.gameData.figures.add(enemy);
                        myEnemies.add(enemy);
                    } else {
                        final Enemy enemy = new Enemy(60 + 50 * (addedCount - enemyCount / 2), -50, health, name, points);
                        //final Enemy enemy = new Enemy(60 + 50 * (addedCount - enemyCount / 2), 90, health, name, points);
                        Main.gameData.figures.add(enemy);
                        myEnemies.add(enemy);
                    }
                }
            }
            pattern = new ArrayList<>();
            pattern.add(linkListEnemies(myEnemies.get(0), myEnemies.get(10)));
            pattern.add(linkListEnemies(myEnemies.get(3), myEnemies.get(13)));
            pattern.add(linkListEnemies(myEnemies.get(1), myEnemies.get(11)));
            pattern.add(linkListEnemies(myEnemies.get(2), myEnemies.get(12)));
            pattern.add(linkListEnemies(myEnemies.get(2), myEnemies.get(12)));
            pattern.add(linkListEnemies(myEnemies.get(1), myEnemies.get(11)));
            pattern.add(linkListEnemies(myEnemies.get(3), myEnemies.get(13)));
            pattern.add(linkListEnemies(myEnemies.get(0), myEnemies.get(10)));
            pattern.add(linkListEnemies(myEnemies.get(4), myEnemies.get(14)));
            pattern.add(linkListEnemies(myEnemies.get(1), myEnemies.get(11)));
            pattern.add(linkListEnemies(myEnemies.get(5), myEnemies.get(15)));
            pattern.add(linkListEnemies(myEnemies.get(2), myEnemies.get(12)));
            pattern.add(linkListEnemies(myEnemies.get(6), myEnemies.get(16)));
            pattern.add(linkListEnemies(myEnemies.get(3), myEnemies.get(13)));
            pattern.add(linkListEnemies(myEnemies.get(7), myEnemies.get(17)));
            pattern.add(linkListEnemies(myEnemies.get(4), myEnemies.get(14)));
            pattern.add(linkListEnemies(myEnemies.get(8), myEnemies.get(18)));
            pattern.add(linkListEnemies(myEnemies.get(5), myEnemies.get(15)));
            pattern.add(linkListEnemies(myEnemies.get(9), myEnemies.get(19)));
            pattern.add(linkListEnemies(myEnemies.get(6), myEnemies.get(16)));
            pattern.add(linkListEnemies(myEnemies.get(8), myEnemies.get(18)));
            pattern.add(linkListEnemies(myEnemies.get(7), myEnemies.get(17)));
            pattern.add(linkListEnemies(myEnemies.get(7), myEnemies.get(17)));
            pattern.add(linkListEnemies(myEnemies.get(8), myEnemies.get(18)));
            pattern.add(linkListEnemies(myEnemies.get(6), myEnemies.get(16)));
            pattern.add(linkListEnemies(myEnemies.get(9), myEnemies.get(19)));
            pattern.add(linkListEnemies(myEnemies.get(5), myEnemies.get(15)));
            pattern.add(linkListEnemies(myEnemies.get(8), myEnemies.get(18)));
            pattern.add(linkListEnemies(myEnemies.get(4), myEnemies.get(14)));
            pattern.add(linkListEnemies(myEnemies.get(7), myEnemies.get(17)));
            pattern.add(linkListEnemies(myEnemies.get(3), myEnemies.get(13)));
            pattern.add(linkListEnemies(myEnemies.get(6), myEnemies.get(16)));
            pattern.add(linkListEnemies(myEnemies.get(2), myEnemies.get(12)));
            pattern.add(linkListEnemies(myEnemies.get(5), myEnemies.get(15)));
            pattern.add(linkListEnemies(myEnemies.get(1), myEnemies.get(11)));
            pattern.add(linkListEnemies(myEnemies.get(4), myEnemies.get(14)));
            inPosition = false;
        }
    }

    private void update() {
        time += delta;

        if (!inPosition) {
            if (time > moveDelay) {
                translate(0, 3);
                moveDistance += 3;
                if (moveDistance >= 150) {
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
                if (shiftLeft) {
                    translate(-3, 0);
                    moveDistance -= 3;
                    if (moveDistance < -20) {
                        if (!floorReached) {
                            translate(0, 3);
                            downDistance += 3;
                            if (downDistance >= 50) {
                                floorReached = true;
                            }
                        } else if (floorReached) {
                            translate(0, -3);
                            downDistance -= 3;
                            if (downDistance < 0) {
                                floorReached = false;
                            }
                        }
                        if (delay >= 0.200) {
                            delay -= 0.03;//fires moderately faster with each drop to a floor of 0.30
                        }
                        shiftLeft = !shiftLeft;
                    }
                } else {
                    translate(3, 0);
                    moveDistance += 3;
                    if (moveDistance > 20) {
                        if (!floorReached) {
                            translate(0, 3);
                            downDistance += 3;
                            if (downDistance >= 50) {
                                floorReached = true;
                            }
                        } else if (floorReached) {
                            translate(0, -3);
                            downDistance -= 3;
                            if (downDistance < 0) {
                                floorReached = false;
                            }
                        }
                        if (delay >= 0.200) {
                            delay -= 0.03;//fires moderately faster with each drop to a floor of 0.30
                        }
                        shiftLeft = !shiftLeft;
                    }
                }
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
                    if (moveDistance >= 150) {
                        enemy.goNext();
                    }
                    f1 = enemy;
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
