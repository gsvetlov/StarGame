package ru.svetlov.user.controller.events;

@FunctionalInterface
public interface TouchUpEvent {
    void notify(float screenX, float screenY, int pointer, int button);
}
