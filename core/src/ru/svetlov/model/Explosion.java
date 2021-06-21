package ru.svetlov.model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.svetlov.base.Sprite;

public class Explosion extends Sprite {
    private final static float ANIMATION_SPEED = 0.02f;
    private final Sound sound;
    private float animationCounter;

    public Explosion(TextureRegion[] textures, Sound sound) {
        super(textures, new Vector2(), new Vector2(), new Vector2());
        this.sound = sound;
    }

    public void setup(float x, float y, float height) {
        super.setPosition(x, y);
        super.setHeight(height);
        setDestroyed(false);
    }

    @Override
    public void update(float delta) {
        animationCounter += delta;
        if (!destroyed && animationCounter > ANIMATION_SPEED) {
            animationCounter -= ANIMATION_SPEED;
            if (++frame == regions.length){
                frame = 0;
                destroyed = true;
            }
        }
    }

    @Override
    public void setDestroyed(boolean destroyed) {
        if (!this.destroyed && sound != null) {
            sound.play();
        }
        super.setDestroyed(destroyed);
    }
}
