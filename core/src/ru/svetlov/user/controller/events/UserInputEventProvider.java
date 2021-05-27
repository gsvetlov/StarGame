package ru.svetlov.user.controller.events;

public interface UserInputEventProvider {
    void setTouchDownEventHandler(TouchDownEvent handler);
    void setKeyDownEventHandler(KeyDownEvent handler);
}
