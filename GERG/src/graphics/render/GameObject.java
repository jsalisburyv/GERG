package graphics.render;

import graphics.core.Transformable;
import org.joml.Vector2f;

public abstract class GameObject extends Transformable implements Drawable {


    protected float width, height;

    public GameObject(Vector2f position, float width, float height) {
        this.position = new Vector2f(position);
        this.width = width;
        this.height = height;
    }

    public abstract void update(float deltaTime);

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
