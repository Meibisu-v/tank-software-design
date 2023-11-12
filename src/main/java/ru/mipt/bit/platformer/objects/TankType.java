package ru.mipt.bit.platformer.objects;

import ru.mipt.bit.platformer.input.Direction;
import ru.mipt.bit.platformer.input.DirectionCoordinates;
import ru.mipt.bit.platformer.level.Level;

public interface TankType {
    void tryMove(DirectionCoordinates move);
    void takeDamage(Bullet bullet, Level level);
    void setTank(Tank tank);
}
