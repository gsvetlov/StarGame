package ru.svetlov.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.svetlov.base.BaseScreen;
import ru.svetlov.base.UserInputEventProvider;
import ru.svetlov.base.util.TextureRegions;
import ru.svetlov.model.Background;
import ru.svetlov.model.Star;
import ru.svetlov.model.PlayerShip;

public class MenuScreen extends BaseScreen {
    private final TextureAtlas atlas;
    private final TextureAtlas gameAtlas;
    private Texture bgTexture;
    private Background background;
    private PlayerShip playerShip;
    private Star[] stars;


    public MenuScreen(UserInputEventProvider userInputEventProvider) {
        super(userInputEventProvider);
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        gameAtlas = new TextureAtlas("textures/mainAtlas.tpack");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor((InputProcessor) userEventProvider);
        bgTexture = new Texture("bg01.png");
        background = new Background(bgTexture);
        TextureRegion[] playerTextures = TextureRegions.split(
                gameAtlas.findRegion("main_ship"), 1, 2, 2);
        playerShip = new PlayerShip(userEventProvider, screenToWorld, playerTextures);
        stars = new Star[128];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas.findRegion("star"));
        }
        batch = new SpriteBatch();
    }

    private void update(float delta) {
        for (Star s : stars)
            s.update(delta);
        playerShip.update(delta);
    }

    private void draw(SpriteBatch batch) {
        background.draw(batch);
        for (Star s : stars)
            s.draw(batch);
        playerShip.draw(batch);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.33f, 0.45f, 0.66f, 1);
        ScreenUtils.clear(0.3f, 0.3f, 0.3f, 1);
        update(delta);
        batch.begin();
        draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rectangle worldBounds) {
        background.resize(worldBounds);
        playerShip.resize(worldBounds);
        for (Star s : stars)
            s.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        playerShip.dispose();
        bgTexture.dispose();
        atlas.dispose();
        gameAtlas.dispose();
    }
}
