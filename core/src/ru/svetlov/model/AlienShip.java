package ru.svetlov.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import ru.svetlov.base.Sprite;

public class AlienShip extends Sprite {
    private Rectangle worldBounds;

    public AlienShip(TextureRegion[] regions){
        super(regions);
    }

    @Override
    public void resize(Rectangle worldBounds) {
        this.worldBounds = worldBounds;
        setHeight(.1f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (!worldBounds.contains(position)){
            setDestroyed(true);
            System.out.println("alienShip destroyed");
        }

    }
}
