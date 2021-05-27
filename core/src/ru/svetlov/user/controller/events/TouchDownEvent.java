package ru.svetlov.user.controller.events;

@FunctionalInterface
public interface TouchDownEvent {
    void onTouchDown(int screenX, int screenY, int pointer, int button);
}
