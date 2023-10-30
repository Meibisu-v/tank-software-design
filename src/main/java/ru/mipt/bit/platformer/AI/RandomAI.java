package ru.mipt.bit.platformer.AI;

import ru.mipt.bit.platformer.input.Direction;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.Random;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class RandomAI implements Action {
    private final Tank tank;
    float deltaTime;

    public RandomAI(Tank tank) {
        this.tank = tank;
    }

    public void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }

    @Override
    public void doAction() {
        Direction[] directions = Direction.values();
        Random random = new Random();
        int idx = random.nextInt(directions.length);
        tank.tryMove(directions[idx]);
        float newMovementProgress = continueProgress(tank.getMovementProgress(),
                deltaTime, tank.getSpeed());
//
        tank.tryReachDestinationCoordinates(newMovementProgress);
    }
}
