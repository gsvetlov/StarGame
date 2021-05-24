package ru.svetlov.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.svetlov.base.InputEventProviderFactory;
import ru.svetlov.user.controller.UserInputEventProvider;

public abstract class BaseScreen implements Screen {
    protected SpriteBatch batch;
    protected int userDeviceWidth;
    protected int userDeviceHeight;
    protected boolean isPaused;
    protected UserInputEventProvider eventProvider;

    public BaseScreen(){
        eventProvider = InputEventProviderFactory.getFactory().getClassUserEventProvider(this);
        userDeviceHeight = Gdx.graphics.getHeight();
        userDeviceWidth = Gdx.graphics.getWidth();
    }

    @Override
    public abstract void show();

    @Override
    public abstract void render(float delta);

    @Override
    public void resize(int width, int height) {
        userDeviceHeight = height;
        userDeviceWidth = width;
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        if (batch != null)
            batch.dispose();
    }
}
