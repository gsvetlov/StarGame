package ru.svetlov.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public abstract class BaseScreen implements Screen {
    protected SpriteBatch batch;
    protected int userDeviceWidth;
    protected int userDeviceHeight;
    protected InputProcessor processor;

    public BaseScreen(InputProcessor processor){
        this.processor = processor;
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

    }

    @Override
    public void resume() {

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
