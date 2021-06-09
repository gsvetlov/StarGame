package ru.svetlov;

import com.badlogic.gdx.Game;

import ru.svetlov.screen.GameScreen;
import ru.svetlov.user.controller.BasicUserInputProcessor;

public class GbStarGame extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen(new BasicUserInputProcessor()));
	}


}
