package ru.svetlov.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;


import ru.svetlov.base.BaseScreen;
import ru.svetlov.base.UserInputEventProvider;
import ru.svetlov.base.util.TextureRegions;
import ru.svetlov.model.AlienGenerator;
import ru.svetlov.model.Background;
import ru.svetlov.model.Star;
import ru.svetlov.model.PlayerShip;
import ru.svetlov.pool.AlienPool;
import ru.svetlov.pool.BulletPool;

public class MenuScreen extends BaseScreen {
    private final TextureAtlas atlas;
    private final TextureAtlas gameAtlas;
    private final Texture bgTexture;
    private Background background;
    private PlayerShip playerShip;
    private Star[] stars;
    private final BulletPool bulletPool;
    private final Music music;
    private final AlienPool aliensPool;
    private AlienGenerator generator;


    public MenuScreen(UserInputEventProvider userInputEventProvider) {
        super(userInputEventProvider);
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        gameAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        bgTexture = new Texture("bg01.png");
        bulletPool = new BulletPool();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        aliensPool = new AlienPool(TextureRegions.split(gameAtlas.findRegion("enemy0"), 1,2,2));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor((InputProcessor) userEventProvider);
        background = new Background(bgTexture);
        TextureRegion bulletRegion = gameAtlas.findRegion("bulletMainShip");
        TextureRegion[] playerTextures = TextureRegions.split(
                gameAtlas.findRegion("main_ship"), 1, 2, 2);
        playerShip = new PlayerShip(userEventProvider, screenToWorld, playerTextures, bulletPool, bulletRegion);
        stars = new Star[128]; // TODO refactor to use SpritePool
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas.findRegion("star"));
        }
        generator = new AlienGenerator(5f, aliensPool, worldBounds);
        batch = new SpriteBatch();
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();

    }

    private void update(float delta) {
        generator.update(delta);
        for (Star s : stars)
            s.update(delta);
        playerShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        aliensPool.updateActiveSprites(delta);
    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyed();
        aliensPool.freeAllDestroyed();
    }

    private void draw(SpriteBatch batch) {
        background.draw(batch);
        for (Star s : stars)
            s.draw(batch);
        playerShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        aliensPool.drawActiveSprites(batch);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.33f, 0.45f, 0.66f, 1);
        update(delta);
        freeAllDestroyed();
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
        music.dispose();
        bgTexture.dispose();
        atlas.dispose();
        gameAtlas.dispose();
        playerShip.dispose();
        bulletPool.dispose();
        aliensPool.dispose();
    }

    @Override
    public void pause() {
        super.pause();
        music.pause();
    }

    @Override
    public void resume() {
        super.resume();
        music.play();
    }
}
