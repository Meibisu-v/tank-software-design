package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public class Obstacle {
    private final GridPoint2 coordinates;

    public Obstacle(GridPoint2 initialCoordinates) {
        coordinates = initialCoordinates;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

}
