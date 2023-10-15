package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.movement.Colliding;

public class Border implements Colliding {
    private int width;
    private int height;

    public Border(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean collides(GridPoint2 target) {
        return target.x < 0 || target.y < 0 || target.x >= width || target.y >= height;
    }
}
