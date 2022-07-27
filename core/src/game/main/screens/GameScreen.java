package game.main.screens;

// Импорт библиотек: ---------------------------------------------------------------------------------------------------
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import game.main.Main;
import game.main.core.functions;
import game.main.input.inputDesktop;
// ---------------------------------------------------------------------------------------------------------------------

public class GameScreen implements Screen {
    // Игровые переменные: ---------------------------------------------------------------------------------------------

    // Камера:
    OrthographicCamera camera;                  // 2D камера.
    public float zoom = 1f;                     // Зум камеры.

    public float mouseScroll;                   // Вращение колёсика мыши.

    public inputDesktop inputPC;     // Штука событий.

    // Карта:
    short[][] tilemap;                          // Карта.
    final short tilemap_height = 8;             // Высота карты.
    final short tilemap_width = 8;              // Ширина карты.
    public static final short unit_size = 64;  // 1 unit = 64 px.
    int[] mouse_block_pos;                      // Позиция мыши в блоках.
    int old_id_block_env;                       // Айди поверхности на которую поставили блок.
    // -----------------------------------------------------------------------------------------------------------------

    // Текстуры: -------------------------------------------------------------------------------------------------------
    SpriteBatch batch;   // Партия спрайтов на отрисовку.

    Texture snow1_env;   // Снежная поверхность 1.
    Texture snow2_env;   // Снежная поверхность 2.
    Texture snow3_env;   // Снежная поверхность 3.

    Texture bonfire_sur; // Костёр.

    Texture player_txtr; // Текстура игрока.
    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void show() {
        // Вызывается при переключении на этот скрин.

        // Создание игровых переменных: --------------------------------------------------------------------------------
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Камера.
        camera.position.x = camera.viewportWidth / 2;
        camera.position.y = camera.viewportHeight / 2;

        tilemap = game.main.tilemap.tilemap.Create(tilemap_height, tilemap_width); // Создание карты.
        mouse_block_pos = new int[2];                            // Позиция мыши.
        mouseScroll = 0f;                                        // Скролл мыши.

        inputPC = new inputDesktop();                            // Доп.источники ввода.
        Gdx.input.setInputProcessor(inputPC);                    // Доп.источники ввода.
        // -------------------------------------------------------------------------------------------------------------

        // Загрузка текстур: -------------------------------------------------------------------------------------------
        batch = new SpriteBatch();

        snow1_env = functions.load_texture("sprites/blocks/environment/snow1.png");
        snow2_env = functions.load_texture("sprites/blocks/environment/snow2.png");
        snow3_env = functions.load_texture("sprites/blocks/environment/snow3.png");
        bonfire_sur = functions.load_texture("sprites/blocks/survival/bonfire/bonfire1.png");
        player_txtr = functions.load_texture("sprites/player/running/1.png");
        // -------------------------------------------------------------------------------------------------------------

        // Создание объектов: ------------------------------------------------------------------------------------------
        // [...]
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

        Logic(); // Вызов логики.
        Draw();  // Вызов отрисовки.
    }

    @Override
    public void resize(int width, int height) {
        // Вызывается при изменении размеров окна.

        // Установка новых размеров камеры:
        camera.viewportWidth = Gdx.graphics.getWidth();
        camera.viewportHeight = Gdx.graphics.getHeight();
    }

    // Не используемое: ------------------------------------------------------------------------------------------------
    @Override
    public void pause() { /* Вызывается при сворачивании окна. */ }

    @Override
    public void resume() { /* Вызывается при разворачивании окна. */ }

    @Override
    public void hide() { /* Вызывается при переключении на этот скрин. */ }
    // -----------------------------------------------------------------------------------------------------------------

