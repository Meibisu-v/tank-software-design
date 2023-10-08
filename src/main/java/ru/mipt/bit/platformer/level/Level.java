package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.movement.CollisionChecker;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.Tank;

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
                case "X":
                    playableTank = new Tank(new GridPoint2(x, y), collisionChecker); //TODO add speed
                    break;
                case "T":
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
