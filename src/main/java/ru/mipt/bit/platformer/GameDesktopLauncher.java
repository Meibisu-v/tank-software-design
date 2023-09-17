package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.UP;
import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.W;
import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private Map map;

    private Player player;

    private Obstacle treeObstacle;

    private Direction desiredDirection() {
        Direction desiredDirection = Direction.NODIRECTION;
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            desiredDirection = Direction.UP;
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            desiredDirection = Direction.LEFT;
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            desiredDirection = Direction.DOWN;
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            desiredDirection = Direction.RIGHT;
        }
        return desiredDirection;
    }
    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        map = new Map("level.tmx", batch);

        player = new Player("images/tank_blue.png", new GridPoint2(1, 1));

        treeObstacle = new Obstacle("images/greenTree.png", new GridPoint2(1, 3));

        moveRectangleAtTileCenter(map.getGroundLayer(), treeObstacle.getRectangle(), treeObstacle.getCoordinates());
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        player.tryMove(treeObstacle, desiredDirection());

        // calculate interpolated player screen coordinates
        map.getTileMovement().moveRectangleBetweenTileCenters(
                    player.getRectangle(),
                    player.getCoordinates(),
                    player.getDestinationCoordinates(),
                    player.getMovementProgress()
                );

        player.continuePlayerProgress(MOVEMENT_SPEED);

        // render each tile of the level
        map.getLevelRenderer().render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(batch, player.getGraphics(), player.getRectangle(), player.getRotation());

        // render tree obstacle
        drawTextureRegionUnscaled(batch, treeObstacle.getGraphics(),
                            treeObstacle.getRectangle(), 0f);

        // submit all drawing requests
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        treeObstacle.getTexture().dispose();
        player.getTexture().dispose();
        map.getLevel().dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
