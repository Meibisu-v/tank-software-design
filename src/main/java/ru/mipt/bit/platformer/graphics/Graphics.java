package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class Graphics {
    private Batch batch;
    private MapRenderer levelRenderer;
    private ObjectGraphics tankObjectGraphics;
    private List<ObjectGraphics> treeObjectGraphics;
    private List<ObjectGraphics> tanksObjectGraphics;
    private TileMovement tileMovement;
    private Level level;

    public Graphics(Level level, TiledMap map) {
        this.level = level;
        batch = new SpriteBatch();
        levelRenderer = createSingleLayerMapRenderer(map, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(map);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        tankObjectGraphics = new ObjectGraphics("images/tank_blue.png");

        tanksObjectGraphics = new ArrayList<>();
        for (Tank tank : level.getTanks()) {
            tanksObjectGraphics.add(new ObjectGraphics("images/tank_blue.png"));
//            tanksObjectGraphics.get(tanksObjectGraphics.size() - 1).moveRectangle(groundLayer, tank.getCoordinates());
        }
        treeObjectGraphics = new ArrayList<>();
        for (Obstacle obstacle : level.getObstacles()) {
            treeObjectGraphics.add(new ObjectGraphics("images/greenTree.png"));
            treeObjectGraphics.get(treeObjectGraphics.size() - 1).moveRectangle(groundLayer, obstacle.getCoordinates());
        }
    }
    public void renderGame() {
        levelRenderer.render();
        // start recording all drawing commands
        batch.begin();
        // render player
        tankObjectGraphics.draw(batch, level.getPlayableTank().getRotation());
        for (int i = 0; i < tanksObjectGraphics.size(); ++i) {
            tanksObjectGraphics.get(i).draw(batch, level.getTanks().get(i).getRotation());
        }
        // render tree obstacle
//        treeObjectGraphics.draw(batch, 0f);
        for (ObjectGraphics treeObject : treeObjectGraphics) {
            treeObject.draw(batch, 0f);
        }
        // submit all drawing requests
        batch.end();
    }

    public void dispose() {
        tankObjectGraphics.dispose();
        for (ObjectGraphics tankObject : tanksObjectGraphics) {
            tankObject.dispose();
        }
        for (ObjectGraphics treeObject : treeObjectGraphics) {
            treeObject.dispose();
        }
        batch.dispose();
    }

    public void calculateInterpolatedCoordinates() {
        // calculate interpolated player screen coordinates
        Tank tank = level.getPlayableTank();
        tileMovement.moveRectangleBetweenTileCenters(
                tankObjectGraphics.getRectangle(),
                tank.getCoordinates(),
                tank.getDestinationCoordinates(),
                tank.getMovementProgress());
        List<Tank> tanks = level.getTanks();
        for (int i = 0; i < level.getTanks().size(); ++i) {
            tileMovement.moveRectangleBetweenTileCenters(
                    tanksObjectGraphics.get(i).getRectangle(),
                    tanks.get(i).getCoordinates(),
                    tanks.get(i).getDestinationCoordinates(),
                    tanks.get(i).getMovementProgress()
            );
        }
    }
}
