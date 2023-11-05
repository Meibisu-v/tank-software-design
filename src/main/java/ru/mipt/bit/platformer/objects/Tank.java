package ru.mipt.bit.platformer.objects;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.movement.Colliding;
import ru.mipt.bit.platformer.movement.CollisionChecker;
import ru.mipt.bit.platformer.input.Direction;
import ru.mipt.bit.platformer.util.ObjectState;
import ru.mipt.bit.platformer.util.TileUtils;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Tank implements Colliding {
    private GridPoint2 coordinates;
    private final Vector2 position;
    private GridPoint2 destinationCoordinates;
    private static final float MOVEMENT_SPEED = 0.6f;
    private static final GridPoint2 NO_COORDINATES = new GridPoint2(-1, -1);
    public static final float MOVEMENT_COMPLETED = 1f;
    public static final int MOVEMENT_STARTED = 0;
    private float rotation;
    private float movementProgress = MOVEMENT_COMPLETED;
    private final CollisionChecker collisionChecker;
    private final TankState tankState;
    private ObjectState movement;
    private final TileUtils tileUtils;

    public float getSpeed() {
        return MOVEMENT_SPEED;
    }
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public TankState getTankState() {
        return tankState;
    }

    public Tank(GridPoint2 initialCoordinates, CollisionChecker collisionChecker, TileUtils tileUtils,
                TankState tankState) {
        destinationCoordinates = initialCoordinates;
        this.position = tileUtils.calculateTileCenter(destinationCoordinates);
        coordinates = initialCoordinates;
        rotation = 0f;
        this.collisionChecker = collisionChecker;
        this.tankState = tankState;
        this.tileUtils = tileUtils;
        this.movement = new ObjectState(coordinates, coordinates, tileUtils, MOVEMENT_SPEED);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void tryMove(Direction direction) {
        if (movement.finishedMoving()) {
            GridPoint2 newCoordinates = direction.apply(coordinates);
            if (collisionChecker.isFree(newCoordinates) == null) {
                position.add(direction.getVector().x, direction.getVector().y);
                movement = new ObjectState(coordinates, newCoordinates, tileUtils, MOVEMENT_SPEED);
                coordinates = newCoordinates;
            }
            rotation = direction.getAngle();
        }
    }

    public void update(float deltaTime) {
        movement.update(deltaTime);
        position.set(movement.calculatePosition());
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

    @Override
    public boolean collides(GridPoint2 target) {
        return movement.getTrajectoryPoints().contains(target);
    }

    private void startMovement() {
        movementProgress = MOVEMENT_STARTED;
    }

    public void takeDamage(Bullet bullet, Level level) {
        tankState.receiveDamage(bullet);
    }

    public TileUtils getTileUtils() {
        return tileUtils;
    }
}
