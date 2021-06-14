package ru.svetlov.model;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import ru.svetlov.base.util.Rnd;
import ru.svetlov.base.util.TextureRegions;
import ru.svetlov.model.configuration.ShipConfiguration;
import ru.svetlov.model.configuration.ShipConfigurationProvider;
import ru.svetlov.model.configuration.ShipType;
import ru.svetlov.pool.AlienPool;

public class AlienGenerator {
    private final ShipConfigurationProvider configProvider;
    private final TextureAtlas atlas;
    private final Map<String, TextureRegion[]> textures;

    private final float generationTime;
    private final AlienPool aliens;
    private float generationTimeCounter;
    private Rectangle worldBounds;

    public AlienGenerator(ShipConfigurationProvider provider, TextureAtlas atlas, float generationTime, AlienPool alienPool, Rectangle worldBounds) {
        this.generationTime = generationTime;
        this.aliens = alienPool;
        this.worldBounds = worldBounds;
        this.configProvider = provider;
        this.atlas = atlas;
        textures = new HashMap<>();
    }

    public void update(float delta) {
        generationTimeCounter += delta;
        if (generationTimeCounter > generationTime) {
            generateAlien();
            generationTimeCounter -= generationTime;
        }
    }

    private void generateAlien() {
        ShipType type = getRandomType();
        ShipConfiguration alienConfig = configProvider.getConfiguration(type);
        AlienShip alien = aliens.obtain();
        alien.set(obtainTexture(alienConfig.TEXTURE_NAME), alienConfig);
        alien.resize(worldBounds);
        float posX = Rnd.nextFloat(
                worldBounds.x + alien.getWidth() / 2,
                worldBounds.x + worldBounds.width - alien.getWidth() / 2);
        float posY = worldBounds.y + worldBounds.height * 1.05f;
        alien.setPosition(posX, posY);
        //System.out.println("alien ship " + type.name() + " created @ " + alien.getPosition());
    }

    private TextureRegion[] obtainTexture(String texture_name) {
        if (!textures.containsKey(texture_name)) {
            TextureAtlas.AtlasRegion region = atlas.findRegion(texture_name);
            if (region == null)
                throw new NoSuchElementException("No such texture name: " + texture_name);
            textures.put(texture_name, TextureRegions.split(region, 1, 2, 2));
        }
        return textures.get(texture_name);
    }

    private ShipType getRandomType() {
        float seed = Rnd.nextFloat(0, 1);
        if (seed > 0.9f) return ShipType.BigAlien;
        if (seed > 0.6f) return ShipType.MiddleAlien;
        return ShipType.SmallAlien;
    }


}
