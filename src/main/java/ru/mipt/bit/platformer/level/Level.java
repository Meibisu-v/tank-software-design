package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.AI.RandomAI;
import ru.mipt.bit.platformer.movement.CollisionChecker;
import ru.mipt.bit.platformer.objects.Border;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final ArrayList<Obstacle> obstacles;
    private Tank playableTank;
    private final ArrayList<Tank> tanks;
    private final LevelGenerator generator;
    private final ArrayList<RandomAI> actors = new ArrayList<>();
    private Border border;
    public Level(LevelGenerator generator) {
        obstacles = new ArrayList<>();
        tanks = new ArrayList<>();
        this.generator = generator;
    }

    public Border getBorder() {
        return border;
    }

    public ArrayList<RandomAI> getActors() {
        return actors;
    }

    public Tank getPlayableTank() {
        return playableTank;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public ArrayList<Tank> getTanks() {
        return tanks;
    }

    public void initBorder(int width, int height) {
        border = new Border(width, height);
    }
    public void initObjects(CollisionChecker collisionChecker) {
        initBorder(10, 8);
        List<String> content = generator.generate();
        for (String line : content) {
            String[] parts = line.split(" ");
            String type = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
//            float speed = 0.2f;
            switch (type) {
                case "X":
                    playableTank = new Tank(new GridPoint2(x, y), collisionChecker);
                    break;
                case "T":
                    obstacles.add(new Obstacle(new GridPoint2(x, y)));
                    break;
                case "E":
                    Tank newTank = new Tank(new GridPoint2(x, y), collisionChecker);
                    tanks.add(newTank);
                    actors.add(new RandomAI(newTank));
                    break;
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
