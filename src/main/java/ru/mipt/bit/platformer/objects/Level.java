package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.LevelGenerator;
import ru.mipt.bit.platformer.movement.CollisionChecker;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final ArrayList<Obstacle> obstacles;
    private Tank playableTank;
    private final List<Tank> tanks;
    private final LevelGenerator generator;

    public Level(LevelGenerator generator) {
        obstacles = new ArrayList<>();
        tanks = new ArrayList<>();
        this.generator = generator;
    }
//    public void dispose() {
//        map.dispose();
//    }

    public Tank getPlayableTank() {
        return playableTank;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<Tank> getTanks() {
        return tanks;
    }


    public void initObjects(CollisionChecker collisionChecker) {
        List<String> content = generator.generate();
        for (String line : content) {
            String[] parts = line.split(" ");
            String type = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
//            float speed = 0.2f;
            switch (type) {
                case "T":
                    playableTank = new Tank(new GridPoint2(x, y), collisionChecker); //TODO add speed
                    break;
                case "O":
                    obstacles.add(new Obstacle(new GridPoint2(x, y)));
                    break;
                case "E":
                    tanks.add(new Tank(new GridPoint2(x, y), collisionChecker));
                default:
                    throw new IllegalArgumentException("Unknown symbol in content of level");
            }
        }
    }
    public void addTank(Tank tank) {
        tanks.add(tank);
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }
}
