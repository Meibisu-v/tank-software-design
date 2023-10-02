package ru.mipt.bit.platformer.movement;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.Tank;

import static org.junit.jupiter.api.Assertions.*;

class CollisionCheckerTest {
    private CollisionChecker colliderManager;

    @BeforeEach
    void setUp() {
        colliderManager = new CollisionChecker();
    }

    @Test
    void colliderTankMovementTest() {
        colliderManager.addColliding(new Obstacle(new GridPoint2(2, 3)));
        colliderManager.addColliding(new Obstacle(new GridPoint2(3, 2)));
        Tank tank = new Tank(new GridPoint2(3, 3), colliderManager);
        assertAll(
                () -> assertFalse(tank.collides(new GridPoint2(5, 5))),
                () -> assertFalse(tank.collides(new GridPoint2(2, 3))),
                () -> assertTrue(tank.collides(new GridPoint2(3, 3))),
                () -> assertFalse(tank.collides(new GridPoint2(3, 2)))
        );
        assertAll(
                () -> assertTrue(colliderManager.isFree(new GridPoint2(5, 5))),
                () -> assertFalse(colliderManager.isFree(new GridPoint2(2, 3))),
                () -> assertFalse(colliderManager.isFree(new GridPoint2(3, 2)))
        );
    }

    @Test
    public void testIsFreeWhenNoColliders() {
        GridPoint2 target = new GridPoint2(1, 1);

        boolean result = colliderManager.isFree(target);

        assertTrue(result);
    }


    @Test
    public void testIsFreeWhenCollidersReturnTrue() {
        Colliding colliding = new Colliding() {
            @Override
            public boolean collides(GridPoint2 target) {
                return false;
            }
        };
        colliderManager.addColliding(colliding);

        GridPoint2 target = new GridPoint2(1, 1);

        boolean result = colliderManager.isFree(target);

        assertTrue(result);
    }

    @Test
    void testIsFreeWhenCollidersDoNotCollide() {
        GridPoint2 target = new GridPoint2(0, 0);
        Colliding colliding = new Colliding() {
            @Override
            public boolean collides(GridPoint2 point) {
                return false;
            }
        };
        colliderManager.addColliding(colliding);
        assertTrue(colliderManager.isFree(target));
    }
    @Test
    void testIsFreeWhenCollidersCollide() {
        GridPoint2 target = new GridPoint2(0, 0);
        Colliding colliding = new Colliding() {
            @Override
            public boolean collides(GridPoint2 point) {
                return true;
            }
        };
        colliderManager.addColliding(colliding);
        assertFalse(colliderManager.isFree(target));
    }
    @Test
    void testAddColliding() {
        Colliding colliding = new Colliding() {
            @Override
            public boolean collides(GridPoint2 point) {
                return true;
            }
        };
        colliderManager.addColliding(colliding);
        assertEquals(1, colliderManager.getColliders().size());
        assertSame(colliding, colliderManager.getColliders().get(0));
    }
}