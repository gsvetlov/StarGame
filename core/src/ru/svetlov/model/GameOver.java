package ru.svetlov.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.concurrent.Callable;

import ru.svetlov.base.Sprite;

public class GameOver extends Sprite {
    private static final float STARTUP_COUNTER_LIMIT = 3f;
    private static final float DISPLAY_COUNTER_LIMIT = 6f;
    private boolean isActive;
    private float startCounter;
    private float displayCounter;
    private final Runnable callback;

    public GameOver(TextureRegion region, Runnable callback) {
        super(region);
        this.callback = callback;
    }

    public void run(float x, float y) {
        setPosition(x, y);
        isActive = true;
    }

    @Override
    public void resize(Rectangle worldBounds) {
        super.resize(worldBounds);
        setHeight(0.05f);
    }

    @Override
    public void update(float delta) {
        if (!isActive) return;
        startCounter += delta;
        if (startCounter > STARTUP_COUNTER_LIMIT)
            displayCounter +=delta;
        if (displayCounter > DISPLAY_COUNTER_LIMIT){
            isActive = false;
            callback.run();
        }

    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!isActive && (displayCounter == 0 || displayCounter > DISPLAY_COUNTER_LIMIT))
            return;
        super.draw(batch);
    }

    public boolean isActive() {
        return isActive;
    }
}
