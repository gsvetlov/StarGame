package ru.svetlov.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.graalvm.compiler.word.Word;

import ru.svetlov.base.Sprite;
import ru.svetlov.base.util.Rnd;

public class Star extends Sprite {

    public Star(TextureRegion region) {
        super(region);
        velocity.set(0,Rnd.nextFloat(-0.01f,-0.1f));
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (position.y < worldBounds.y)
            position.set(Rnd.nextFloat(worldBounds.x, worldBounds.x + worldBounds.width), worldBounds.y + worldBounds.height);
    }

    @Override
    public void resize(Rectangle worldBounds) {
        super.resize(worldBounds);
        setHeight(0.005f);
        float x = Rnd.nextFloat(worldBounds.x, worldBounds.x + worldBounds.width);
        float y = Rnd.nextFloat(worldBounds.y, worldBounds.y + worldBounds.height);
        position.set(x, y);
    }


}
