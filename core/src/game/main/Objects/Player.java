package game.main.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends GameObject {
    public byte anim_running_frames = 8; // Сколько спрайтов анимации бега.
    public float pos_x;
    public float pos_y;
    public float width;
    public float height;
    public Sprite[] amin_frames = new Sprite[anim_running_frames];

    public Player(float position_x, float position_y, float texture_width, float texture_height) {
        super(position_x, position_y, texture_width, texture_height);
        pos_x = position_x;
        pos_y = position_y;
        width = texture_width;
        height = texture_height;
    }
}
