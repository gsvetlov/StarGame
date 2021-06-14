package ru.svetlov.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import ru.svetlov.base.Sprite;

public class Background extends Sprite {

    public Background(TextureRegion texture) {
        super(texture);
    }

    @Override
    public void resize(Rectangle worldBounds) {
        super.resize(worldBounds);
        setHeight(1f);
    }
}
