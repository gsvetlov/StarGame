package ru.svetlov.model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.svetlov.model.configuration.ShipConfiguration;
import ru.svetlov.pool.BulletPool;
import ru.svetlov.pool.ExplosionPool;

public class AlienShip extends Ship {
    private Rectangle activeBounds;
    private float blinkCounter;
    private static final float BLINK_DURATION = 0.5f;

    public AlienShip(
            TextureRegion[] regions,
            TextureRegion bulletTexture,
            BulletPool bullets,
            ExplosionPool explosions,
            Sound sound){
        super(regions, bulletTexture, bullets, explosions, sound, new Vector2(), new Vector2(), new Vector2());
        autoFire = true;
    }

    @Override
    public void set(TextureRegion[] regions, ShipConfiguration config) {
        super.set(regions, config);
        velocity = new Vector2(0, -config.VELOCITY);
    }

    @Override
    public void resize(Rectangle worldBounds) {
        this.worldBounds = worldBounds;
        setHeight(size);
        activeBounds = new Rectangle()
                .setSize(worldBounds.width, worldBounds.height * 1.2f)
                .setCenter(0, 0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (!activeBounds.contains(position)) {
            setDestroyed(true);
        }
        blinkCounter +=delta;
        if (blinkCounter > BLINK_DURATION)
            frame = 0;

    }

    @Override
    public void shoot(boolean trigger) {
        super.shoot(trigger);
        Bullet bullet = bullets.obtain();
        bullet.set(
                null,
                bulletTexture,
                bulletPosition.set(position.x, position.y - spriteBounds.height / 2),
                bulletSpeed.set(0, velocity.y * bulletSpeedFactor),
                worldBounds,
                damage,
                0.01f);
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        frame = 1;
        blinkCounter = 0;
    }

    @Override
    public int giveDamage() {
        return super.giveDamage();
    }
}
