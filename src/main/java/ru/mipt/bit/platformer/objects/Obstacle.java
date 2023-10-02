package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.movement.Colliding;

public class Obstacle implements Colliding {
    private final GridPoint2 coordinates;

    public Obstacle(GridPoint2 initialCoordinates) {
        coordinates = initialCoordinates;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean collides(GridPoint2 target) {
        return target.equals(coordinates);
    }
}
