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
    private OrthographicCamera camera;
    private float zoom = 0.5f;

    private List<Player> list_players;

    private int[][] tilemap;
    private final int tilemap_height = 256;
    private final int tilemap_width = 256;
    private final byte block_unit = 32;

    // private int[] tilemap_mouse_pos; TODO

    private float DeltaTime = 1;
    // -----------------------------------------------------------------------------------------------------------------

    // Текстуры: -------------------------------------------------------------------------------------------------------
    private SpriteBatch batch;

    private Texture snow1_env;
    private Texture snow2_env;
    private Texture snow3_env;

    private Texture player_txtr;
    // -----------------------------------------------------------------------------------------------------------------

    /* P.S: Этот блок кода не используется.
    public Texture texture_filter(Texture texture) {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return texture;}*/

    public Texture load_texture(String link) { return new Texture(link); }

    public boolean isCollision(Rectangle rect1, Rectangle rect2) { return Intersector.overlaps(rect1, rect2); }

    @Override
    public void show() {
        // Вызывается при переключении на этот скрин.

        // Создание игровых переменных: --------------------------------------------------------------------------------
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = zoom;

        list_players = new ArrayList<>();
        tilemap = game.main.TileMap.TileMap.Create(tilemap_height, tilemap_width);
        // tilemap_mouse_pos = new int[2]; TODO
        // -------------------------------------------------------------------------------------------------------------

        // Загрузка текстур: -------------------------------------------------------------------------------------------
        batch = new SpriteBatch();

        snow1_env = load_texture("sprites/blocks/environment/snow1.png");
        snow2_env = load_texture("sprites/blocks/environment/snow2.png");
        snow3_env = load_texture("sprites/blocks/environment/snow3.png");

        player_txtr = load_texture("sprites/player/running/1.png");
        // -------------------------------------------------------------------------------------------------------------

        // Создание объектов: ------------------------------------------------------------------------------------------
        list_players.add(new Player(0f, 0f, player_txtr.getWidth(), player_txtr.getHeight()));
        // -------------------------------------------------------------------------------------------------------------
    }

    @Override
    public void render(float delta) {
        // Вызывается постоянно.

        // Очистка экрана, обновление камеры: --------------------------------------------------------------------------
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f); // > Очистка экрана.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                 // |

        DeltaTime = (float) Main.window_FPS / Gdx.graphics.getFramesPerSecond();
        if (DeltaTime > 340282356779733661637539395458142568447.9f) DeltaTime = 1;
        System.out.println(DeltaTime);

        camera.update(); // Обновление камеры.
        camera.zoom = zoom;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.y += 5 * DeltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.position.x -= 5 * DeltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.y -= 5 * DeltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.position.x += 5 * DeltaTime;

        Gdx.graphics.setTitle(Main.window_Title + " | FPS: " + Gdx.graphics.getFramesPerSecond());
        // -------------------------------------------------------------------------------------------------------------

        // Отрисовка: --------------------------------------------------------------------------------------------------
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int block_y=0; block_y < tilemap_height; block_y++) {
            for (int block_x=0; block_x < tilemap_width; block_x++) {
                int pos_x = block_unit * block_x;
                int pos_y = block_unit * block_y;
                Rectangle block_rect = new Rectangle(pos_x, pos_y, block_unit, block_unit);
                Rectangle camera_rect = new Rectangle(camera.position.x - camera.viewportWidth / 2,
                                                      camera.position.y - camera.viewportHeight / 2,
                                                         camera.viewportWidth, camera.viewportHeight);

                if (isCollision(block_rect, camera_rect)) {
                    if (tilemap[block_y][block_x] == -1) {
                        batch.draw(snow1_env, pos_x, pos_y);
                    }
                    if (tilemap[block_y][block_x] == -2) {
                        batch.draw(snow2_env, pos_x, pos_y);
                    }
                    if (tilemap[block_y][block_x] == -3) {
                        batch.draw(snow3_env, pos_x, pos_y);
                    }
                }
            }
        }

        batch.end();
        // -------------------------------------------------------------------------------------------------------------
    }

    @Override
    public void resize(int width, int height) {
        // Вызывается при изменении размеров окна.
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

    @Override
    public void dispose() {
        // Вызывается при закрытии окна.
        batch.dispose();
        snow1_env.dispose();
        snow2_env.dispose();
        snow3_env.dispose();
        player_txtr.dispose();
    }
}
