package ru.mipt.bit.platformer.movement;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;


public class CollisionChecker {
    private final ArrayList<Colliding> colliders = new ArrayList<>();

    public ArrayList<Colliding> getColliders() { // for test
        return colliders;
    }

    public void addColliding(Colliding colliding) {
        colliders.add(colliding);
    }

    public void removeColliding(Colliding colliding) {
        colliders.remove(colliding);
    }

    public Colliding isFree(GridPoint2 target) {
        for (var collider : colliders) {
            if (collider.collides(target)) return collider;
        }
        return null;
    }
}
