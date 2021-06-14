package ru.svetlov.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;


import java.util.List;

import ru.svetlov.base.BaseScreen;
import ru.svetlov.base.UserInputEventProvider;
import ru.svetlov.base.util.TextureRegions;
import ru.svetlov.factory.Factory;
import ru.svetlov.model.AlienGenerator;
import ru.svetlov.model.AlienShip;
import ru.svetlov.model.Background;
import ru.svetlov.model.Bullet;
import ru.svetlov.model.Collide;
import ru.svetlov.model.GameOver;
import ru.svetlov.model.Star;
import ru.svetlov.model.PlayerShip;
import ru.svetlov.model.configuration.StaticConfigurationProvider;
import ru.svetlov.pool.AlienPool;
import ru.svetlov.pool.BulletPool;
import ru.svetlov.pool.ExplosionPool;

public class GameScreen extends BaseScreen {
    private final TextureAtlas gameAtlas;
    private final Music music;
    private final Sound alienSound;
    private final Sound playerSound;

    private final Background background;
    private final Star[] stars;

    private final BulletPool bulletPool;
    private final ExplosionPool explosionPool;
    private PlayerShip playerShip;
    private AlienGenerator generator;
    private AlienPool aliensPool;

    private GameOver gameOver;

    public GameScreen(
            Game game,
            UserInputEventProvider userInputEventProvider,
            TextureAtlas gameAtlas,
            Background gameBackground,
            Sound playerSound,
            Sound alienSound,
            Sound explosionSound,
            Music music,
            Star[] stars){
        super(game, userInputEventProvider);
        this.gameAtlas = gameAtlas;
        this.background = gameBackground;
        this.playerSound = playerSound;
        this.alienSound = alienSound;
        this.music = music;
        this.stars = stars;

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(
                TextureRegions.split(gameAtlas.findRegion("explosion"),
                        9,9,74),explosionSound);

    }

//    public GameScreen(Game game, UserInputEventProvider userInputEventProvider) {
//        super(game, userInputEventProvider);
//        atlas = new TextureAtlas("textures/menuAtlas.tpack");
//        gameAtlas = new TextureAtlas("textures/mainAtlas.tpack");
//        bgTexture = new Texture("bg01.png");
//        background = new Background(bgTexture);
//        playerSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
//        alienSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
//        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
//        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
//        bulletPool = new BulletPool();
//       explosionPool = new ExplosionPool(
//                TextureRegions.split(
//                        gameAtlas.findRegion("explosion"),
//                        9,
//                        9,
//                        74),
//                explosionSound);
//    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor((InputProcessor) userEventProvider);
        gameOver = new GameOver(gameAtlas.findRegion("message_game_over"), this::screenSwitch);
        TextureRegion playerBullet = gameAtlas.findRegion("bulletMainShip");
        TextureRegion[] playerTextures = TextureRegions.split(
                gameAtlas.findRegion("main_ship"), 1, 2, 2);
        playerShip = new PlayerShip(userEventProvider, screenToWorld, playerTextures, playerBullet, bulletPool, explosionPool, playerSound);
        TextureRegion alienBullet = gameAtlas.findRegion("bulletEnemy");
        aliensPool = new AlienPool(playerTextures, alienBullet, bulletPool, explosionPool, alienSound);
//        stars = new Star[128]; // TODO refactor to use SpritePool
//        for (int i = 0; i < stars.length; i++) {
//            stars[i] = new Star(atlas.findRegion("star"));
//        }
        generator = new AlienGenerator(new StaticConfigurationProvider(), gameAtlas, 5f, aliensPool, worldBounds);
        batch = new SpriteBatch();
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();
    }

    private void update(float delta) {
        generator.update(delta);
        for (Star s : stars)
            s.update(delta);
        explosionPool.updateActiveSprites(delta);
        playerShip.update(delta);
        aliensPool.updateActiveSprites(delta);
        bulletPool.updateActiveSprites(delta);
        checkCollisions();
        gameOver.update(delta);
        if (!gameOver.isActive() && playerShip.isDestroyed())
            gameOver.run(0,0);
    }

    private void checkCollisions() {
        for (AlienShip ship : aliensPool.getActiveObjects()) {
            if (!playerShip.isDestroyed())
                playerShip.collide(ship, ship.getBounds());
            for (Bullet bullet : bulletPool.getActiveObjects()) {
                if (bullet.getOwner() != null)
                    ship.collide(bullet, bullet.getBounds());
                else
                    playerShip.collide(bullet, bullet.getBounds());
            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyed();
        aliensPool.freeAllDestroyed();
        explosionPool.freeAllDestroyed();
    }

    private void draw(SpriteBatch batch) {
        background.draw(batch);
        for (Star s : stars)
            s.draw(batch);
        playerShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        aliensPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        if (gameOver.isActive())
            gameOver.draw(batch);
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
        gameOver.resize(worldBounds);
        playerShip.resize(worldBounds);
        for (Star s : stars)
            s.resize(worldBounds);
    }

    @Override
    public void dispose() {
        System.out.println("gameScreen disposing");
        super.dispose();
//        bulletPool.dispose();
//        explosionPool.dispose();
//        aliensPool.dispose();
//        playerShip.dispose();
//        gameOver.dispose();
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

    private void screenSwitch() {
        System.out.println("screen switch active");
        game.setScreen(Factory.getFactory().getNewGameScreen(game));
    }
}
