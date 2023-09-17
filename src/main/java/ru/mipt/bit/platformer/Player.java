package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Player {
    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private GridPoint2 coordinates;
    // which tile the player want to go next
    private final GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    public Rectangle getRectangle() {
        return rectangle;
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public float getRotation() {
        return rotation;
    }

    public Texture getTexture() {
        return texture;
    }

    public Player(String imagePath, GridPoint2 initialCoordinates) {
        texture = new Texture(imagePath);
        graphics = new TextureRegion(texture);
        rectangle = createBoundingRectangle(graphics);
        // set player initial position
        destinationCoordinates = initialCoordinates;
        coordinates = new GridPoint2(destinationCoordinates);
        rotation = 0f;
    }


    private boolean canMove(Direction direction, GridPoint2 obstacleCoordinates) {
        GridPoint2 newCoordinates = new GridPoint2(this.coordinates);
        direction.move(newCoordinates);
        return !newCoordinates.equals(obstacleCoordinates);
    }

    private boolean startMovement() {
        return isEqual(movementProgress, 1f);
    }

    private void stopMovement() {
        movementProgress = 0f;
    }

    public void tryMove(Obstacle obstacle, Direction direction) {
        GridPoint2 treeObstacleCoordinates = obstacle.getCoordinates();
        if (startMovement() && Direction.NODIRECTION != direction) {
            if (canMove(direction, treeObstacleCoordinates)) {
                direction.move(destinationCoordinates);
                stopMovement();
            }
            rotation = direction.getAngle();
        }
    }

    public void continuePlayerProgress(float MOVEMENT_SPEED) {
        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(movementProgress, 1f)) {
            // record that the player has reached his/her destination
            coordinates = destinationCoordinates;
        }
    }
}
