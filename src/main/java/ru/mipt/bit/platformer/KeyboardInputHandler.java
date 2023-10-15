package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.movement.Direction;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class KeyboardInputHandler implements InputHandler{
    @Override
    public Direction handleKeystrokes() {
        Direction direction = Direction.NODIRECTION;
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            direction = Direction.UP;
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            direction = Direction.LEFT;
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            direction = Direction.DOWN;
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            direction = Direction.RIGHT;
        }
        return direction;
    }
}
