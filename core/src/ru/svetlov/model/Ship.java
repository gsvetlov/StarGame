package ru.svetlov.model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.svetlov.base.Sprite;
import ru.svetlov.model.configuration.ShipConfiguration;
import ru.svetlov.pool.BulletPool;
import ru.svetlov.pool.ExplosionPool;

public abstract class Ship extends Sprite implements Collide {
    protected BulletPool bullets;
    protected TextureRegion bulletTexture;
    protected Vector2 bulletPosition;
    protected Vector2 bulletSpeed;
    protected float bulletSpeedFactor;
    protected boolean autoFire;
    protected float triggerCounter;
    protected float triggerTIMESPAN = 0.25f;

    protected Sound sound; // звук выстрела

    protected ExplosionPool explosionPool; // анимация взрыва

    protected float blinkCounter;
    protected static final float BLINK_DURATION = 0.5f;

    protected int hp;
    protected int damage;
    protected float size;

    public Ship(
            TextureRegion[] regions,
            TextureRegion bulletTexture,
            BulletPool bullets,
            ExplosionPool explosions,
            Sound sound,
            Vector2 pos, Vector2 vel, Vector2 acc) {
        super(regions, pos, vel, acc);
        this.explosionPool = explosions;
        this.sound = sound;
        this.bullets = bullets;
        this.bulletTexture = bulletTexture;
        bulletPosition = new Vector2();
        bulletSpeed = new Vector2();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public void set(TextureRegion[] regions, ShipConfiguration config) {
        setRegions(regions);
        size = config.SIZE;
        hp = config.HP;
        damage = config.DAMAGE;
        triggerTIMESPAN = config.FIRE_FREQ;
        triggerCounter = triggerTIMESPAN / 2;
        bulletSpeedFactor = config.BULLET_SPEED;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (autoFire) runAutoFire(delta);
        blinkCounter += delta;
        if (blinkCounter > BLINK_DURATION)
            frame = 0;
    }

    public void shoot(boolean trigger) {
        if (!trigger) return;
        if (sound != null)
            sound.play();
    }

    protected void runAutoFire(float delta) {
        triggerCounter += delta;
        if (triggerCounter > triggerTIMESPAN) {
            shoot(autoFire);
            triggerCounter -= triggerTIMESPAN;
        }
    }

    @Override
    public boolean collide(Collide object, Rectangle bounds) {
        if (position.dst2(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2)
                < spriteBounds.height * spriteBounds.height * spriteBounds.height) {
            takeDamage(object.giveDamage());
            object.takeDamage(giveDamage());
            return true;
        }
        return false;
    }

    @Override
    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) setDestroyed(true);
    }

    @Override
    public int giveDamage() {
        return damage;
    }

    @Override
    public void setDestroyed(boolean destroyed) {
        super.setDestroyed(destroyed);
        Explosion explosion = explosionPool.obtain();
        explosion.setup(position.x, position.y, spriteBounds.height);
    }
}
