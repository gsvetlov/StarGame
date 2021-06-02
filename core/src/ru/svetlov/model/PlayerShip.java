package ru.svetlov.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.svetlov.base.Sprite;
import ru.svetlov.base.UserInputEventProvider;
import ru.svetlov.user.controller.events.TouchDownEvent;

public class PlayerShip extends Sprite {
    private final Vector2 targetPosition;
    private final UserInputEventProvider provider;
    private final Vector2 touch;
    private final Matrix3 screenToWorld;

    public PlayerShip(UserInputEventProvider provider, Matrix3 screenToWorld,
                      TextureRegion region) {
        super(region);
        targetPosition = position.cpy();
        touch = new Vector2();
        this.screenToWorld = screenToWorld;
        this.provider = provider;
        this.provider.subscribe((TouchDownEvent) this::onTouchDown);
        this.provider.subscribe(this::onTouchDragged);
    }

    public void getToPosition(Vector2 pos, float speed) {
        targetPosition.set(pos);
        velocity.set(targetPosition).sub(position).nor().scl(speed);
    }

    @Override
    public void resize(Rectangle worldBounds) {
        setHeight(.1f);
    }

    @Override
    public void update(float delta) {
        checkPositionReached(delta);
        super.update(delta);
    }

    @Override
    public void dispose() {
        provider.unsubscribe((TouchDownEvent) this::onTouchDown);
    }

    private void checkPositionReached(float delta) {
        if (position.dst2(targetPosition) <= velocity.len2() * delta * delta) {
            position.set(targetPosition);
            velocity.setZero();
        }
    }

    private void onTouchDown(float screenX, float screenY, int pointer, int button) {
        touch.set(screenX, screenY).mul(screenToWorld);
        getToPosition(touch, 0.1f);
    }

    private void onTouchDragged(float screenX, float screenY, int pointer) {
        onTouchDown(screenX, screenY, pointer, 0);
    }
}
