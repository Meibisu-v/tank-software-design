package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.movement.Colliding;

public class Obstacle implements Colliding {
    private final GridPoint2 coordinates;
    private final Vector2 vectorPosition;

    public Obstacle(GridPoint2 initialCoordinates, Vector2 vectorPosition) {
        coordinates = initialCoordinates;
        this.vectorPosition = vectorPosition;
    }

    public Vector2 getVectorPosition() {
        return vectorPosition;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean collides(GridPoint2 target) {
        return target.equals(coordinates);
    }
}
