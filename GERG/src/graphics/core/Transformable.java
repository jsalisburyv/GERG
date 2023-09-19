package graphics.core;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public abstract class Transformable {

    protected Vector2f origin;
    protected Vector2f position;
    protected float angle;
    protected Vector2f scale;
    protected Matrix4f transform;
    protected boolean transformNeedUpdate;

    public Transformable() {
        origin = new Vector2f();
        position = new Vector2f();
        angle = 0;
        scale = new Vector2f();
        transform = new Matrix4f();
        transformNeedUpdate = true;
    }

    public Vector2f getPosition(){
        return position;
    }

    public void setPosition(float x, float y){
        this.position.set(x, y);
        transformNeedUpdate = true;
    }

    public void setPosition(Vector2f position){
        this.position.set(position);
        transformNeedUpdate = true;
    }

    public void translate(float offsetX, float offsetY){
        this.position.add(offsetX, offsetY);
        transformNeedUpdate = true;
    }

    public void translate(Vector2f offset){
        this.position.add(offset);
        transformNeedUpdate = true;
    }

    public float getRotation(){
        return angle;
    }
    public void setRotation(float angle){
        this.angle = angle;
        transformNeedUpdate = true;
    }
    public void rotate(float angle){
        this.angle += angle;
        transformNeedUpdate = true;
    }

    public Vector2f getScale(){
        return scale;
    }

    public void setScale(float factor){
        this.scale.set(factor, factor);
        transformNeedUpdate = true;
    }

    public void setScale(float factorX, float factorY){
        this.scale.set(factorX, factorY);
        transformNeedUpdate = true;
    }
    public void setScale(Vector2f factor) {
        this.scale.set(factor);
        transformNeedUpdate = true;
    }

    public void scale(float factor) {
        this.scale.add(new Vector2f(factor));
        transformNeedUpdate = true;
    }

    public void scale(float factorX, float factorY){
        this.scale.mul(factorX, factorY);
        transformNeedUpdate = true;
    }
    public void scale(Vector2f factor) {
        this.scale.mul(factor);
        transformNeedUpdate = true;
    }

    public void setTransform(Matrix4f transform) {
        transform = new Matrix4f(transform);
        transformNeedUpdate = false;
    }

    public Matrix4f getTransform() {
        if(transformNeedUpdate) {
            transform = new Matrix4f()
                    .translate(position.x, position.y, 0)
                    .rotateZ(angle)
                    .scale(scale.x, scale.y, 0);
            transformNeedUpdate = false;
        }
        return transform;
    }
}
