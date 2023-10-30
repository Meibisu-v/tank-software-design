package ru.mipt.bit.platformer.util;

public interface EventManager {
    void subscribe(EventListener eventListener);
    void unsubscribe(EventListener eventListener);
}
