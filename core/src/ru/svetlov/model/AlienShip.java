package ru.svetlov.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.svetlov.base.Sprite;
import ru.svetlov.model.configuration.ShipConfiguration;

public class AlienShip extends Ship {
    private Rectangle activeBounds;
    private float blinkCounter;
    private static final float BLINK_DURATION = 0.5f;

    public AlienShip(){

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
            System.out.println("alienShip out of bounds");
        }
        blinkCounter +=delta;
        if (blinkCounter > BLINK_DURATION)
            frame = 0;

    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        frame = 1;
        blinkCounter = 0;
        System.out.println("[alien] damage taken: " + damage);
    }

    @Override
    public int giveDamage() {
        System.out.println("[alien] damage given");
        return super.giveDamage();
    }
}
