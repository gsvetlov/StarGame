package ru.svetlov.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen extends BaseScreen {
    private final Texture background;
    private final Texture followerImage;
    private final TrackingObject follower;

    public MenuScreen() {
        background = new Texture("background02-1024x2048.png");
        followerImage = new Texture("badlogic.jpg");
        follower = new TrackingObject(new Vector2(userDeviceWidth/2f,userDeviceHeight/2f), new Vector2(0,0), new Vector2(0,0));
        eventProvider.setTouchDownEventHandler(this::onTouchDown);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor((InputProcessor)eventProvider);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        follower.update(delta);
        ScreenUtils.clear(0.33f, 0.45f, 0.66f, 1);
        batch.begin();
        batch.draw(background, 0,0, userDeviceWidth, userDeviceHeight);
        batch.draw(followerImage, follower.getPosition().x, follower.getPosition().y, 30,30);
        batch.end();
    }


    private void onTouchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touch detected screenX "+ screenX + " screenY " + screenY + " pointer " + pointer +
                " button " + button);
        follower.getToPosition(new Vector2(screenX, userDeviceHeight - screenY), new Vector2(100,0));
    }
}
