package ru.svetlov;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GbStarGame extends ApplicationAdapter {
	private int userDeviceWidth;
	private int userDeviceHeight;
	private SpriteBatch batch;
	private Texture background;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background01-512x1024.png");
		userDeviceHeight = Gdx.graphics.getHeight();
		userDeviceWidth = Gdx.graphics.getWidth();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.33f, 0.45f, 0.66f, 1);
		batch.begin();
		batch.draw(background, 0,0, userDeviceWidth, userDeviceHeight);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
