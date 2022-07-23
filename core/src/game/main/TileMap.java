package game.main;

public class TileMap {
    public static int[][] Create(int height, int width, int id_all_blocks) {
        int[][] tilemap_list =  new int[height][width];  // Создание коллекции. (создание сетки блоков).

        // Проход по блокам по высоте:
        for (int block_y=0; block_y<=height-1; block_y++) {
            // Проход по блокам по ширине:
            for (int block_x=0; block_x<=width-1; block_x++) {
                // Добавление в блок, id блока:
                tilemap_list[block_y][block_x] = id_all_blocks;
            }
        }
        return tilemap_list;
    }
}
