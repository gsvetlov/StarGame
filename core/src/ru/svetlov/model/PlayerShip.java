package ru.svetlov.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.svetlov.base.Sprite;
import ru.svetlov.base.UserInputEventProvider;
import ru.svetlov.pool.BulletPool;
import ru.svetlov.user.controller.events.*;

public class PlayerShip extends Sprite {
    private final Vector2 targetPosition;
    private final UserInputEventProvider provider;
    private final Vector2 touch;
    private final Matrix3 screenToWorld;
    private Rectangle worldBounds;

    private final BulletPool bullets;
    private final TextureRegion bulletRegion;
    private final Vector2 bulletPosition;
    private final Vector2 bulletSpeed;

    private final Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav")); // звук выстрела

    private boolean autoFire;
    private float triggerCounter;
    private final static float AUTOFIRE_TIMESPAN = 0.25f;

    public PlayerShip(UserInputEventProvider provider, Matrix3 screenToWorld,
                      TextureRegion[] regions, BulletPool bulletPool, TextureRegion bulletRegion) {
        super(regions, new Vector2(), new Vector2(), new Vector2());
        targetPosition = position.cpy();
        touch = new Vector2();

        this.bullets = bulletPool;
        this.bulletRegion = bulletRegion;
        bulletPosition = new Vector2();
        bulletSpeed = new Vector2();

        // get screen to world conversion matrix
        this.screenToWorld = screenToWorld; // TODO: refactor to call outer converter

        this.provider = provider; // subscribe user input
        this.provider.subscribe((TouchDownEvent) this::onTouchDown);
        this.provider.subscribe((TouchUpEvent) this::onTouchUp);
        this.provider.subscribe(this::onTouchDragged);
        this.provider.subscribe((KeyDownEvent) this::onKeyDown);
        this.provider.subscribe((KeyUpEvent) this::onKeyUp);


        position.set(0, -0.42f); // set position to lower part of screen
    }

    public void getToPosition(Vector2 pos, float speed) {
        targetPosition.set(pos);
        velocity.set(targetPosition).sub(position).nor().scl(speed);
    }

    @Override
    public void resize(Rectangle worldBounds) {
        this.worldBounds = worldBounds;
        setHeight(.1f);
    }

    @Override
    public void update(float delta) {
        checkPositionReached(delta);
        super.update(delta);
        if (autoFire) runAutoFire(delta);
    }

    private void runAutoFire(float delta){
        triggerCounter += delta;
        if (triggerCounter > AUTOFIRE_TIMESPAN){
            shoot(autoFire);
            triggerCounter -= AUTOFIRE_TIMESPAN;
        }
    }

    @Override
    public void dispose() {
        provider.unsubscribe((TouchDownEvent) this::onTouchDown);
        provider.unsubscribe((TouchUpEvent) this::onTouchUp);
        provider.unsubscribe(this::onTouchDragged);
        provider.unsubscribe((KeyDownEvent) this::onKeyDown);
        provider.unsubscribe((KeyUpEvent) this::onKeyUp);
        sound.dispose();
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

    private void shoot(boolean trigger) {
        if (!trigger) return;
        Bullet bullet = bullets.obtain();
        bullet.set(
                this,
                bulletRegion,
                bulletPosition.set(position.x, position.y + spriteBounds.height / 2),
                bulletSpeed.set(0, 0.4f),
                worldBounds,
                1,
                0.01f);
        sound.play();
    }

    private void onTouchDown(float screenX, float screenY, int pointer, int button) {
        touch.set(screenX, screenY).mul(screenToWorld);
    }

    private void onTouchUp(float screenX, float screenY, int pointer, int button) {
        touch.set(screenX, screenY).mul(screenToWorld);
        getToPosition(touch, 0.1f);
        autoFire = true;
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
