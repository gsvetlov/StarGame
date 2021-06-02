package ru.svetlov.base;

import com.badlogic.gdx.math.Rectangle;

import ru.svetlov.user.controller.events.*;

public interface UserInputEventProvider {
    //void setTouchDownEventHandler(TouchDownEvent handler);
    //void setKeyDownEventHandler(KeyDownEvent handler);
    void subscribe(TouchDownEvent listener);
    void unsubscribe(TouchDownEvent listener);
    void subscribe(TouchUpEvent listener);
    void unsubscribe(TouchUpEvent listener);
    void subscribe(TouchDraggedEvent listener);
    void unsubscribe(TouchDraggedEvent listener);
    void subscribe(KeyDownEvent listener);
    void unsubscribe(KeyDownEvent listener);
    void subscribe(KeyUpEvent listener);
    void unsubscribe(KeyUpEvent listener);
    void subscribe(KeyTypedEvent listener);
    void unsubscribe(KeyTypedEvent listener);

    void setScreenBounds(Rectangle screenBounds);
}
