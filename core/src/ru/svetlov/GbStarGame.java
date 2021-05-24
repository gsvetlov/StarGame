package ru.svetlov;

import com.badlogic.gdx.Game;

import ru.svetlov.screen.MenuScreen;
import ru.svetlov.user.controller.BaseUserInputProcessor;

public class GbStarGame extends Game {
	@Override
	public void create() {
		setScreen(new MenuScreen(new BaseUserInputProcessor()));
	}
}
