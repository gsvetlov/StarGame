package ru.svetlov.user.controller.events;

@FunctionalInterface
public interface TouchDraggedEvent {
    void notify(float screenX, float screenY, int pointer);
}
