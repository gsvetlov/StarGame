package ru.svetlov.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Sprite {

    protected TextureRegion[] regions;
    protected int frame;
    protected float angle;
    protected float scale = 1f;

    protected Rectangle spriteBounds;
    protected Rectangle worldBounds;

    protected Vector2 position;
    protected Vector2 velocity;
    protected Vector2 acceleration;
    protected boolean destroyed;

    public Sprite() {
        position = new Vector2();
        velocity = new Vector2();
        acceleration = new Vector2();
        spriteBounds = new Rectangle();
        worldBounds = new Rectangle();
    }

    public Sprite(TextureRegion[] regions, Vector2 position, Vector2 velocity, Vector2 acceleration) {
        this.regions = regions;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        setRegions(regions);
    }

    public Sprite(TextureRegion region) {
        this(region, new Vector2(), new Vector2(), new Vector2());
    }

    public Sprite(TextureRegion region, Vector2 position, Vector2 velocity, Vector2 acceleration) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        TextureRegion[] regions = new TextureRegion[1];
        regions[0] = region;
        setRegions(regions);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                spriteBounds.x, spriteBounds.y,
                position.x, position.y,
                spriteBounds.width, spriteBounds.height,
                scale, scale,
                angle
        );
    }

    public void resize(Rectangle worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void setHeight(float height) {
        float aspect = spriteBounds.getAspectRatio();
        spriteBounds.setHeight(height).setWidth(height * aspect).setCenter(position);
    }


    public void update(float delta) {
        updateVelocity(delta);
        updatePosition(delta);
    }

    public void dispose() {
    }

    private void updatePosition(float delta) {
        position.mulAdd(velocity, delta);
        spriteBounds.setCenter(position);
    }

    private void updateVelocity(float delta) {
        velocity.mulAdd(acceleration, delta);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
        spriteBounds.setCenter(position);
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
        spriteBounds.setCenter(position);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    public void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration.set(acceleration);
    }

    public void setAcceleration(float x, float y) {
        this.acceleration.set(x, y);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public void setRegions(TextureRegion[] regions) {
        this.regions = regions;
        spriteBounds = new Rectangle(0, 0,
                regions[0].getRegionWidth(), regions[0].getRegionHeight())
                .setCenter(position);
    }

    public float getWidth() {
        return spriteBounds.getWidth();
    }

    public Rectangle getBounds(){
        return spriteBounds;
    }
}
