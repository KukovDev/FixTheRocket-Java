package game.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import game.main.screens.GameScreen;

public class Main extends Game {
	// Переменные окна: ------------------------------------------------------------------------------------------------
	public static String window_Title = "FixTheRocket"; // Заголовок окна.
	public static int window_FPS = 60;                  // Кол-во кадров обновления окна.
	public static boolean window_vSync = true;          // Вертикальная сихронизация окна.
	public static int window_width = 960;               // Ширина окна.
	public static int window_height = 540;              // Высота окна.
	// -----------------------------------------------------------------------------------------------------------------

	@Override
	public void create() {
		// Вызывается при запуске окна.
		Screen gameScreen = new GameScreen();
		setScreen(gameScreen); // Переключение на игровой скрин. (P.S: ./Screens/GameScreen.java)
	}
}
