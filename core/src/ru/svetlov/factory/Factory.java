package ru.svetlov.factory;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.svetlov.base.UserInputEventProvider;
import ru.svetlov.base.util.TextureRegions;
import ru.svetlov.model.Background;
import ru.svetlov.model.NewGameButton;
import ru.svetlov.model.Star;
import ru.svetlov.pool.BulletPool;
import ru.svetlov.pool.ExplosionPool;
import ru.svetlov.screen.GameScreen;
import ru.svetlov.screen.NewGameScreen;
import ru.svetlov.user.controller.BasicUserInputProcessor;

public class Factory {
    private static Factory instance;

    public static Factory getFactory() {
        if (instance != null)
            return instance;
        instance = new Factory();
        return instance;
    }

    private final UserInputEventProvider userInputEventProvider;
    private final TextureAtlas atlas;
    private final TextureAtlas gameAtlas;
    private final Texture bgTexture;
    private final Texture menuTexture;
    private final Music music;
    private final Sound explosionSound;
    private final Sound alienSound;
    private final Sound playerSound;
    private Star[] stars;

    private Factory() {
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        gameAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        bgTexture = new Texture("bg01.png");
        menuTexture = new Texture("bg02.png");
        playerSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        alienSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        userInputEventProvider = new BasicUserInputProcessor();
    }

    public Star[] getStars() {
        if (stars != null) return stars;
        stars = new Star[128]; // TODO refactor to use SpritePool
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas.findRegion("star"));
        }
        return stars;
    }

    public Background getGameBackground() {
        return new Background(new TextureRegion(bgTexture));
    }

    public Background getMenuBackground() {
        return new Background(new TextureRegion(menuTexture));
    }

    public UserInputEventProvider getUserInputEventProvider() {
        return userInputEventProvider;
    }

    public GameScreen getGameScreen(Game game) {
        return new GameScreen(game, userInputEventProvider, gameAtlas, getGameBackground(), playerSound, alienSound, explosionSound, music, getStars());
    }

    public NewGameScreen getNewGameScreen(Game game) {
        return new NewGameScreen(game, userInputEventProvider, gameAtlas.findRegion("button_new_game"), getMenuBackground(), getStars());
//        game.setScreen(new NewGameScreen(
//                game,
//                userEventProvider,
//                gameAtlas.findRegion("button_new_game"),
//                background,
//                stars));
    }
}
