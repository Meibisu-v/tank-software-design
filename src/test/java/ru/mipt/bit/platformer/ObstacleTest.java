package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.objects.Obstacle;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

    @Test
    void testCollidesWhenTargetIsSameAsCoordinates() {
        GridPoint2 coordinates = new GridPoint2(1, 2);
        Obstacle obstacle = new Obstacle(coordinates);
        assertTrue(obstacle.collides(coordinates));
    }

    @Test
    void testCollidesWhenTargetIsDifferentFromCoordinates() {
        GridPoint2 coordinates = new GridPoint2(1, 2);
        GridPoint2 differentTarget = new GridPoint2(3, 4);
        Obstacle obstacle = new Obstacle(coordinates);
        assertFalse(obstacle.collides(differentTarget));
    }
    @Test
    public void testGetCoordinates() {
        GridPoint2 initialCoordinates = new GridPoint2(1, 2);
        Obstacle obstacle = new Obstacle(initialCoordinates);

        GridPoint2 result = obstacle.getCoordinates();

        assertEquals(initialCoordinates, result);
    }

}