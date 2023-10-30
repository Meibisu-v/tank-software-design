package ru.mipt.bit.platformer.objects;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.movement.Colliding;
import ru.mipt.bit.platformer.movement.CollisionChecker;
import ru.mipt.bit.platformer.input.Direction;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Tank implements Colliding {
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private GridPoint2 coordinatesDuringMovement;
    private static final float MOVEMENT_SPEED = 0.6f;
    private static final GridPoint2 NO_COORDINATES = new GridPoint2(-1, -1);
    public static final float MOVEMENT_COMPLETED = 1f;
    public static final int MOVEMENT_STARTED = 0;
    private float rotation;
    private float movementProgress = MOVEMENT_COMPLETED;
    private final CollisionChecker collisionChecker;

    public float getSpeed() {
        return MOVEMENT_SPEED;
    }
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public Tank(GridPoint2 initialCoordinates, CollisionChecker collisionChecker) {
        destinationCoordinates = initialCoordinates;
        coordinates = new GridPoint2(destinationCoordinates);
        rotation = 0f;
        this.collisionChecker = collisionChecker;
        coordinatesDuringMovement = NO_COORDINATES;
    }

    public void tryMove(Direction direction) {
        if (notMoving() && Direction.NODIRECTION != direction) {
            GridPoint2 newCoordinates = direction.apply(coordinates);
            if (collisionChecker.isFree(newCoordinates)) {
                destinationCoordinates = newCoordinates;
                coordinatesDuringMovement = newCoordinates;
                startMovement();
                rotation = direction.getAngle();
            }
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

            coordinatesDuringMovement = NO_COORDINATES;
        }
    }

    @Override
    public boolean collides(GridPoint2 target) {
        return coordinates.equals(target) || coordinates.equals(coordinatesDuringMovement);
    }

    private void startMovement() {
        movementProgress = MOVEMENT_STARTED;
    }
}
