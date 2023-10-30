package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.Obstacle;

public class ObstacleGraphics implements Graphics{
    private final Rectangle rectangle;
    private final Batch batch;
    private final TextureRegion textureRegion;
    private final Obstacle obstacle;

    public ObstacleGraphics(Obstacle obstacle, Batch batch) {
        this.textureRegion = new TextureRegion(new Texture("images/greenTree.png"));
        this.rectangle =  new Rectangle().setWidth(textureRegion.getRegionWidth()).setHeight(textureRegion.getRegionHeight());
        this.obstacle = obstacle;
        this.batch = batch;
    }

    public void render() {
        rectangle.setCenter(obstacle.getVectorPosition());
        int regionWidth = textureRegion.getRegionWidth();
        int regionHeight = textureRegion.getRegionHeight();
        float regionOriginX = regionWidth / 2f;
        float regionOriginY = regionHeight / 2f;
        batch.draw(textureRegion, rectangle.x, rectangle.y, regionOriginX, regionOriginY, regionWidth, regionHeight,
                1f, 1f, 0f);

    }

    public void delete() {
        textureRegion.getTexture().dispose();
    }
}
