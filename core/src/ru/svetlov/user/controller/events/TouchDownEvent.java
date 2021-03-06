package ru.svetlov.user.controller.events;

@FunctionalInterface
public interface TouchDownEvent {
    void notify(float screenX, float screenY, int pointer, int button);
}
