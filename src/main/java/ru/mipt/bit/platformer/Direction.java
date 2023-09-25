package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    RIGHT(0),
    UP(90),
    LEFT(-180),
    DOWN(-90),
    NODIRECTION(1);

    private final float angle;
    Direction(float angle){
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

    public GridPoint2 apply(GridPoint2 point) {
        GridPoint2 copyPoint = point.cpy();
        switch (this) {
            case UP:
                ++copyPoint.y;
                break;
            case DOWN:
                --copyPoint.y;
                break;
            case RIGHT:
                ++copyPoint.x;
                break;
            case LEFT:
                --copyPoint.x;
                break;
        }
        return copyPoint;
    }
}
