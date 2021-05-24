package ru.svetlov.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen extends BaseScreen {
    private Texture background;

    public MenuScreen(InputProcessor processor) {
        super(processor);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture("background01-512x1024.png");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.33f, 0.45f, 0.66f, 1);
        batch.begin();
        batch.draw(background, 0,0, userDeviceWidth, userDeviceHeight);
        batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        super.dispose();
    }
}
