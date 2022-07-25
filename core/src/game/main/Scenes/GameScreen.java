package game.main.Scenes;

// Импорт библиотек: ---------------------------------------------------------------------------------------------------
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import game.main.Main;
import java.util.ArrayList;
import java.util.List;
import game.main.Objects.Player;
// ---------------------------------------------------------------------------------------------------------------------

public class GameScreen implements Screen {
    // Игровые переменные: ---------------------------------------------------------------------------------------------
    OrthographicCamera camera;        // 2D камера.
    float zoom = 0.5f;                // Зум камеры.

    List<Player> list_players;        // TODO Список игроков.

    int[][] tilemap;                  // Карта.
    final int tilemap_height = 4;     // Высота карты.
    final int tilemap_width = 4;      // Ширина карты.
    final short block_unit = 32;     // 1 unit = 32 px.

    int[] mouse_block_pos;    // Позиция мыши в блоках.
    int old_id_block_env;             // Айди поверхности на которую поставили блок.
    // -----------------------------------------------------------------------------------------------------------------

    // Текстуры: -------------------------------------------------------------------------------------------------------
    SpriteBatch batch;   // Партия спрайтов на отрисовку.

    Texture snow1_env;   // Снежная поверхность 1.
    Texture snow2_env;   // Снежная поверхность 2.
    Texture snow3_env;   // Снежная поверхность 3.

    Texture bonfire_sur; // Костёр.

    Texture player_txtr; // Текстура игрока.
    // -----------------------------------------------------------------------------------------------------------------

    // Загрузка спрайтов:
    Texture load_texture(String link) { return new Texture(link); }

    // Проверка на сталкивание двух Rect:
    public boolean isCollision(Rectangle rect1, Rectangle rect2) { return Intersector.overlaps(rect1, rect2); }

    public int[] block_mouse_pos() {
        mouse_block_pos[0] = (Gdx.input.getX() + ((int) camera.position.x - (int) camera.viewportWidth / 4) * 2) /
                (int) (block_unit / zoom);

        mouse_block_pos[1] = (-(Gdx.input.getY() - Gdx.graphics.getHeight()) + ((int) camera.position.y -
                (int) camera.viewportHeight / 4) * 2) / (int) (block_unit / zoom);

        if ((Gdx.input.getX() + ((int) camera.position.x - (int) camera.viewportWidth / 4) * 2) < 0) {
            mouse_block_pos[0] = -1;
        }

        if (-(Gdx.input.getY() - Gdx.graphics.getHeight()) +
                ((int) camera.position.y - (int) camera.viewportHeight / 4) * 2 < 0) {
            mouse_block_pos[1] = -1;
        }

        return mouse_block_pos;
    }

    @Override
    public void show() {
        // Вызывается при переключении на этот скрин.

        // Создание игровых переменных: --------------------------------------------------------------------------------
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        list_players = new ArrayList<>();
        tilemap = game.main.TileMap.TileMap.Create(tilemap_height, tilemap_width);
        mouse_block_pos = new int[2];
        // -------------------------------------------------------------------------------------------------------------

        // Загрузка текстур: -------------------------------------------------------------------------------------------
        batch = new SpriteBatch();

        snow1_env = load_texture("sprites/blocks/environment/snow1.png");
        snow2_env = load_texture("sprites/blocks/environment/snow2.png");
        snow3_env = load_texture("sprites/blocks/environment/snow3.png");
        bonfire_sur = load_texture("sprites/blocks/survival/bonfire/bonfire1.png");
        player_txtr = load_texture("sprites/player/running/1.png");
        // -------------------------------------------------------------------------------------------------------------

        // Создание объектов: ------------------------------------------------------------------------------------------
        list_players.add(new Player(0f, 0f, player_txtr.getWidth(), player_txtr.getHeight()));
        // -------------------------------------------------------------------------------------------------------------
    }

    @Override
    public void dispose() {
        // Вызывается при закрытии окна.
        batch.dispose();
        snow1_env.dispose();
        snow2_env.dispose();
        snow3_env.dispose();
        bonfire_sur.dispose();
        player_txtr.dispose();
    }

    @Override
    public void render(float delta) {
        // Вызывается постоянно.

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f); // > Очистка экрана.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                 // |

        // Время между кадрами.
        float deltaTime = (float) Main.window_FPS / Gdx.graphics.getFramesPerSecond(); // Получение DeltaTime.
        if (deltaTime > 340282356779733661637539395458142568447.9f) deltaTime = 1; // Если DeltaTime больше максимума.

