package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.badlogic.gdx.math.MathUtils.isEqual;

public class ObjectState {
    private float progress;
    private final float speed;
    private final GridPoint2 toGridPosition;
    private final GridPoint2 fromGridPosition;
    private final TileUtils tileUtils;


    public ObjectState(GridPoint2 fromGridPosition, GridPoint2 toGridPosition, TileUtils tileUtils, float speed) {
        this.toGridPosition = toGridPosition;
        this.fromGridPosition = fromGridPosition;
        this.speed = speed;
        this.tileUtils = tileUtils;
        progress = 0f;
    }

    public void update(float deltaTime) {
        progress = clamp(progress + deltaTime / speed, 0f, 1f);
        if (isEqual(progress, 1f)) {
            fromGridPosition.set(toGridPosition);
        }
    }

    public boolean finishedMoving() {
        return toGridPosition.equals(fromGridPosition) || isEqual(progress, 1f);
    }

    public boolean halfWayMoving() {
        return progress > 0.5f;
    }

    public Vector2 calculatePosition() {
        return tileUtils.calculatePositionBetween(fromGridPosition, toGridPosition, progress);
    }

    public List<GridPoint2> getTrajectoryPoints() {
        return Arrays.asList(fromGridPosition.cpy(), toGridPosition.cpy());
    }

}
