package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(new DirectionCoordinates(0,1)),
    DOWN(new DirectionCoordinates(0,-1)),
    LEFT(new DirectionCoordinates(-1,0)),
    RIGHT(new DirectionCoordinates(1,0)),
    NODIRECTION(new DirectionCoordinates(0, 0));


    private final DirectionCoordinates direction;

    Direction(DirectionCoordinates direction) {
        this.direction = direction;
    }

    public static DirectionCoordinates calcDirection(float angle) {
        for (var directions : Direction.values()) {
            if (Math.abs(angle - directions.getDirection().getAngle()) < 0.1f) {
                return directions.getDirection();
            }
        }
        return null;
    }

    public DirectionCoordinates getDirection() {
        return direction;
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
