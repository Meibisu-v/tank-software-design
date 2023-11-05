package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.input.Direction;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.movement.Colliding;
import ru.mipt.bit.platformer.movement.CollisionChecker;
import ru.mipt.bit.platformer.util.ObjectState;
import ru.mipt.bit.platformer.util.TileUtils;

public class Bullet {
    private final int damage;
    private final Vector2 position;
    private GridPoint2 gridPosition;
    private final float rotation;
    private final float speed;
    private ObjectState objectState;
    private final CollisionChecker colliderManager;
    private final TileUtils tileUtils;
    private final Direction direction;
    private final Level level;

    public Bullet(GridPoint2 gridPosition, Direction direction, int damage, float speed, CollisionChecker colliderManager,
                  TileUtils tileUtils, Level level) {
        this.damage = damage;
        this.gridPosition = gridPosition;
        this.position = tileUtils.calculateTileCenter(gridPosition);
        this.rotation = direction.getAngle();
        this.speed = speed;
        this.colliderManager = colliderManager;
        this.tileUtils = tileUtils;
        this.direction = direction;
        this.level = level;
    }

    public int getDamage() {
        return damage;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public void checkCollisions() {
        Colliding collider = colliderManager.isFree(gridPosition);
        if (collider != null) {
            level.removeBullet(this);
        }
        if (collider instanceof Tank) {
            ((Tank) collider).takeDamage(this, level);
        }
    }

    public void update(float deltaTime) {
        if (objectState != null) {
            objectState.update(deltaTime);
        }
        if (objectState == null || objectState.halfWayMoving()) {
            checkCollisions();
        }
        if (objectState == null || objectState.finishedMoving()) {
            GridPoint2 prevPosition = gridPosition.cpy();

            gridPosition = direction.apply(prevPosition);

            objectState = new ObjectState(prevPosition, gridPosition, tileUtils, speed);
        }
        if (objectState != null) {
            position.set(objectState.calculatePosition());
        }
    }
}