package game.main.TileMap;

public class TileMap {
    public static int[][] Create(int height, int width) {
        int[][] tilemap_list =  new int[height][width];  // Создание коллекции. (создание сетки блоков).
        // Проход по блокам по высоте:
        byte block_id = 0;

        for (int block_y=0; block_y<=height-1; block_y++) {
            // Проход по блокам по ширине:
            for (int block_x=0; block_x<=width-1; block_x++) {
                block_id -= 1;
                if (block_id < -3) block_id = -1;

                // Добавление в блок, id блока:
                tilemap_list[block_y][block_x] = 1;
            }
        }
        return tilemap_list;
    }
}
