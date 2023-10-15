package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.movement.CollisionChecker;
import ru.mipt.bit.platformer.movement.Direction;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {
    @Test
    public void testInit() {
        CollisionChecker colliderManager = new CollisionChecker();
        colliderManager.addColliding(new Obstacle(new GridPoint2(2, 3)));
        colliderManager.addColliding(new Obstacle(new GridPoint2(3, 2)));
        Tank tank = new Tank(new GridPoint2(3, 3), colliderManager);
        assertEquals(new GridPoint2(3, 3), tank.getCoordinates());
    }
    @Test
    public void testMoveToEmptyPoint() {
        CollisionChecker colliderManager = new CollisionChecker();
        GridPoint2 tankCoordinates = new GridPoint2(2, 2);
        Tank tank = new Tank(tankCoordinates, colliderManager);

        assertEquals(tankCoordinates, tank.getCoordinates());
        List<Obstacle> obstacles = new ArrayList<>();

        tank.tryMove(obstacles, Direction.RIGHT);
        tank.tryReachDestinationCoordinates(1f);
        assertEquals( new GridPoint2(3, 2), tank.getCoordinates());

        tank.tryMove(obstacles, Direction.LEFT);
        assertEquals(0f, tank.getMovementProgress());
        tank.tryReachDestinationCoordinates(1f);
        assertEquals(1f, tank.getMovementProgress());
        assertEquals( new GridPoint2(2, 2), tank.getCoordinates());

        tank.tryMove(obstacles, Direction.UP);
        tank.tryReachDestinationCoordinates(1f);
        assertEquals( new GridPoint2(2, 3), tank.getCoordinates());
        tank.tryMove(obstacles, Direction.DOWN);
        tank.tryReachDestinationCoordinates(1f);
        assertEquals( new GridPoint2(2, 2), tank.getCoordinates());
    }
    @Test
    public void testTryMoveInSingleObstacleLeft() {
        CollisionChecker colliderManager = new CollisionChecker();
        colliderManager.addColliding(new Obstacle(new GridPoint2(2, 3)));
        colliderManager.addColliding(new Obstacle(new GridPoint2(3, 2)));
        GridPoint2 tankCoordinates = new GridPoint2(2, 2);
        Tank tank = new Tank(tankCoordinates, colliderManager);

        ArrayList<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(new GridPoint2(1, 1)));
        tank.tryMove(obstacles, Direction.LEFT);

        assertEquals(tank.getCoordinates(), tankCoordinates);
    }
    @Test
    public void testTryMoveInSingleObstacleRight() {
        CollisionChecker colliderManager = new CollisionChecker();
        colliderManager.addColliding(new Obstacle(new GridPoint2(2, 3)));
        colliderManager.addColliding(new Obstacle(new GridPoint2(3, 2)));
        GridPoint2 tankCoordinates = new GridPoint2(2, 2);
        Tank tank = new Tank(tankCoordinates, colliderManager);

        ArrayList<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(new GridPoint2(3, 2)));
        tank.tryMove(obstacles, Direction.RIGHT);

        assertEquals(tank.getCoordinates(), tankCoordinates);
    }
    @Test
    public void testTryMoveInSingleObstacleUp() {
        CollisionChecker colliderManager = new CollisionChecker();
        colliderManager.addColliding(new Obstacle(new GridPoint2(2, 3)));
        colliderManager.addColliding(new Obstacle(new GridPoint2(3, 2)));
        GridPoint2 tankCoordinates = new GridPoint2(2, 2);
        Tank tank = new Tank(tankCoordinates, colliderManager);

        ArrayList<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(new GridPoint2(2, 3)));
        tank.tryMove(obstacles, Direction.UP);

        assertEquals(tank.getCoordinates(), tankCoordinates);
    }
    @Test
    public void testTryMoveInSingleObstacleDown() {
        CollisionChecker colliderManager = new CollisionChecker();
        colliderManager.addColliding(new Obstacle(new GridPoint2(2, 3)));
        colliderManager.addColliding(new Obstacle(new GridPoint2(2, 1)));
        GridPoint2 tankCoordinates = new GridPoint2(2, 2);
        Tank tank = new Tank(tankCoordinates, colliderManager);

        ArrayList<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(new GridPoint2(2, 3)));
        obstacles.add(new Obstacle(new GridPoint2(2, 1)));
        tank.tryMove(obstacles, Direction.DOWN);

        assertEquals(tank.getCoordinates(), tankCoordinates);
    }
    @Test
    public void testTryMoveWithMultipleObstacles() {
        CollisionChecker colliderManager = new CollisionChecker();
        colliderManager.addColliding(new Obstacle(new GridPoint2(2, 3)));
        colliderManager.addColliding(new Obstacle(new GridPoint2(3, 2)));
        GridPoint2 tankCoordinates = new GridPoint2(2, 2);
        Tank tank = new Tank(tankCoordinates, colliderManager);

        ArrayList<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(new GridPoint2(1, 2)));

        tank.tryMove(obstacles, Direction.RIGHT);
        assertEquals(tankCoordinates, tank.getCoordinates());

        tank.tryMove(obstacles, Direction.LEFT);
        assertEquals(tankCoordinates, tank.getCoordinates());

        tank.tryMove(obstacles, Direction.UP);
        assertEquals(tankCoordinates, tank.getCoordinates());

        tank.tryMove(obstacles, Direction.DOWN);
        assertEquals(tankCoordinates, tank.getCoordinates());
    }

    @Test
    void testGetSpeed() {
        CollisionChecker colliderManager = new CollisionChecker();
        GridPoint2 tankCoordinates = new GridPoint2(2, 2);
        Tank tank = new Tank(tankCoordinates, colliderManager);

        assertEquals(0.4f, tank.getSpeed());
    }
    @Test
    void testCollides() {
        CollisionChecker colliderManager = new CollisionChecker();
        GridPoint2 tankCoordinates = new GridPoint2(2, 2);
        Tank tank = new Tank(tankCoordinates, colliderManager);

        GridPoint2 target = new GridPoint2(2, 2);
        assertTrue(tank.collides(target)); // Tank should collide with target

        target = new GridPoint2(3, 2);
        assertFalse(tank.collides(target)); // Tank should not collide with target
    }
}