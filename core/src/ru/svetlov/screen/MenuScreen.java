package ru.svetlov.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.svetlov.base.BaseScreen;
import ru.svetlov.base.UserInputEventProvider;
import ru.svetlov.model.Background;
import ru.svetlov.model.TrackingSprite;

public class MenuScreen extends BaseScreen {
    private final Texture bgTexture;
    private final Background background;
    private final Texture logoImage;
    private final TrackingSprite follower;
    private final Vector2 touch;

    public MenuScreen(UserInputEventProvider userInputEventProvider) {
        super(userInputEventProvider);
        bgTexture = new Texture("bg02.png");
        background = new Background(bgTexture);
        logoImage = new Texture("badlogic.jpg");
        follower = new TrackingSprite(logoImage, new Vector2(), new Vector2(), new Vector2());
        touch = new Vector2();
        userEventProvider.setTouchDownEventHandler(this::onTouchDown);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor((InputProcessor) userEventProvider);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        follower.update(delta);
        ScreenUtils.clear(0.33f, 0.45f, 0.66f, 1);
        batch.begin();
        background.draw(batch);
        follower.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rectangle worldBounds) {
        background.resize(worldBounds);
        follower.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bgTexture.dispose();
        logoImage.dispose();
    }

    private void onTouchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.height - screenY).mul(screenToWorld);
        follower.getToPosition(touch, 0.1f);
    }
}
