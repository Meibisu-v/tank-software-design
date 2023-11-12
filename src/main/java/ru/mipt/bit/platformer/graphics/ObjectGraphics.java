package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class ObjectGraphics {
    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;

    public ObjectGraphics(String textureFile)  {
        texture = new Texture(textureFile);
        graphics = new TextureRegion(texture);
        rectangle = createBoundingRectangle(graphics);
    }
    public void draw(Batch batch, float rotation) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }

    public void moveRectangle(TiledMapTileLayer groundLayer, GridPoint2 coordinates){
        moveRectangleAtTileCenter(groundLayer, rectangle, coordinates);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void dispose() {
        texture.dispose();
    }
}
