
package model;

import java.util.List;

public interface Level {
    void makeLevel();
    int enemyCount();
    List<Enemy> getFiringEnemies();
}
