package model;

import java.util.List;

public class MakeALevel {

    private Level level;

    MakeALevel() {
        this.level = new LevelNULL();
    }

    MakeALevel(Level level) {
        this.level = level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void changeLevel() {
        if (this.level.getClass() == LevelNULL.class) {
            this.level = new LevelOne();
        } else if (this.level.getClass() == LevelOne.class) {
            this.level = new LevelTwo();
        } else if (this.level.getClass() == LevelTwo.class) {
            this.level = new LevelThree();
        } else if (this.level.getClass() == LevelThree.class) {
            this.level = new LevelFour();
        } else if (this.level.getClass() == LevelFour.class) {
            this.level = new LevelFinal();
        } else if (this.level.getClass() == LevelFinal.class){
            this.level = new LevelGameOver();
        }
    }

    public boolean gameOver() {
        return this.level.getClass() == LevelGameOver.class;
    }

    public int enemyCount() {
        return this.level.enemyCount();
    }

    public List<Enemy> getFiringEnemies() {
        return this.level.getFiringEnemies();
    }

    void createLevel() {
        this.level.makeLevel();
    }
}
