package ru.svetlov.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

import ru.svetlov.base.SpritesPool;
import ru.svetlov.model.AlienShip;
import ru.svetlov.model.configuration.ShipConfiguration;

public class AlienPool extends SpritesPool<AlienShip> {

    @Override
    protected AlienShip newObject()
    {
        return new AlienShip();
    }


}
