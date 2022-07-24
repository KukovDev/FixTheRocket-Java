package game.main.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

abstract class GameObject {
    Rectangle bounds;
    Sprite object;

    GameObject(Sprite sprite, float x, float y, float width, float height) {
        bounds = new Rectangle(x, y, width, height);
        object = new Sprite(sprite);
    }

    public void draw(SpriteBatch batch) {
        object.setBounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        object.draw(batch);
    }
}
