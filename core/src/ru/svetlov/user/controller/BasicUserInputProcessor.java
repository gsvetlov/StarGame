package ru.svetlov.user.controller;

import com.badlogic.gdx.InputProcessor;

import ru.svetlov.user.controller.events.KeyDownEvent;
import ru.svetlov.user.controller.events.TouchDownEvent;
import ru.svetlov.base.UserInputEventProvider;

public class BasicUserInputProcessor implements InputProcessor, UserInputEventProvider {
    private TouchDownEvent touchDownEventHandler;
    private KeyDownEvent keyDownEventHandler;

    @Override
    public void setTouchDownEventHandler(TouchDownEvent handler) {
        touchDownEventHandler = handler;
    }

    @Override
    public void setKeyDownEventHandler(KeyDownEvent handler) {
        keyDownEventHandler = handler;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keyDownEventHandler != null)
            keyDownEventHandler.onKeyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (touchDownEventHandler != null)
            touchDownEventHandler.onTouchDown(screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
