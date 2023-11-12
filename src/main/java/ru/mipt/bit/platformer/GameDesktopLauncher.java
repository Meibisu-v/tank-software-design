package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.AI;
import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.AI.AIAdapter;
import ru.mipt.bit.platformer.AI.Action;
import ru.mipt.bit.platformer.AI.RandomAI;
import ru.mipt.bit.platformer.graphics.MapGraphics;
import ru.mipt.bit.platformer.input.GunListener;
import ru.mipt.bit.platformer.input.KeyboardInputHandler;
import ru.mipt.bit.platformer.input.ToggleListener;
import ru.mipt.bit.platformer.level.GenerateLevelFromMap;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.level.LevelObserver;
import ru.mipt.bit.platformer.movement.CollisionChecker;
import ru.mipt.bit.platformer.input.Direction;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.TileUtils;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private TiledMap map;
    private CollisionChecker collisionChecker;
    private Level level;
    private TileUtils tileUtils;
    private Batch batch;
    private LevelObserver levelListener;
    private final KeyboardInputHandler inputHandler = new KeyboardInputHandler();
    private final ArrayList<Action> actors = new ArrayList<>();
    @Override
    public void create() {
        map = new TmxMapLoader().load("level.tmx");
        TiledMapTileLayer groundLayer = getSingleLayer(map);
        tileUtils = new TileUtils(new GridPoint2(groundLayer.getTileWidth(), groundLayer.getTileHeight()));
        AI aiAdapter = new NotRecommendingAI();
        level = new Level(new GenerateLevelFromMap("src/main/resources/map.txt"));

        batch = new SpriteBatch();

        ToggleListener toggleListener = new ToggleListener();

        levelListener = new LevelObserver(new MapGraphics(map, batch), batch, toggleListener);
        level.subscribe(levelListener);

        level.initObjects(tileUtils);
//        graphicsInit = new GraphicsInit(level, map, groundLayer);
        level.initColliders();

        actors.add(new Player(level.getPlayableTank(), new KeyboardInputHandler(), new GunListener()));
        actors.add(new AIAdapter(new NotRecommendingAI(), level));
    }



    @Override
    public void render() {
        // do action
//        for (var actor : actors) {
//            actor.doAction();
//        }
        new Player(level.getPlayableTank(), new KeyboardInputHandler(), new GunListener()).doAction();
        float deltaTime = Gdx.graphics.getDeltaTime();
        for (RandomAI actor : level.getActors()) {
            actor.setDeltaTime(deltaTime);
            actor.doAction();
        }
        level.update(deltaTime);
//        Direction desiredDirection = inputHandler.handleKeystrokes();
//        level.getPlayableTank().tryMove(desiredDirection);

//        continueTankProgress(deltaTime);
        clearScreen();
        levelListener.levelRender();
        // render each tile of the level
//        renderGame();
    }

    private void continueTankProgress(float deltaTime) {
        float newMovementProgress = continueProgress(level.getPlayableTank().getMovementProgress(),
                                                    deltaTime, level.getPlayableTank().getSpeed());
        level.getPlayableTank().tryReachDestinationCoordinates(newMovementProgress);
    }

    private static void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
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
//        graphicsInit.dispose();
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        map.dispose();
        levelListener.levelRender();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
