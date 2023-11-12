package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.AI.Action;
import ru.mipt.bit.platformer.input.Direction;
import ru.mipt.bit.platformer.input.GunListener;
import ru.mipt.bit.platformer.input.KeyboardInputHandler;
import ru.mipt.bit.platformer.objects.Tank;

public class Player implements Action {
    private final Tank tank;
    private final KeyboardInputHandler listener;
    private final GunListener gunListener;

    public Player(Tank tank, KeyboardInputHandler listener, GunListener gunListener) {
        this.tank = tank;
        this.listener = listener;
        this.gunListener = gunListener;
    }

    @Override
    public void doAction() {
        Direction direction = listener.handleKeystrokes();
        if (direction != Direction.NODIRECTION) {
            tank.tryMove(direction.getDirection());
        }
        if (gunListener.shootKeyPressed()) {
            tank.tryShoot();
        }
    }
}
