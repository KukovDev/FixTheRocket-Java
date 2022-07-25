package game.main.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import game.main.screens.GameScreen;

public class Blocks {
    public short pos_block_x;
    public short pos_block_y;
    public short unit;
    public Rectangle rect;

    public byte anim_frames;
    public float anim_step;
    public  float anim_speed;

    public Texture[] block_texture;

    public Blocks() {
        rect = new Rectangle(pos_block_x * GameScreen.bk_ut_sz,
                pos_block_y * GameScreen.bk_ut_sz,
                GameScreen.bk_ut_sz * unit,
                GameScreen.bk_ut_sz * unit);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(block_texture[(int) anim_step],
                pos_block_x * GameScreen.bk_ut_sz,
                pos_block_y * GameScreen.bk_ut_sz,
                GameScreen.bk_ut_sz * unit,
                GameScreen.bk_ut_sz * unit);
    }

    public void AnimUpdate() {
        anim_step += anim_speed;
        if (anim_step > anim_frames-1) anim_step = anim_frames-1;
    }
}
