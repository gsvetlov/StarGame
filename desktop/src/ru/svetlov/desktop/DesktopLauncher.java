package ru.svetlov.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.svetlov.GbStarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = createConfig();
		new LwjglApplication(new GbStarGame(), config);
	}

	private static LwjglApplicationConfiguration createConfig() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "StarGame";
		config.height = 896;
		config.width = 448;
		config.resizable = false;
		config.x = -1;
		config.y = 50;
		return config;
	}
}
