package ru.mipt.bit.platformer;
import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Tank {
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private static final float MOVEMENT_SPEED = 0.4f;

    public static final float MOVEMENT_COMPLETED = 1f;
    public static final int MOVEMENT_STARTED = 0;
    private float rotation;
    private float movementProgress = MOVEMENT_COMPLETED;

    public float getSpeed() {
        return MOVEMENT_SPEED;
    }
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public Tank(GridPoint2 initialCoordinates) {
        destinationCoordinates = initialCoordinates;
        coordinates = new GridPoint2(destinationCoordinates);
        rotation = 0f;
    }

    private boolean canMove(Direction direction, GridPoint2 obstacleCoordinates) {
        GridPoint2 newCoordinates = direction.apply(this.coordinates);
        return !newCoordinates.equals(obstacleCoordinates);
    }


    public void tryMove(Obstacle obstacle, Direction direction) {
        GridPoint2 obstacleCoordinates = obstacle.getCoordinates();
        if (notMoving() && Direction.NODIRECTION != direction) {
            if (canMove(direction, obstacleCoordinates)) {
                destinationCoordinates = direction.apply(destinationCoordinates);
                startMovement();
            }
            rotation = direction.getAngle();
        }
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public float getRotation() {
        return rotation;
    }

    private boolean notMoving() {
        return isEqual(movementProgress, MOVEMENT_COMPLETED);
    }

    public void tryReachDestinationCoordinates(float newMovementProgress) {
        movementProgress = newMovementProgress;
        if (isEqual(newMovementProgress, MOVEMENT_COMPLETED)) {
            // record that the player has reached his/her destination
            coordinates = destinationCoordinates;
        }
    }
    private void startMovement() {
        movementProgress = MOVEMENT_STARTED;
    }
}
