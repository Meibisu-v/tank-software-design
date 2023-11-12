package ru.mipt.bit.platformer.objects;

import ru.mipt.bit.platformer.input.Direction;
import ru.mipt.bit.platformer.input.DirectionCoordinates;
import ru.mipt.bit.platformer.level.Level;

public class Middle implements TankType {
    private Tank tank;

    public Middle(Tank tank) {
        this.tank = tank;
    }

    public Middle() {
    }

    @Override
    public void tryMove(DirectionCoordinates move) {
        tank.tryMoveWithSpeed(move, 2.0f);
    }

    @Override
    public void takeDamage(Bullet bullet, Level level) {
        tank.getTankState().receiveDamage(bullet);
        float curHealth = tank.getTankState().getCurHealth();
        float health = tank.getTankState().getHealth();
        if (curHealth >= 0.7f * health) {
            level.removeTank(tank);
        } else if (curHealth > 0.15f * health) {
            tank.changeState(new Middle(tank));
        } else {
            tank.changeState(new Heavy(tank));
        }

    }

    @Override
    public void setTank(Tank tank) {
        this.tank = tank;
    }

}
