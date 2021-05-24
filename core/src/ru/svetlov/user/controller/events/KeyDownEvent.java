package ru.svetlov.user.controller.events;

@FunctionalInterface
public interface KeyDownEvent {
    void onKeyDown(int keycode);
}
