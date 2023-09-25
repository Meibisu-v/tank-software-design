package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class Graphics {
    private Batch batch;
    private MapRenderer levelRenderer;
    private ObjectGraphics tankObjectGraphics;
    private ObjectGraphics treeObjectGraphics;
    private TileMovement tileMovement;
    private Level level;

    public Graphics(Level level) {
        this.level = level;
        batch = new SpriteBatch();
        levelRenderer = createSingleLayerMapRenderer(level.getMap(), batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level.getMap());
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        tankObjectGraphics = new ObjectGraphics("images/tank_blue.png");

        treeObjectGraphics = new ObjectGraphics("images/greenTree.png");

        treeObjectGraphics.moveRectangle(groundLayer, level.getTreeObstacle().getCoordinates());

    }
    public void renderGame() {
        levelRenderer.render();
        // start recording all drawing commands
        batch.begin();
        // render player
        tankObjectGraphics.draw(batch, level.getTank().getRotation());
        // render tree obstacle
        treeObjectGraphics.draw(batch, 0f);
        // submit all drawing requests
        batch.end();
    }

    public void dispose() {
        tankObjectGraphics.dispose();
        treeObjectGraphics.dispose();
        batch.dispose();
    }

    public void calculateInterpolatedCoordinates() {
        // calculate interpolated player screen coordinates
        Tank tank = level.getTank();
        tileMovement.moveRectangleBetweenTileCenters(
                tankObjectGraphics.getRectangle(),
                tank.getCoordinates(),
                tank.getDestinationCoordinates(),
                tank.getMovementProgress());
    }
}
