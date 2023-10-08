package ru.mipt.bit.platformer.movement;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.movement.Colliding;

import java.util.ArrayList;

public class CollisionChecker {
    private final ArrayList<Colliding> colliders = new ArrayList<>();

    public ArrayList<Colliding> getColliders() { // for test
        return colliders;
    }

    public void addColliding(Colliding colliding) {
        colliders.add(colliding);
    }

    public boolean isFree(GridPoint2 target) {
        for (var collider : colliders) {
            if (collider.collides(target)) return false;
        }
        return true;
    }
}
