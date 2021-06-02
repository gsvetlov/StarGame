package ru.svetlov.base;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.svetlov.base.util.MatrixUtils;

public abstract class BaseScreen implements Screen {
    protected Rectangle screenBounds;
    protected Rectangle worldBounds;
    protected final Rectangle glBounds;
    protected Matrix4 worldToGl;
    protected Matrix3 screenToWorld;
    protected SpriteBatch batch;
    protected boolean isPaused;
    protected UserInputEventProvider userEventProvider;

    public BaseScreen(UserInputEventProvider userInputEventProvider) {
        userEventProvider = userInputEventProvider;
        screenBounds = new Rectangle();
        worldBounds = new Rectangle(0, 0, 0, 1f);
        glBounds = new Rectangle(0, 0, 2f, 2f).setCenter(0,0);
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
    }

    @Override
    public abstract void show();

    @Override
    public abstract void render(float delta);

    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width, height);
        worldBounds.setWidth(screenBounds.getAspectRatio()).setCenter(0,0);
        MatrixUtils.getTransitionMatrix(worldToGl, worldBounds, glBounds);
        MatrixUtils.getTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);
        userEventProvider.setScreenBounds(screenBounds);
    }

    public abstract void resize(Rectangle worldBounds);

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
