package ru.svetlov.model.ship;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.svetlov.base.UserInputEventProvider;
import ru.svetlov.model.Bullet;
import ru.svetlov.pool.BulletPool;
import ru.svetlov.pool.ExplosionPool;
import ru.svetlov.user.controller.events.*;

public class PlayerShip extends Ship {

    private final Matrix3 screenToWorld;
    private final UserInputEventProvider provider;
    private final Vector2 touch;
    private final Vector2 targetPosition;

    public PlayerShip(UserInputEventProvider provider, Matrix3 screenToWorld,
                      TextureRegion[] regions,
                      TextureRegion bulletTexture,
                      BulletPool bullets,
                      ExplosionPool explosions,
                      Sound sound) {
        super(regions, bulletTexture, bullets, explosions, sound, new Vector2(), new Vector2(), new Vector2());
        targetPosition = position.cpy();
        touch = new Vector2();

        // get screen to world conversion matrix
        this.screenToWorld = screenToWorld; // TODO: refactor to call outer converter

        // subscribe to user input
        this.provider = provider;
        this.provider.subscribe((TouchDownEvent) this::onTouchDown);
        this.provider.subscribe((TouchUpEvent) this::onTouchUp);
        this.provider.subscribe(this::onTouchDragged);
        this.provider.subscribe((KeyDownEvent) this::onKeyDown);
        this.provider.subscribe((KeyUpEvent) this::onKeyUp);

        position.set(0, -0.42f); // set position to lower part of screen
        hp = 100;
        damage = 2;
    }

    public void getToPosition(Vector2 pos, float speed) {
        targetPosition.set(pos);
        velocity.set(targetPosition).sub(position).nor().scl(speed);
    }

    @Override
    public void update(float delta) {
        checkPositionReached(delta);
        super.update(delta);
    }

    @Override
    public void resize(Rectangle worldBounds) {
        this.worldBounds = worldBounds;
        setHeight(.1f);
    }

    @Override
    public void setDestroyed(boolean destroyed) {
        super.setDestroyed(destroyed);
        dispose();
    }

    @Override
    public void dispose() {
        autoFire = false;
        super.dispose();
        provider.unsubscribe((TouchDownEvent) this::onTouchDown);
        provider.unsubscribe((TouchUpEvent) this::onTouchUp);
        provider.unsubscribe(this::onTouchDragged);
        provider.unsubscribe((KeyDownEvent) this::onKeyDown);
        provider.unsubscribe((KeyUpEvent) this::onKeyUp);
    }

    private void checkPositionReached(float delta) {
        if (position.dst2(targetPosition) <= velocity.len2() * delta * delta) {
            position.set(targetPosition);
            velocity.setZero();
            targetPosition.set(0, -1f);
        }
        // position is out of screen bounds check
        if (position.x < worldBounds.getX())
            position.x = worldBounds.getX();
        if (position.x > worldBounds.getX() + worldBounds.getWidth())
            position.x = worldBounds.getX() + worldBounds.getWidth();
        if (position.y < worldBounds.getY())
            position.y = worldBounds.getY();
        if (position.y > worldBounds.getY() + worldBounds.getHeight())
            position.y = worldBounds.getY() + worldBounds.getHeight();
    }

    @Override
    public void shoot(boolean trigger) {
        super.shoot(trigger);
        Bullet bullet = bullets.obtain();
        bullet.set(
                this,
                bulletTexture,
                bulletPosition.set(position.x, position.y + spriteBounds.height / 2),
                bulletSpeed.set(0, 0.5f),
                worldBounds,
                damage,
                0.01f);
    }

    @Override
    public void takeDamage(int damage) {
        frame = 1;
        blinkCounter = 0;
        super.takeDamage(damage);
    }

    @Override
    public int giveDamage() {
        return damage * 2;
    }

    private void onTouchDown(float screenX, float screenY, int pointer, int button) {
        touch.set(screenX, screenY).mul(screenToWorld);
    }

    private void onTouchUp(float screenX, float screenY, int pointer, int button) {
        touch.set(screenX, screenY).mul(screenToWorld);
        getToPosition(touch, 0.1f);
        if (pointer == 1) autoFire = !autoFire;
    }

    private void onTouchDragged(float screenX, float screenY, int pointer) {
        onTouchUp(screenX, screenY, pointer, 0);
    }

    private void onKeyDown(int code) {
        switch (code) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                velocity.add(-0.1f, 0);
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                velocity.add(0.1f, 0);
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                velocity.add(0, 0.1f);
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                velocity.add(0, -0.1f);
                break;
            case Input.Keys.ALT_LEFT:
                autoFire = !autoFire;
                break;
        }
    }

    private void onKeyUp(int code) {
        switch (code) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                velocity.sub(-0.1f, 0);
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                velocity.sub(0.1f, 0);
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                velocity.sub(0, 0.1f);
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                velocity.sub(0, -0.1f);
                break;
        }
    }
}