        camera.update(); // Обновление камеры.
        camera.zoom = zoom;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.y += 5 * deltaTime; // > Перемещение камеры.
        if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.position.x -= 5 * deltaTime; // |
        if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.y -= 5 * deltaTime; // |
        if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.position.x += 5 * deltaTime; // |

        Gdx.graphics.setTitle(Main.window_Title + " | FPS: " + Gdx.graphics.getFramesPerSecond());

        // Установка блоков: -------------------------------------------------------------------------------------------
        try {
            mouse_block_pos = block_mouse_pos(); // Получение указания мыши на блок.

            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                old_id_block_env = tilemap[mouse_block_pos[1]][mouse_block_pos[0]];
                tilemap[mouse_block_pos[1]][mouse_block_pos[0]] = 1;
            }

            if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
                tilemap[mouse_block_pos[1]][mouse_block_pos[0]] = -1;
        } catch (Exception ignored) {}
        // -------------------------------------------------------------------------------------------------------------

        /* Blocks id:
        id: -3 = snow3.
        id: -2 = snow2.
        id: -1 = snow1.
        -----------------
        id: 1 = bonfire.
        */

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // Отрисовка поверхностей: -------------------------------------------------------------------------------------
        for (int block_y=0; block_y < tilemap_height; block_y++) {
            // Пройтись по блокам в ширину:
            for (int block_x=0; block_x < tilemap_width; block_x++) {
                int pos_x = block_unit * block_x; // Получение X позиции блока в пикселях.
                int pos_y = block_unit * block_y; // Получение Y позиции блока в пикселях.
                Rectangle block_rect = new Rectangle(pos_x, pos_y, block_unit, block_unit); // Rect блока.
                Rectangle camera_rect = new Rectangle(camera.position.x - camera.viewportWidth / 2,
                                                      camera.position.y - camera.viewportHeight / 2,
                                                         camera.viewportWidth, camera.viewportHeight); // Rect камеры.

                // Если камера видит блок:
                if (isCollision(block_rect, camera_rect)) {
                    if (tilemap[block_y][block_x] == -1) { batch.draw(snow1_env, pos_x, pos_y); } // Снеж.Поверх. 1.
                    if (tilemap[block_y][block_x] == -2) { batch.draw(snow2_env, pos_x, pos_y); } // Снеж.Поверх. 2.
                    if (tilemap[block_y][block_x] == -3) { batch.draw(snow3_env, pos_x, pos_y); } // Снеж.Поверх. 3.
                }}}
        // -------------------------------------------------------------------------------------------------------------

        // Отрисовка блоков: -------------------------------------------------------------------------------------------
        for (int block_y=0; block_y < tilemap_height; block_y++) {
            // Пройтись по блокам в ширину:
            for (int block_x=0; block_x < tilemap_width; block_x++) {
                int pos_x = block_unit * block_x; // Получение X позиции блока в пикселях.
                int pos_y = block_unit * block_y; // Получение Y позиции блока в пикселях.
                Rectangle block_rect = new Rectangle(pos_x, pos_y, block_unit, block_unit); // Rect блока.
                Rectangle camera_rect = new Rectangle(camera.position.x - camera.viewportWidth / 2,
                        camera.position.y - camera.viewportHeight / 2,
                        camera.viewportWidth, camera.viewportHeight); // Rect камеры.

                // Если камера видит блок:
                if (isCollision(block_rect, camera_rect)) {
                    if (tilemap[block_y][block_x] == 1) { batch.draw(snow1_env, pos_x, pos_y); batch.draw(bonfire_sur, pos_x, pos_y); }  // Костёр.
                }}}
        // -------------------------------------------------------------------------------------------------------------

        // Отрисовка сущностей(игроков/животных): ----------------------------------------------------------------------
        // TODO Здесь должен быть цикл for для обработки игроков в коллекции.
        // -------------------------------------------------------------------------------------------------------------
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Вызывается при изменении размеров окна.

        // Установка новых размеров камеры:
        camera.viewportWidth = Gdx.graphics.getWidth();
        camera.viewportHeight = Gdx.graphics.getHeight();
    }

    @Override
    public void pause() {
        // Вызывается при сворачивании окна.
    }

    @Override
    public void resume() {
        // Вызывается при разворачивании окна.
    }

    @Override
    public void hide() {
        // Вызывается при переключении на этот скрин.
    }
}
