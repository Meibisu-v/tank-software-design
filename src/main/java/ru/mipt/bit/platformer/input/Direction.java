package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(new GridPoint2(0,1), 90),
    DOWN(new GridPoint2(0,-1), -90),
    LEFT(new GridPoint2(-1,0), -180),
    RIGHT(new GridPoint2(1,0), 0),
    NODIRECTION(new GridPoint2(0, 0), 1);


    private final GridPoint2 vector;

    private final float angle;

    Direction(GridPoint2 vector, float angle){
        this.vector = vector;
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

    public GridPoint2 getVector() {
        return vector;
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
