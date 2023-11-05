package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.input.ToggleListener;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileUtils;

public class StatsGraphics implements Graphics{
    private final ToggleListener toggleListener;
    private final TankGraphics tankGraphics;
    private final Batch batch;
    private final TileUtils tileUtils;
    TextureRegion healthBar;
    public StatsGraphics(TankGraphics tankGraphics, ToggleListener toggleListener, Batch batch, TileUtils tileUtils) {
        this.tankGraphics = tankGraphics;
        this.tileUtils = tileUtils;
        this.batch = batch;
        this.toggleListener = toggleListener;
//        healthBar = createRedBar(tankGraphics.getTank().getTankState().getHealth(), Color.RED);
    }
    private void drawHealthBar(Batch batch, Rectangle tankRectangle, int health) {
        TextureRegion healthBgBar = createRedBar(100, Color.RED);
        TextureRegion healthLeftBar = createRedBar(health, Color.BLUE);
        Rectangle hpRectangle = new Rectangle(tankRectangle);
        hpRectangle.y += 90;
        GdxGameUtils.drawTextureRegionUnscaled(batch, healthBgBar, hpRectangle, 0);
        GdxGameUtils.drawTextureRegionUnscaled(batch, healthLeftBar, hpRectangle, 0);}

    private static TextureRegion createRedBar(int health, Color color) {
        Pixmap pixmap = new Pixmap(90 * health / 100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, 90 * health / 100, 20);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        TextureRegion healthBar = new TextureRegion(texture);
        return healthBar;
    }

    @Override
    public void render() {
        tankGraphics.render();
        if (toggleListener.toggled()) {
            int health = tankGraphics.getTank().getTankState().getHealth();
            int healthLeft = tankGraphics.getTank().getTankState().getCurHealth();
            drawHealthBar(batch, tankGraphics.getRectangle(), healthLeft * 100 / health);
        }
    }

    @Override
    public void delete() {
        tankGraphics.delete();
    }
}
