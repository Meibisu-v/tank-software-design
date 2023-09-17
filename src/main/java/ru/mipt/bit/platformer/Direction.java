package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    RIGHT(0),
    UP(90),
    LEFT(-180),
    DOWN(-90),
    NODIRECTION(1);

    private final float angle;
    private Direction(float angle){
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

    public void move(GridPoint2 point) {
        switch (this) {
            case UP:
                ++point.y;
                break;
            case DOWN:
                --point.y;
                break;
            case RIGHT:
                ++point.x;
                break;
            case LEFT:
                --point.x;
                break;
        }
    }
}
