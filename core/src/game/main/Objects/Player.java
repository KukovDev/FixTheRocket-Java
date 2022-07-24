package game.main.Objects;

import com.badlogic.gdx.graphics.Texture;

public class Player extends GameObject {
    public byte AnimRunningFrames = 8; // Сколько спрайтов анимации бега.
    public float pos_x;
    public float pos_y;
    public float width;
    public float height;

    public Player(Texture texture, float position_x, float position_y, float texture_width, float texture_height) {
        super(texture, position_x, position_y, texture_width, texture_height);
        pos_x = position_x;
        pos_y = position_y;
        width = texture_width;
        height = texture_height;
    }
}
