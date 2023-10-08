package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import ru.mipt.bit.platformer.graphics.Graphics;
import ru.mipt.bit.platformer.level.GenerateLevelFromMap;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.level.RandomMapGenerator;
import ru.mipt.bit.platformer.movement.CollisionChecker;
import ru.mipt.bit.platformer.movement.Direction;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private Graphics graphics;
    private TiledMap map;
    private CollisionChecker collisionChecker;
    private Level level;
    private final KeyboardInputHandler inputHandler = new KeyboardInputHandler();
    @Override
    public void create() {
        map = new TmxMapLoader().load("level.tmx");
//        level = new Level(new GenerateLevelFromCoord("src/main/resources/placement.txt"));
//        level = new Level(new GenerateLevelFromMap("src/main/resources/map.txt"));
        new RandomMapGenerator(5, 5,4 ).saveMapToFile("randomMap.txt");
        level = new Level(new GenerateLevelFromMap("src/main/resources/randomMap.txt"));
        collisionChecker = new CollisionChecker();
        level.initObjects(collisionChecker);
        graphics = new Graphics(level, map);
        initColliders();
    }

    private void initColliders() {
        if (level.getPlayableTank() != null) {
            collisionChecker.addColliding(level.getPlayableTank());
        }
        for (var tank : level.getTanks()) {
            collisionChecker.addColliding(tank);
        }
        for (var obstacle : level.getObstacles()) {
            collisionChecker.addColliding(obstacle);
        }
    }

    @Override
    public void render() {
        clearScreen();

        float deltaTime = Gdx.graphics.getDeltaTime();
        Direction desiredDirection = inputHandler.handleKeystrokes();
        level.getPlayableTank().tryMove(level.getObstacles(), desiredDirection);
        graphics.calculateInterpolatedCoordinates();
        continueTankProgress(deltaTime);
        // render each tile of the level
        renderGame();
    }

    private void continueTankProgress(float deltaTime) {
        float newMovementProgress = continueProgress(level.getPlayableTank().getMovementProgress(),
                                                    deltaTime, level.getPlayableTank().getSpeed());
        level.getPlayableTank().tryReachDestinationCoordinates(newMovementProgress);
    }
    private void renderGame() {
        graphics.renderGame();
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
        graphics.dispose();
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        //tankObjectGraphics.dispose();
        //treeObjectGraphics.dispose();
        map.dispose();
        graphics.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
