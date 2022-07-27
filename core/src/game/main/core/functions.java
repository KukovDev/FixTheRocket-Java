package game.main.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import game.main.input.inputDesktop;

public class functions {
    // Загрузка спрайтов:
    public static Texture load_texture(String link) { return new Texture(link); }

    // Проверка на сталкивание двух Rect:
    public static boolean isCollision(Rectangle rect1, Rectangle rect2) { return Intersector.overlaps(rect1, rect2); }

    // Получение вращения колёсика мыши:
    public static float getScroll() { return inputDesktop.scroll; }

    // Получение позиции мыши по блокам на карте:
    public static void get_block_mouse_pos(
            int[] mouse_block_pos,     /* Позиция мыши в блоках */
            short tilemap_w,           /* Ширина карты */
            short tilemap_h,           /* Высота карты */
            int ut_size,               /* Размер блока 1 на 1 */
            OrthographicCamera camera, /* Камера */
            float zoom                 /* Зум камеры */ ) {

        int mouse_pos_x = Gdx.input.getX();                                                 // Позиция мыши по X.
        int mouse_pos_y = -(Gdx.input.getY() - Gdx.graphics.getHeight());                   // Позиция мыши по Y.

        int camera_zoom_x = (int)((camera.viewportWidth-(camera.viewportWidth*zoom))/2);    // 123 X.
        int camera_zoom_y = (int)((camera.viewportHeight-(camera.viewportHeight*zoom))/2);  // 123 Y.

        int camera_pos_x = (int)(camera.position.x - ((camera.viewportWidth / 2) * zoom));  // Позиция камеры по X.
        int camera_pos_y = (int)(camera.position.y - ((camera.viewportHeight / 2) * zoom)); // Позиция камеры по Y.

        camera_pos_x += (camera_pos_x - (camera_pos_x * zoom));
        camera_pos_y += (camera_pos_y - (camera_pos_y * zoom));

        int map_mouse_pos_x = mouse_pos_x + camera_pos_x;                                   // Позиция мыши на карте.
        int map_mouse_pos_y = mouse_pos_y + camera_pos_y;                                   // Позиция мыши на карте.

        mouse_block_pos[0] = map_mouse_pos_x / (int)(ut_size * zoom);          // Установка позиции мыши в блоках по X.
        mouse_block_pos[1] = map_mouse_pos_y / (int)(ut_size * zoom);          // Установка позиции мыши в блоках по Y.

        if (map_mouse_pos_x < 0) mouse_block_pos[0] = -1;                      // Установка мин. значений мыши по X.
        if (map_mouse_pos_y < 0) mouse_block_pos[1] = -1;                      // Установка мин. значений мыши по Y.
        if (mouse_block_pos[0] >= tilemap_w) mouse_block_pos[0] = tilemap_w;   // Установка макс. значений мыши по X.
        if (mouse_block_pos[1] >= tilemap_h) mouse_block_pos[1] = tilemap_h;   // Установка макс. значений мыши по Y.

        // System.out.println(camera_zoom_x + " | " + camera_zoom_y);           // Зум в пикселях.
        // System.out.println(mouse_block_pos[0] + " | " + mouse_block_pos[1]); // Позиция мыши в блоках.
        System.out.println(map_mouse_pos_x + " | " + map_mouse_pos_y);       // Позиция мыши в пикселях.
        // System.out.println(camera_pos_x + " | " + camera_pos_y);             // Позиция камеры.
        // System.out.println(mouse_pos_x + " | " + mouse_pos_y);               // Позиция мыши.
    }
}
