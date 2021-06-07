package ru.svetlov.user.controller.events;

@FunctionalInterface
public interface KeyTypedEvent {
    void notify(char character);
}
