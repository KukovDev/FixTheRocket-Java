package game.main.Scenes;

// Импорт библиотек: ---------------------------------------------------------------------------------------------------
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.main.Main;
import game.main.Objects.Player;

import java.util.ArrayList;
import java.util.List;
// ---------------------------------------------------------------------------------------------------------------------

public class GameScreen implements Screen {
    // Игровые переменные: ---------------------------------------------------------------------------------------------
    private OrthographicCamera camera;
    private float zoom = 0.15f;

    private List<Player> list_players;

    private int[][] tilemap;
    private int tilemap_height = 4;
    private int tilemap_width = 4;

    private int[] tilemap_mouse_pos;
    // -----------------------------------------------------------------------------------------------------------------

    // Текстуры-Спрайты: -------------------------------------------------------------------------------------------------------
    private SpriteBatch batch;

    private Sprite snow_surf_sprt;
    private Sprite null_surf_sprt;

    private Sprite player_sprt;
    // -----------------------------------------------------------------------------------------------------------------

    /* P.S: Этот блок кода не используется.
    public Texture texture_filter(Texture texture) {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return texture;}*/

    public Sprite load_sprite(String link) {
        return new Sprite(new Texture(link));
    }

    @Override
    public void show() {
        // Вызывается при переключении на этот скрин.

        // Создание игровых переменных: --------------------------------------------------------------------------------
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = zoom;

        list_players = new ArrayList<>();
        tilemap = game.main.TileMap.TileMap.Create(tilemap_height, tilemap_width, -1);
        tilemap_mouse_pos = new int[2];
        // -------------------------------------------------------------------------------------------------------------

        // Преобразование текстур в спрайты: ---------------------------------------------------------------------------
        snow_surf_sprt = load_sprite("sprites/blocks/surfs/snow_surf.png");
        null_surf_sprt = load_sprite("sprites/blocks/surfs/null_surf.png");
        player_sprt = load_sprite("sprites/player/running/1.png");
        // -------------------------------------------------------------------------------------------------------------

        // Создание объектов: ------------------------------------------------------------------------------------------
        list_players.add(new Player(player_txtr, 0f, 0f, player_txtr.getWidth(), player_txtr.getHeight()));
        // -------------------------------------------------------------------------------------------------------------
    }

    @Override
    public void render(float delta) {
        // Вызывается постоянно.

        // Очистка экрана, обновление камеры: --------------------------------------------------------------------------
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f); // > Очистка экрана.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                 // |

        camera.update(); // Обновление камеры.
        camera.zoom = zoom;

        Gdx.graphics.setTitle(Main.window_Title + " | FPS: " + Gdx.graphics.getFramesPerSecond());
        // -------------------------------------------------------------------------------------------------------------

        // Отрисовка: --------------------------------------------------------------------------------------------------
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int block_y=0; block_y<tilemap_height; block_y++) {
            for (int block_x=0; block_x<tilemap_width; block_x++) {
                if (tilemap[block_y][block_x] == -1){
                    batch.draw(snow_surf_txtr, block_x, block_y);}
                else {batch.draw(null_surf_txtr, block_x, block_y);}
            }}

        for (Player list_player : list_players) {list_player.draw(batch);}

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
        snow_surf_txtr.dispose();
    }
}
