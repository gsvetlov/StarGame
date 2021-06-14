package ru.svetlov.model;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;

import ru.svetlov.base.UserInputEventProvider;

public class NewGameButton extends UserButton {
    public NewGameButton(TextureRegion region, float height, float posX, float posY, UserInputEventProvider provider, Matrix3 screenToWorld, Runnable action) {
        super(region, height, posX, posY, provider, screenToWorld, action);
    }

    @Override
    protected void onTouchDown(float screenX, float screenY, int pointer, int button) {
        super.onTouchDown(screenX, screenY, pointer, button);
        if (spriteBounds.contains(touch) && pointer == 0) {
            isPressed = true;
            setHeight(height * 1.2f);
        }
    }

    @Override
    protected void onTouchDragged(float screenX, float screenY, int pointer) {
        onTouchDown(screenX, screenY, pointer, 0);

    }

    @Override
    protected void onTouchUp(float screenX, float screenY, int pointer, int button) {
        super.onTouchUp(screenX, screenY, pointer, button);
        if (isPressed && spriteBounds.contains(touch) && pointer == 0)
            action.run();
        else {
            setHeight(height);
            isPressed = false;
        }
    }

    @Override
    protected void onKeyDown(int code) {
        if (code == Input.Keys.N)
            action.run();
    }
}
