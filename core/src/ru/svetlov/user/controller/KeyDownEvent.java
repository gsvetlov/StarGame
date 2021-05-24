package ru.svetlov.user.controller;

@FunctionalInterface
public interface KeyDownEvent {
    void onKeyDown(int keycode);
}
