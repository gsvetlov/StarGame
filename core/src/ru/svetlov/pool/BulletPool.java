package ru.svetlov.pool;

import ru.svetlov.base.SpritesPool;
import ru.svetlov.model.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
