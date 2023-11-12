package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.level.LevelGenerator;
import ru.mipt.bit.platformer.movement.Colliding;
import ru.mipt.bit.platformer.movement.CollisionChecker;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {
    @Test
    public void testInitObjectsCheckPlayerPosition() {
        LevelGenerator generator = () -> List.of("T 1 1");
        Level level = new Level(generator);

        CollisionChecker collisionChecker = new CollisionChecker();
        level.initObjects(collisionChecker);
//        Check if objects were initialized correctly
        assertNotNull(level.getPlayableTank());
        assertEquals(1, level.getPlayableTank().getCoordinates().x);
        assertEquals(1, level.getPlayableTank().getCoordinates().y);
    }

    @Test
    public void testIsFreeWhenCollidersReturnFalse() {
        CollisionChecker checker = new CollisionChecker();
        Colliding colliding = new Colliding() {
            @Override
            public boolean collides(GridPoint2 target) {
                return true;
            }
        };
        checker.addColliding(colliding);

        GridPoint2 target = new GridPoint2(1, 1);

        boolean result = checker.isFree(target);

        assertFalse(result);
    }


    @Test
    void testAddTank() {
        LevelGenerator generator = new LevelGenerator() {
            @Override
            public List<String> generate() {
                return List.of("T 1 1");
            }
        };
        CollisionChecker collisionChecker = new CollisionChecker();
        Level level = new Level(generator);
        Tank tank = new Tank(new GridPoint2(1, 1), collisionChecker);

        level.addTank(tank);

        assertEquals(1, level.getTanks().size());
        assertEquals(tank, level.getTanks().get(0));
    }
}