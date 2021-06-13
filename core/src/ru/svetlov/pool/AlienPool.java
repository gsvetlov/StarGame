package ru.svetlov.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

import ru.svetlov.base.SpritesPool;
import ru.svetlov.base.util.TextureRegions;
import ru.svetlov.model.AlienShip;
import ru.svetlov.model.configuration.ShipConfiguration;

public class AlienPool extends SpritesPool<AlienShip> {
    private final TextureRegion[] regions;
    private final TextureRegion bulletTexture;
    private final BulletPool bullets;
    private final ExplosionPool explosions;
    private final Sound sound;

    public AlienPool(TextureRegion[] regions, TextureRegion bulletTexture, BulletPool bullets, ExplosionPool explosions, Sound sound) {
        this.regions = regions;
        this.bulletTexture = bulletTexture;
        this.bullets = bullets;
        this.explosions = explosions;
        this.sound = sound;
    }

    @Override
    protected AlienShip newObject()
    {
        return new AlienShip(
                regions,
                bulletTexture,
                bullets,
                explosions,
                sound);
    }


}
