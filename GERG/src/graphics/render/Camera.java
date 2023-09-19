package graphics.render;


import core.Window;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

public class Camera {

    private Vector2f position;
    private float zoom;
    private Matrix4f projectionMatrix;
    private boolean matrixNeedsUpdate;

    public Camera() {
        this.position = new Vector2f();
        this.zoom = 1f;
        this.projectionMatrix = new Matrix4f();
        this.matrixNeedsUpdate = true;

    }

    public Camera(float zoom) {
        this.position = new Vector2f();
        this.zoom = zoom;
        this.projectionMatrix = new Matrix4f();
        this.matrixNeedsUpdate = true;
    }

    public Camera(Vector2f position, float zoom) {
        this.position = new Vector2f(position);
        this.zoom = zoom;
        this.projectionMatrix = new Matrix4f();
        this.matrixNeedsUpdate = true;
    }

    public void zoom(float factor) {
        this.matrixNeedsUpdate = true;
        this.zoom += factor * 0.1;
        if(zoom < 1) zoom = 1;
    }

    public void translate(Vector2f offset) {
        this.matrixNeedsUpdate = true;
        this.position.add(new Vector2f(offset).mul(zoom));
    }

    public void reset() {
        this.matrixNeedsUpdate = true;
        this.position = new Vector2f();
        this.zoom = 1f;
    }

    public float getZoom() {
        return this.zoom;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Matrix4f getProjectionMatrix() {
        if(matrixNeedsUpdate){
            Vector2i windowSize = Window.getSize();
            Matrix4f orthoMatrix = new Matrix4f().setOrtho(0, windowSize.x, windowSize.y, 0, -1, 1);
            projectionMatrix = orthoMatrix.translate(position.x, position.y, 0).scale(zoom);
        }
        return projectionMatrix;
    }
}
