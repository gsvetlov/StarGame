package ru.svetlov.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.svetlov.base.Sprite;
import ru.svetlov.model.configuration.ShipConfiguration;
import ru.svetlov.pool.BulletPool;

public abstract class Ship extends Sprite implements Collide {
    protected BulletPool bullets;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletPosition;
    protected Vector2 bulletSpeed;

    protected boolean autoFire;
    protected float triggerCounter;
    protected static float AUTOFIRE_TIMESPAN = 0.25f;
    protected final Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav")); // звук выстрела

    protected int hp;
    protected int damage;
    protected float size;


    public Ship() {

    }

    public Ship(TextureRegion[] regions, Vector2 pos, Vector2 vel, Vector2 acc) {
        super(regions, pos, vel, acc);
    }

    @Override
    public void dispose() {
        super.dispose();
        sound.dispose();
    }

    public void set(TextureRegion[] regions, ShipConfiguration config) {
        setRegions(regions);
        size = config.SIZE;
        hp = config.HP;
        damage = config.DAMAGE;
    }

    public void shoot(boolean trigger) {
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
        if (sound != null)
            sound.play();
    }

    @Override
    public boolean collide(Collide object, Rectangle bounds) {
        if (!spriteBounds.overlaps(bounds)) return false;
        takeDamage(object.giveDamage());
        object.takeDamage(giveDamage());
        return true;
    }

    @Override
    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) destroyed = true;
    }

    @Override
    public int giveDamage() {
        return damage;
    }
}
