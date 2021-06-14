package ru.svetlov.model;

import com.badlogic.gdx.math.Rectangle;

public interface Collide {
    boolean collide(Collide object, Rectangle bounds);
    void takeDamage(int damage);
    int giveDamage();
}
