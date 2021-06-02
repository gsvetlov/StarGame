package ru.svetlov.user.controller.events;

@FunctionalInterface
public interface KeyUpEvent {
    void notify(int keycode);
}
