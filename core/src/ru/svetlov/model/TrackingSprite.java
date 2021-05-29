package ru.svetlov.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.svetlov.base.Sprite;

public class TrackingSprite extends Sprite {
    private final Vector2 targetPosition;

    public TrackingSprite(Texture region, Vector2 position, Vector2 velocity, Vector2 acceleration) {
        super(new TextureRegion(region), position, velocity, acceleration);
        targetPosition = position.cpy();
    }

    public void getToPosition(Vector2 pos, float speed) {
        targetPosition.set(pos);
        velocity.set(targetPosition).sub(position).nor().scl(speed);
        System.out.println(position + " -> " + targetPosition + " at " + velocity);
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

    private void checkPositionReached(float delta) {
        if (position.dst2(targetPosition) <= velocity.len2() * delta * delta) {
            position.set(targetPosition);
            velocity.setZero();
        }
    }
}
