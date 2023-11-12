package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Gdx;

import static com.badlogic.gdx.Input.Keys.L;

public class ToggleListener {
    private boolean toggle = false;
    private boolean waitRelaxation = false;

    public boolean toggled() {
        return toggle;
    }

    public void update() {
        if (!Gdx.input.isKeyPressed(L)) {
            waitRelaxation = false;
        }
        if (Gdx.input.isKeyPressed(L) && !waitRelaxation) {
            toggle = !toggle;
            waitRelaxation = true;
        }
    }
}
