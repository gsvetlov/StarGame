package ru.svetlov.base;

import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    protected Vector2 position;
    protected Vector2 velocity;
    protected Vector2 acceleration;

    public GameObject(Vector2 position, Vector2 velocity, Vector2 acceleration) {
        this.position = position.cpy();
        this.velocity = velocity.cpy();
        this.acceleration = acceleration.cpy();
    }

    public void update(float delta){
        updatePosition(delta);
        updateVelocity(delta);
    }

    private void updatePosition(float delta){
        position.mulAdd(velocity,delta);
    }

    private void updateVelocity(float delta) {
        velocity.mulAdd(acceleration, delta);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position.cpy();
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity.cpy();
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration.cpy();
    }
}
