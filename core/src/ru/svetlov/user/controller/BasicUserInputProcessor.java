package ru.svetlov.user.controller;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

import ru.svetlov.user.controller.events.*;
import ru.svetlov.base.UserInputEventProvider;


public class BasicUserInputProcessor extends InputAdapter implements UserInputEventProvider {
    private final List<KeyUpEvent> keyUpEventListeners = new ArrayList<>();
    private final List<KeyDownEvent> keyDownEventListeners = new ArrayList<>();
    private final List<KeyTypedEvent> keyTypedEventListeners = new ArrayList<>();
    private final List<TouchUpEvent> touchUpEventListeners = new ArrayList<>();
    private final List<TouchDownEvent> touchDownEventListeners = new ArrayList<>();
    private final List<TouchDraggedEvent> touchDraggedEventListeners = new ArrayList<>();

    private Rectangle screenBounds = new Rectangle();

    @Override
    public void setScreenBounds(Rectangle screenBounds) {
        this.screenBounds = screenBounds;
    }

    @Override
    public boolean keyTyped(char character) {
        for (KeyTypedEvent event : keyTypedEventListeners)
            if (event != null)
                event.notify(character);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        for (KeyUpEvent event : keyUpEventListeners)
            if (event != null)
                event.notify(keycode);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        for (KeyDownEvent event : keyDownEventListeners)
            if (event != null)
                event.notify(keycode);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (TouchDownEvent event : touchDownEventListeners)
            if (event != null)
                event.notify(screenX, screenBounds.height - screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (TouchUpEvent event : touchUpEventListeners)
            if (event != null)
                event.notify(screenX, screenBounds.height - screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (TouchDraggedEvent event : touchDraggedEventListeners)
            if (event != null)
                event.notify(screenX, screenBounds.height - screenY, pointer);
        return false;
    }

    @Override
    public void subscribe(TouchDownEvent listener) {
        touchDownEventListeners.add(listener);
    }

    @Override
    public void unsubscribe(TouchDownEvent listener) {
        touchDownEventListeners.remove(listener);
    }

    @Override
    public void subscribe(TouchUpEvent listener) {
        touchUpEventListeners.add(listener);
    }

    @Override
    public void unsubscribe(TouchUpEvent listener) {
        touchUpEventListeners.remove(listener);
    }

    @Override
    public void subscribe(TouchDraggedEvent listener) {
        touchDraggedEventListeners.add(listener);
    }

    @Override
    public void unsubscribe(TouchDraggedEvent listener) {
        touchDraggedEventListeners.remove(listener);
    }

    @Override
    public void subscribe(KeyDownEvent listener) {
        keyDownEventListeners.add(listener);
    }

    @Override
    public void unsubscribe(KeyDownEvent listener) {
        keyDownEventListeners.remove(listener);
    }

    @Override
    public void subscribe(KeyUpEvent listener) {
        keyUpEventListeners.add(listener);
    }

    @Override
    public void unsubscribe(KeyUpEvent listener) {
        keyUpEventListeners.remove(listener);
    }

    @Override
    public void subscribe(KeyTypedEvent listener) {
        keyTypedEventListeners.add(listener);
    }

    @Override
    public void unsubscribe(KeyTypedEvent listener) {
        keyTypedEventListeners.remove(listener);
    }

}
