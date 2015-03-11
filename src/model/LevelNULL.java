
package model;

import java.util.List;


public class LevelNULL implements Level{
    int health = 0;
    
    LevelNULL(){}
    
    @Override
    public void makeLevel() {//nothing generated here.
    }

    @Override
    public int enemyCount() {
        return 0;
    }

    @Override
    public List<Enemy> getFiringEnemies() {
        List<Enemy> emptyList = null;
        return emptyList;
    }
    
}
