package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.movement.Direction;

public interface InputHandler {
    Direction handleKeystrokes();
}
