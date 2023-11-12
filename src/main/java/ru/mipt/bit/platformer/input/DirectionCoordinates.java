package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.math.GridPoint2;

public class DirectionCoordinates {
    private final GridPoint2 deltaCoordinate;
    private final float angle;

    public DirectionCoordinates(int x, int y) {
        deltaCoordinate = new GridPoint2(x, y);
        angle = (float) (Math.atan2(y, x) * 180 / Math.PI);
    }

    public float getAngle() {
        return angle;
    }

    public GridPoint2 getDeltaCoordinate() {
        return new GridPoint2(deltaCoordinate);
    }

}
