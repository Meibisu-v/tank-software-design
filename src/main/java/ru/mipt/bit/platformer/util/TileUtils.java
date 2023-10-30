package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class TileUtils {
    private final GridPoint2 tileSize;

    public TileUtils(GridPoint2 tileSize) {
        this.tileSize = tileSize;
    }

    public GridPoint2 getTileSize() {
        return tileSize;
    }

    public Vector2 calculateTileCenter(GridPoint2 tileCoordinates) {
        int tileWidth = tileSize.x;
        int tileHeight = tileSize.y;
        int tileBottomLeftCornerX = tileCoordinates.x * tileWidth;
        int tileBottomLeftCornerY = tileCoordinates.y * tileHeight;

        return new Rectangle()
                .setX(tileBottomLeftCornerX)
                .setY(tileBottomLeftCornerY)
                .setWidth(tileWidth)
                .setHeight(tileHeight)
                .getCenter(new Vector2());
    }

    public Vector2 calculatePositionBetween(GridPoint2 fromTileCoordinates, GridPoint2 toTileCoordinates, float progress) {
        Vector2 fromTileCenter = calculateTileCenter(fromTileCoordinates);
        Vector2 toTileCenter = calculateTileCenter(toTileCoordinates);
        Interpolation interpolation = Interpolation.smooth;
        float intermediateX = interpolation.apply(fromTileCenter.x, toTileCenter.x, progress);
        float intermediateY = interpolation.apply(fromTileCenter.y, toTileCenter.y, progress);
        return new Vector2().set(intermediateX, intermediateY);
    }
}