package ru.svetlov.user.controller.events;

@FunctionalInterface
public interface KeyDownEvent {
    void notify(int keycode);
}
