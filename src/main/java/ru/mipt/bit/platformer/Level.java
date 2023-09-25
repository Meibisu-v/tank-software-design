package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;

public class Level {
    private TiledMap map;
    private Obstacle treeObstacle;
    private Tank tank;

    public Level() {
        map = new TmxMapLoader().load("level.tmx");
        tank = new Tank(new GridPoint2(1, 1));
        treeObstacle = new Obstacle(new GridPoint2(1, 3));
    }
    public void dispose() {
        map.dispose();
    }

    public Tank getTank() {
        return tank;
    }

    public Obstacle getTreeObstacle() {
        return treeObstacle;
    }

    public TiledMap getMap() {
        return map;
    }
}
