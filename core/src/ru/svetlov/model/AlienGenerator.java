package ru.svetlov.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.svetlov.base.util.Rnd;
import ru.svetlov.pool.AlienPool;

public class AlienGenerator {
    private final float generationTime;
    private final AlienPool aliens;
    private float generationTimeCounter;
    private Rectangle worldBounds;

    public AlienGenerator(float generationTime, AlienPool alienPool, Rectangle worldBounds) {
        this.generationTime = generationTime;
        this.aliens = alienPool;
        this.worldBounds = worldBounds;
    }

    public void update(float delta) {
        generationTimeCounter += delta;
        if (generationTimeCounter > generationTime) {
            generateAlien();
            generationTimeCounter -= generationTime;
        }
    }

    private void generateAlien() {
        AlienShip alien = aliens.obtain();
        alien.resize(worldBounds);
        Vector2 p0 = new Vector2(Rnd.nextFloat(worldBounds.x, worldBounds.x + worldBounds.width), worldBounds.y + worldBounds.height);
        alien.setPosition(p0);
        alien.setVelocity(new Vector2(0, -0.15f));

        System.out.println("alien ship created @ " + p0);
    }


}
