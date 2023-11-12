package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.Bullet;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class BulletGraphics implements Graphics{
    private final Rectangle rectangle;
    private final Batch batch;
    private final TextureRegion textureRegion;
    private final Bullet bullet;

    public BulletGraphics(Bullet bullet, Batch batch)  {
        this.textureRegion = new TextureRegion(new Texture("images/pngwing.com.png"));
        this.rectangle = new Rectangle().setWidth(textureRegion.getRegionWidth())
                .setHeight(textureRegion.getRegionHeight());

        this.batch = batch;
        this.bullet = bullet;
    }
    public void render() {
        rectangle.setCenter(bullet.getPosition());
        drawTextureRegionUnscaled(batch, textureRegion, rectangle, 0f);

    }
    public void delete() {
        textureRegion.getTexture().dispose();
    }
}
