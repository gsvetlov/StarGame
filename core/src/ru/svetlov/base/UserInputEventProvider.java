package ru.svetlov.base;

import ru.svetlov.user.controller.events.KeyDownEvent;
import ru.svetlov.user.controller.events.TouchDownEvent;

public interface UserInputEventProvider {
    void setTouchDownEventHandler(TouchDownEvent handler);
    void setKeyDownEventHandler(KeyDownEvent handler);
}
