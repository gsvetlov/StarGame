package ru.svetlov.user.controller;

public interface UserInputEventProvider {
    void setTouchDownEventHandler(TouchDownEvent handler);
    void setKeyDownEventHandler(KeyDownEvent handler);
}
