package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private Graphics graphics;
    private Level level;
    private final KeyboardInputHandler inputHandler = new KeyboardInputHandler();
    @Override
    public void create() {
        level = new Level();
        graphics = new Graphics(level);
    }

    @Override
    public void render() {
        clearScreen();

        float deltaTime = Gdx.graphics.getDeltaTime();
        Direction desiredDirection = inputHandler.handleKeystrokes();
        level.getTank().tryMove(level.getTreeObstacle(), desiredDirection);
        graphics.calculateInterpolatedCoordinates();
        continueTankProgress(deltaTime);
        // render each tile of the level
        renderGame();
    }

    private void continueTankProgress(float deltaTime) {
        float newMovementProgress = continueProgress(level.getTank().getMovementProgress(),
                                                    deltaTime, level.getTank().getSpeed());
        level.getTank().tryReachDestinationCoordinates(newMovementProgress);
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
        level.dispose();
        graphics.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
