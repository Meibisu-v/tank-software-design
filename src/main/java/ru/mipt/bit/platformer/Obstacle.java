package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Obstacle {
    private final Texture texture;
    private final TextureRegion graphics;
    private final GridPoint2 coordinates;
    private final Rectangle rectangle;

    public Obstacle(String imagePath, GridPoint2 initialCoordinates) {
        texture = new Texture(imagePath);
        graphics = new TextureRegion(texture);
        coordinates = initialCoordinates;
        rectangle = createBoundingRectangle(graphics);
    }
    public Texture getTexture() {
        return texture;
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
