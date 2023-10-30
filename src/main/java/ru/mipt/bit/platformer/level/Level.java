package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.AI.RandomAI;
import ru.mipt.bit.platformer.movement.CollisionChecker;
import ru.mipt.bit.platformer.objects.*;
import ru.mipt.bit.platformer.util.EventListener;
import ru.mipt.bit.platformer.util.EventManager;
import ru.mipt.bit.platformer.util.TileUtils;

import java.util.ArrayList;
import java.util.List;

public class Level implements EventManager {
    private final ArrayList<Obstacle> obstacles;
    private final ArrayList<Object> bullets;
    private final ArrayList<EventListener> observers;
    private final ArrayList<Object> destroyedBullets;
    private Tank playableTank;
    private final ArrayList<Tank> tanks;
    private final LevelGenerator generator;
    private final ArrayList<RandomAI> actors = new ArrayList<>();
    private Border border;
    public Level(LevelGenerator generator) {
        obstacles = new ArrayList<>();
        tanks = new ArrayList<>();
        this.generator = generator;
        bullets = new ArrayList<>();
        observers = new ArrayList<>();
        destroyedBullets = new ArrayList<>();
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
    public void initObjects(CollisionChecker collisionChecker, TileUtils tileUtils) {
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
                    playableTank = new Tank(new GridPoint2(x, y), collisionChecker, tileUtils, new TankState());
                    observers.forEach(observer -> observer.update("add", playableTank));
                    break;
                case "T":
                    obstacles.add(new Obstacle(new GridPoint2(x, y), tileUtils.calculateTileCenter(new GridPoint2(x, y))));
                    observers.forEach(observer -> obstacles.forEach((obstacle -> observer.update("add", obstacle))));
                    break;
                case "E":
                    Tank newTank = new Tank(new GridPoint2(x, y), collisionChecker, tileUtils, new TankState(2));
                    tanks.add(newTank);
                    observers.forEach(observer -> tanks.forEach(enemy -> observer.update("add", enemy)));
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

    public void update(float deltaTime) {
        for (var tank : tanks) {
            tank.update(deltaTime);
        }
        playableTank.update(deltaTime);
    }
    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public void removeBullet(Bullet bullet) {
        destroyedBullets.add(bullet);
        observers.forEach(observer -> observer.update("erase", bullet));
    }

    @Override
    public void subscribe(EventListener eventListener) {
        observers.add(eventListener);
    }

    @Override
    public void unsubscribe(EventListener eventListener) {
        observers.remove(eventListener);
    }
}
