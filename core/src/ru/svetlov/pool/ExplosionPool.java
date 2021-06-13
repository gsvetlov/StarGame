package ru.svetlov.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.svetlov.base.SpritesPool;
import ru.svetlov.model.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private final TextureRegion[] textures;
    private final Sound sound;

    public ExplosionPool(TextureRegion[] textures, Sound sound) {
        this.textures = textures;
        this.sound = sound;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(textures, sound);
    }
}
