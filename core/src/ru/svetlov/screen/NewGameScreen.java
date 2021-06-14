package ru.svetlov.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import ru.svetlov.base.BaseScreen;
import ru.svetlov.base.UserInputEventProvider;
import ru.svetlov.factory.Factory;
import ru.svetlov.model.Background;
import ru.svetlov.screen.button.NewGameButton;
import ru.svetlov.model.Star;
import ru.svetlov.screen.button.UserButton;

public class NewGameScreen extends BaseScreen {
    private final TextureRegion newGameTexture;
    private final Background bg;
    private Star[] stars;
    private UserButton btnNewGame;

    public NewGameScreen(Game game, UserInputEventProvider userInputEventProvider, TextureRegion newGameTexture, Background bg, Star[] stars) {
        super(game, userInputEventProvider);
        this.newGameTexture = newGameTexture;
        this.bg = bg;
        this.stars = stars;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor((InputProcessor) userEventProvider);
        btnNewGame = new NewGameButton(
                newGameTexture,
                0.05f,
                0,
                0.15f,
                userEventProvider,
                screenToWorld,
                this::newGameAction);
    }

    private void newGameAction() {
        System.out.println("button Action!");
        game.setScreen(Factory.getFactory().getGameScreen(game));
    }


    @Override
    public void render(float delta) {
        for (Star s : stars)
            s.update(delta);
        batch.begin();
        bg.draw(batch);
        for (Star s : stars)
            s.draw(batch);
        btnNewGame.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rectangle worldBounds) {
        btnNewGame.resize(worldBounds);
        bg.resize(worldBounds);
        for (Star s : stars)
            s.resize(worldBounds);
    }

    @Override
    public void dispose() {
        btnNewGame.dispose();
        super.dispose();
    }
}
