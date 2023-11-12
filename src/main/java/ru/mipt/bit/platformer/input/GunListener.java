package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys.SPACE;

public class GunListener {
    public boolean shootKeyPressed() {
        return Gdx.input.isKeyPressed(SPACE);
    }
}
