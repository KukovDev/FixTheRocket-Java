package game.main.Objects;

import com.badlogic.gdx.math.Rectangle;

abstract class GameObject {
    Rectangle bounds;

    GameObject(float x, float y, float width, float height) {
        bounds = new Rectangle(x, y, width, height);
    }
}
