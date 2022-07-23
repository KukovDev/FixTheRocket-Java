package game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Load {
    public static Texture snow_land_block = new Texture(Gdx.files.internal("sprites/blocks/lands/snow_land.png"));
    public static Texture bad_land_block = new Texture(Gdx.files.internal("sprites/blocks/lands/bad_land.png"));

    public static void dispose() {
        snow_land_block.dispose();
        bad_land_block.dispose();
    }
}
