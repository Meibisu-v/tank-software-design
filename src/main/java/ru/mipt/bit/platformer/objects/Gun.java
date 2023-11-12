package ru.mipt.bit.platformer.objects;

import ru.mipt.bit.platformer.input.Direction;
import ru.mipt.bit.platformer.input.DirectionCoordinates;
import ru.mipt.bit.platformer.level.Level;

public class Gun {
    private final Level level;
    private final Tank tank;
    private final float bulletSpeed;
    private final int damage;
    private float deltaTime;

    public Gun(Level level, Tank tank, float bulletSpeed, int damage) {
        this.level = level;
        this.tank = tank;
        this.bulletSpeed = bulletSpeed;
        this.damage = damage;
    }
    public void tryShoot() {
        DirectionCoordinates direction = Direction.calcDirection(tank.getRotation());
        if (direction != null) {
            level.addBullet(new Bullet(
                    tank.getCoordinates().cpy().add(direction.getDeltaCoordinate()),
                    direction,
                    damage,
                    bulletSpeed,
                    tank.getCollisionChecker(),
                    tank.getTileUtils(),
                    level)
            );
        }
    }
}
