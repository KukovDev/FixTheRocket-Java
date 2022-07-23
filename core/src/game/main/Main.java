package game.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
	// Переменные окна: ------------------------------------------------------------------------------------------------
	public static String window_Title = "FixTheRocket";  // Заголовок окна.
	public static int window_FPS = 60;                   // Кол-во кадров обновления окна.
	public static boolean window_vSync = true;           // Вертикальная сихронизация окна.
	public static int window_width = 960;                // Ширина окна.
	public static int window_height = 540;               // Высота окна.
	// -----------------------------------------------------------------------------------------------------------------

	// Переменные игры: ------------------------------------------------------------------------------------------------
	private SpriteBatch block_batch;         // Партия спрайтов блоков.
	private SpriteBatch blocks_layer1;       // Слой отрисовываемых блоков 1.

	private final int tilemap_height = 32;   // Размер карты по высоте.
	private final int tilemap_width = 32;    // Размер карты по ширине.
	private int[][] tilemap;
	// -----------------------------------------------------------------------------------------------------------------

	public void create () {
		// Вызывается при запуске игры.

		block_batch = new SpriteBatch();
		blocks_layer1 = new SpriteBatch();

		tilemap = TileMap.Create(tilemap_height, tilemap_width, -1);  // Загрузка-создание карты.
	}

	public void render () {
		// Вызывается всегда.
		ScreenUtils.clear(0f, 0f, 0f, 1f);  // Зарисовка экрана.
		Gdx.graphics.setTitle(window_Title + " | FPS: " + Gdx.graphics.getFramesPerSecond());  // Отображение FPS.

		block_batch.begin();    // Отрисовка блоков.
		blocks_layer1.begin();  // Отрисовка первого слоя блоков.
		// Проход по блокам и отрисовка на партии:
		for (int block_y=0; block_y<=tilemap_height-1; block_y++) {
			for (int block_x=0; block_x<=tilemap_width-1; block_x++) {
				float pos_y = block_y * Load.snow_land_block.getHeight();
				float pos_x = block_x * Load.snow_land_block.getWidth();

				if (tilemap[block_y][block_x] == -1) block_batch.draw(Load.snow_land_block, pos_x, pos_y);
				else block_batch.draw(Load.bad_land_block, pos_x, pos_y);
			}
		}
		blocks_layer1.end();
		block_batch.end();
	}

	public void resize (int width, int height) {
		// Вызывается при изменении размера окна.
	}

	/* Эти блоки кода пока что не нужны.
	public void pause () {
		// Вызывается когда пользователь переключается с игры на другое окно.
	}

	public void resume () {
		// Вызывается когда пользователь переключается с другого окна на игру.
	}
	*/

	public void dispose () {
		// Вызывается при закрытии окна. Здесь надо указать все ресурсы(текстуры, звуки, ...) игры которые надо удалить.
		Load.dispose();
		block_batch.dispose();
		blocks_layer1.dispose();
	}
}
