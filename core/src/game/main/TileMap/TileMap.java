package game.main.TileMap;

public class TileMap {
    public static int[][] Create(int height, int width) {
        int[][] tilemap_list =  new int[height][width];  // Создание коллекции. (создание сетки блоков).

        /* Blocks id:
        id: -3 = snow3
        id: -2 = snow2
        id: -1 = snow1
        id: 0 = null
        */
        // Проход по блокам по высоте:
        byte block_id = 0;

        for (int block_y=0; block_y<=height-1; block_y++) {
            // Проход по блокам по ширине:
            for (int block_x=0; block_x<=width-1; block_x++) {
                block_id -= 1;
                if (block_id < -3) block_id = -1;

                // Добавление в блок, id блока:
                tilemap_list[block_y][block_x] = block_id;
            }
        }
        return tilemap_list;
    }
}
