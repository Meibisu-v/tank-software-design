package ru.mipt.bit.platformer.movement;

import com.badlogic.gdx.math.GridPoint2;

public interface Colliding {
    boolean collides(GridPoint2 target);
}
