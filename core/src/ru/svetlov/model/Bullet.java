package ru.svetlov.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.svetlov.base.Sprite;

public class Bullet extends Sprite implements Collide {
    private Rectangle worldBounds;
    private int damage;
    private Sprite owner;

    public Bullet(){
        regions = new TextureRegion[1];
        velocity= new Vector2();
        position = new Vector2();
        acceleration = new Vector2();
    }

    @Override
    public void resize(Rectangle worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void set(
            Sprite owner,
            TextureRegion region,
            Vector2 pos0,
            Vector2 v0,
            Rectangle worldBounds,
            int damage,
            float height) {
        this.owner = owner;
        regions[0] = region;
        spriteBounds = new Rectangle(0, 0, regions[0].getRegionWidth(), regions[0].getRegionHeight());
        position.set(pos0);
        velocity.set(v0);
        this.worldBounds = worldBounds;
        this.damage = damage;
        setHeight(height);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (!worldBounds.contains(spriteBounds))
            setDestroyed(true);
    }

    public int getDamage() {
        return damage;
    }

    public Sprite getOwner() {
        return owner;
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
        destroyed = true;
    }

    @Override
    public int giveDamage() {
        return damage;
    }
}
