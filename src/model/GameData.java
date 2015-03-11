package model;

import controller.Main;
import view.GamePanel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import view.MainWindow;

public class GameData {

    private static int RADIUS = 2;
    private static int TIMING = 10;
    public final List<GameFigure> figures;
    public HealthBar healthBar;
    public int score;
    private int delay;
    private int enemies;
    public MakeALevel leveller;

    public GameData() {
        figures = Collections.synchronizedList(new ArrayList<GameFigure>());
        healthBar = new HealthBar(0, GamePanel.PHEIGHT - 15, 3);
        figures.add(new Hero((GamePanel.PWIDTH / 2) - 16, GamePanel.PHEIGHT - 60));
        leveller = new MakeALevel(new LevelNULL());
        Random r = new Random();
        this.add(40);
        this.score = 0;
        delay = r.nextInt(TIMING);
    }

    public final void add(int n) {
        Random r = new Random();
        //this.enemies += n;
        synchronized (figures) {
            for (int i = 0; i < n; i++) {
                figures.add(new Star(r.nextInt(GamePanel.PWIDTH),
                        r.nextInt(GamePanel.PHEIGHT), RADIUS, Color.WHITE));
            }
        }
    }

    public final void newLevel() {
        leveller.changeLevel();
        enemies = leveller.enemyCount();
        leveller.createLevel();
    }

    public void update() {
        delay--;
        Random r = new Random();
        synchronized (figures) {
            //movement for Hero happens here to faciliatate checks and balances
            if (MainWindow.keyListener.goLeft != MainWindow.keyListener.goRight && !MainWindow.keyListener.noMove) {
                Hero hero = (Hero) figures.get(0);
                if (MainWindow.keyListener.goLeft) {
                    if (hero.x <= 0) {
                        MainWindow.keyListener.goLeft = false;
                    } else {
                        hero.translate(-5, 0);
                    }
                }

                if (MainWindow.keyListener.goRight) {
                    if (hero.x + 30 >= Main.gamePanel.getWidth()) {
                        MainWindow.keyListener.goRight = false;
                    } else {
                        hero.translate(5, 0);
                    }
                }
            }
            //main update loop
            figures.stream().forEach((f) -> {
                f.update();
            });
            //collisions and kills, obviously
            collisionCheck();
            if (delay <= 0) {
                figures.add(new Star(r.nextInt(GamePanel.PWIDTH), -5, RADIUS, Color.WHITE));
                delay = r.nextInt(TIMING);
            }
            //animate the characters
            figures.stream().forEach((f1) -> {
                if (f1.getClass().equals(Enemy.class) || f1.getClass().equals(Hero.class)) {
                    //animate enemy ships
                    if (f1.getClass().equals(Enemy.class)) {
                        Enemy enemy = (Enemy) f1;
                        if (enemy.getState().getClass() != ShipStateAlive.class && enemy.getState().getClass() != ShipStateDead.class) {
                            enemy.deathTimer += 1.0 / 40.0;
                            if (enemy.deathTimer > 0.100) {
                                if (enemy.getState().getClass().equals(ShipStateImpervious.class)) {
                                    //allows for a second of respite after respawn to reorient
                                    if (enemy.deathTimer > 1.0) {
                                        enemy.goNext();
                                        enemy.deathTimer = 0;
                                    }
                                } else {
                                    enemy.goNext();
                                    enemy.deathTimer = 0;
                                }
                            }
                        }
                        f1 = enemy;
                    }
                    //animte the user character.
                    if (f1.getClass().equals(Hero.class)) {
                        Hero hero = (Hero) f1;
                        if (hero.getState().getClass() != ShipStateAlive.class && hero.getState().getClass() != ShipStateDead.class) {
                            hero.deathTimer += 1.0 / 40.0;
                            if (hero.deathTimer > 0.100) {
                                if (hero.getState().getClass().equals(ShipStateImpervious.class)) {
                                    //allows for a second of respite after respawn to reorient
                                    if (hero.deathTimer > 1.0) {
                                        hero.goNext();
                                        hero.deathTimer = 0;
                                    }
                                } else {
                                    hero.goNext();
                                    hero.deathTimer = 0;
                                }
                            }
                        }
                        f1 = hero;
                    }
                }
            });
        }
    }

    public void collisionCheck() {
        List<GameFigure> removeDead = new ArrayList<>();
        synchronized (figures) {
            //fires according to what enemies are specified my the level data
            final List<Enemy> toShoot = this.leveller.getFiringEnemies();
            if (toShoot != null) {
                toShoot.stream().filter((f) -> (f.living())).forEach((f) -> {
                    figures.add(new Bullet(f.x + 16, f.y + 15, Color.ORANGE.brighter(), true));
                });
            }
            if(!healthBar.living() && MainWindow.notOver){
                leveller.setLevel(new LevelGameOver());
                leveller.createLevel();
            }
            
            //removes dead objects(hero, stars, bullets, enemies
            figures.stream().forEach((f1) -> {
                if (f1.getClass() == Bullet.class && f1.living()) {
                    figures.stream().forEach((f2) -> {
                        if ((f2.getClass() == Enemy.class || f2.getClass() == Hero.class) && f2.alive < 1 && f1.getIFF() != f2.getIFF()) {
                            if (f1.getCollisionBox().intersects(f2.getCollisionBox())) {
                                f1.hit();
                                int points = f2.hit();
                                if (points > 0) {
                                    score += points;
                                    enemies--;
                                }
                            }
                        }
                    });
                }
            });
            figures.stream().forEach((f) -> {
                if (f.alive > 1) {
                    removeDead.add(f);
                }
            });
            figures.removeAll(removeDead);
            //if no more enemies, game over
            if (enemies <= 0 && !leveller.gameOver()) {
                newLevel();
            }
            
            
            //else, displays enemies and score.
            if (MainWindow.notOver) {
                MainWindow.message.setText(score + " Points with " + enemies + " Left in level");
            }
        }
    }
}
