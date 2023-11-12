package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.Tank;

public class TankGraphics implements Graphics{
    private final Rectangle rectangle;
    private final Batch batch;
    private final TextureRegion textureRegion;
    private final Tank tank;

    public TankGraphics(Tank tank, Batch batch) {
        this.textureRegion = new TextureRegion(new Texture("images/tank_blue.png"));
        this.rectangle = new Rectangle()
                .setWidth(textureRegion.getRegionWidth())
                .setHeight(textureRegion.getRegionHeight());
        this.tank = tank;
        this.batch = batch;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Tank getTank() {
        return tank;
    }

    @Override
    public void render() {
        rectangle.setCenter(tank.getPosition());
        int regionWidth = textureRegion.getRegionWidth();
        int regionHeight = textureRegion.getRegionHeight();
        float regionOriginX = regionWidth / 2f;
        float regionOriginY = regionHeight / 2f;
        batch.draw(textureRegion, rectangle.x, rectangle.y, regionOriginX, regionOriginY, regionWidth, regionHeight,
                1f, 1f, tank.getRotation());

    }

    @Override
    public void delete() {
        textureRegion.getTexture().dispose();
    }
}
