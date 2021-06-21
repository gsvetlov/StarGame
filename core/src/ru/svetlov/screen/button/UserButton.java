package ru.svetlov.screen.button;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.svetlov.base.Sprite;
import ru.svetlov.base.UserInputEventProvider;
import ru.svetlov.user.controller.events.KeyDownEvent;
import ru.svetlov.user.controller.events.KeyUpEvent;
import ru.svetlov.user.controller.events.TouchDownEvent;
import ru.svetlov.user.controller.events.TouchUpEvent;

public abstract class UserButton extends Sprite {
    protected final UserInputEventProvider provider;
    protected final Matrix3 screenToWorld;
    protected final Runnable action;
    protected final Vector2 touch;
    protected float height;
    protected boolean isPressed;

    public UserButton(TextureRegion region,
                      float height,
                      float posX,
                      float posY,
                      UserInputEventProvider provider,
                      Matrix3 screenToWorld,
                      Runnable action) {
        super(region);
        this.height = height;
        setPosition(posX, posY);
        this.screenToWorld = screenToWorld;
        touch = new Vector2();
        this.action = action;
        this.provider = provider;
        provider.subscribe((TouchDownEvent) this::onTouchDown);
        provider.subscribe((TouchUpEvent) this::onTouchUp);
        provider.subscribe(this::onTouchDragged);
        provider.subscribe((KeyDownEvent) this::onKeyDown);
        provider.subscribe((KeyUpEvent) this::onKeyUp);
    }

    @Override
    public void resize(Rectangle worldBounds) {
        super.resize(worldBounds);
        setHeight(height);
    }

    @Override
    public void dispose() {
        super.dispose();
        provider.unsubscribe((TouchDownEvent) this::onTouchDown);
        provider.unsubscribe((TouchUpEvent) this::onTouchUp);
        provider.unsubscribe((KeyDownEvent) this::onKeyDown);
        provider.unsubscribe((KeyUpEvent) this::onKeyUp);
    }

    protected void onTouchDown(float screenX, float screenY, int pointer, int button) {
        touch.set(screenX, screenY).mul(screenToWorld);
    }

    protected void onTouchUp(float screenX, float screenY, int pointer, int button) {
        touch.set(screenX, screenY).mul(screenToWorld);
    }

    protected void onTouchDragged(float screenX, float screenY, int pointer) {
        touch.set(screenX, screenY).mul(screenToWorld);
    }

    protected void onKeyDown(int code) {

    }

    protected void onKeyUp(int code) {
    }

}
