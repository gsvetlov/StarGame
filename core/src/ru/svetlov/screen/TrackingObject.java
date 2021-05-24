package ru.svetlov.screen;

import com.badlogic.gdx.math.Vector2;

import ru.svetlov.base.GameObject;

public class TrackingObject extends GameObject {
    private Vector2 targetPosition;

    public TrackingObject(Vector2 position, Vector2 velocity, Vector2 acceleration) {
        super(position, velocity, acceleration);
        targetPosition = position.cpy();
    }

    public void getToPosition(Vector2 pos, Vector2 vel){
        System.out.println("going to position");
        targetPosition = pos.cpy();
        velocity.set(targetPosition.cpy().sub(position).nor().scl(vel.len()));
        System.out.println("velocity : " + velocity.x + "," + velocity.y);
    }

    @Override
    public void update(float delta) {
        checkPositionReached(delta);
        super.update(delta);

    }

    private void checkPositionReached(float delta){
        float rangeToTarget = targetPosition.cpy().sub(position).len();
        if (rangeToTarget < velocity.len()*delta){
            velocity.set(0,0);
            System.out.println("position reached");
        }
    }
}
