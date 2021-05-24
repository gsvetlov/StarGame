package ru.svetlov;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import ru.svetlov.screen.MenuScreen;
import ru.svetlov.user.controller.BasicUserInputProcessor;

public class GbStarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen());
	}


}
