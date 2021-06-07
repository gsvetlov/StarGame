package ru.svetlov.pool;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.svetlov.base.SpritesPool;
import ru.svetlov.model.AlienShip;

public class AlienPool extends SpritesPool<AlienShip> {
    private TextureRegion[] regions;

    public AlienPool(TextureRegion[] regions){
        this.regions = regions;
    }

    @Override
    protected AlienShip newObject() {
        return new AlienShip(regions);
    }
}
