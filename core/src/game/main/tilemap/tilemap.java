package game.main.tilemap;

public class tilemap {
    public static short[][] Create(short height, short width) {
        // Максимальные размеры карты 32,000 на 32,000 блоков. Это 1,024,000,000 Блоков:
        if (height > 32000) height = 32000; if (width > 32000) width = 32000;

        short[][] tilemap_list =  new short[height][width];  // Создание коллекции. (создание сетки блоков).

        // Проход по блокам по высоте:
        byte block_id = 0;

        for (short block_y=0; block_y<=height-1; block_y++) {
            // Проход по блокам по ширине:
            for (short block_x=0; block_x<=width-1; block_x++) {
                block_id -= 1;
                if (block_id < -3) block_id = -1;

                // Добавление в блок, id блока:
                tilemap_list[block_y][block_x] = 1;
            }
        }
        return tilemap_list;
    }
}
