package game.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		// Установка значений: -----------------------------------------------------------------------------------------
		config.setTitle(Main.window_Title);
		config.setResizable(true);
		config.useVsync(Main.window_vSync);
		config.setForegroundFPS(Main.window_FPS);
		config.setWindowedMode(Main.window_width, Main.window_height);
		// -------------------------------------------------------------------------------------------------------------

		new Lwjgl3Application(new Main(), config);
	}
}