    // Логика:
    public void Logic() {
        // Время между кадрами: ----------------------------------------------------------------------------------------
        float DeltaTime = (float) Main.window_FPS / Gdx.graphics.getFramesPerSecond(); // Получение DeltaTime.
        if (DeltaTime > 340282356779733661637539395458142568447.9f) DeltaTime = 1; // Если DeltaTime больше максимума.
        // -------------------------------------------------------------------------------------------------------------

        // Зум камеры: -------------------------------------------------------------------------------------------------
        mouseScroll = functions.getScroll();    // Получение скроллинга.
        zoom += mouseScroll / 10;     // Установка зума камеры.
        if (zoom > 2.5f) zoom = 2.5f; if (zoom < 0.5f) zoom = 0.5f; // Установка ограничений зумминга.
        camera.zoom = zoom;           // Применение зума.
        inputDesktop.scroll = 0f; // Сброс скроллинга.
        // -------------------------------------------------------------------------------------------------------------

        // Заголовок окна:
        Gdx.graphics.setTitle(Main.window_Title + " | FPS: " + Gdx.graphics.getFramesPerSecond() + " | zoom: " + zoom);

        // Перемещение камеры: -----------------------------------------------------------------------------------------
        if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.y += 5 * DeltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.position.x -= 5 * DeltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.y -= 5 * DeltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.position.x += 5 * DeltaTime;
        // -------------------------------------------------------------------------------------------------------------

        functions.get_block_mouse_pos(mouse_block_pos, tilemap_width, tilemap_height, unit_size, camera, zoom);
        // System.out.println(mouse_block_pos[0] + " | " + mouse_block_pos[1]);

        // Установка блоков: -------------------------------------------------------------------------------------------
        try {
            // Установка:
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                old_id_block_env = tilemap[mouse_block_pos[1]][mouse_block_pos[0]];
                tilemap[mouse_block_pos[1]][mouse_block_pos[0]] = 1;
            }

            // Удаление:
            if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                tilemap[mouse_block_pos[1]][mouse_block_pos[0]] = -1;
            }

        } catch (Exception ignored) {}
        // -------------------------------------------------------------------------------------------------------------
    }

    // Отрисовка:
    public void Draw() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);  // Очистка экрана.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                  // Очистка экрана.
        camera.update();                                           // Обновление камеры.
        batch.setProjectionMatrix(camera.combined);                // Что-то непонятное но важное для камеры.

        /* Blocks id:
        id: -3 = snow3.
        id: -2 = snow2.
        id: -1 = snow1.
        -----------------
        id: 1 = bonfire.
        */

        batch.begin(); // Начало отрисовки.

        // Отрисовка поверхностей: -------------------------------------------------------------------------------------
        for (int block_y=0; block_y < tilemap_height; block_y++) {
            // Пройтись по блокам в ширину:
            for (int block_x=0; block_x < tilemap_width; block_x++) {
                int pos_x = unit_size * block_x; // Получение X позиции блока в пикселях.
                int pos_y = unit_size * block_y; // Получение Y позиции блока в пикселях.
                Rectangle block_rect = new Rectangle(pos_x, pos_y, unit_size, unit_size); // Rect блока.
                Rectangle camera_rect = new Rectangle(camera.position.x - ((camera.viewportWidth * zoom) / 2),
                        camera.position.y - ((camera.viewportHeight * zoom) / 2),
                        camera.viewportWidth * zoom, camera.viewportHeight * zoom); // Rect камеры.

                // Если камера видит блок:
                if (functions.isCollision(block_rect, camera_rect)) {
                    // Снеж.Поверх. 1:
                    if (tilemap[block_y][block_x] == -1) { batch.draw(snow1_env, pos_x, pos_y, 64, 64); }

                    // Снеж.Поверх. 2:
                    if (tilemap[block_y][block_x] == -2) { batch.draw(snow2_env, pos_x, pos_y, 64, 64); }

                    // Снеж.Поверх. 3:
                    if (tilemap[block_y][block_x] == -3) { batch.draw(snow3_env, pos_x, pos_y, 64, 64); }
                }
            }
        }
        // -------------------------------------------------------------------------------------------------------------

        // Отрисовка блоков: -------------------------------------------------------------------------------------------
        for (int block_y=0; block_y < tilemap_height; block_y++) {
            // Пройтись по блокам в ширину:
            for (int block_x=0; block_x < tilemap_width; block_x++) {
                int pos_x = unit_size * block_x; // Получение X позиции блока в пикселях.
                int pos_y = unit_size * block_y; // Получение Y позиции блока в пикселях.
                Rectangle block_rect = new Rectangle(pos_x, pos_y, unit_size, unit_size); // Rect блока.
                Rectangle camera_rect = new Rectangle(camera.position.x - ((camera.viewportWidth * zoom) / 2),
                        camera.position.y - ((camera.viewportHeight * zoom) / 2),
                        camera.viewportWidth * zoom, camera.viewportHeight * zoom); // Rect камеры.

                // Если камера видит блок:
                if (functions.isCollision(block_rect, camera_rect)) {
                    // Костёр:
                    if (tilemap[block_y][block_x] == 1) {
                        batch.draw(snow1_env, pos_x, pos_y, 64, 64);
                        batch.draw(bonfire_sur, pos_x, pos_y, 64, 64);
                    }
                }
            }
        }
        // -------------------------------------------------------------------------------------------------------------

        // Отрисовка сущностей(игроков/животных): ----------------------------------------------------------------------
        // [...]
        // -------------------------------------------------------------------------------------------------------------

        batch.end(); // Конец отрисовки.
    }
}
