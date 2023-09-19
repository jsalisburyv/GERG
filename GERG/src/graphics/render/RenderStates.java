package graphics.render;

import core.resources.ResourceManager;
import graphics.core.Shader;
import graphics.textures.Texture;
import org.joml.Matrix4f;

public class RenderStates {
    public static final RenderStates DEFAULT = new RenderStates();

    // blendMode
    // transform
    public Texture texture;
    public Shader shader;
    public float scale;

    public Matrix4f projection;

    public RenderStates() {
        this.texture = null;
        this.shader = null;
        this.projection = new Matrix4f();
    }

    public RenderStates(String shaderFilename){
        this.texture = null;
        this.shader = ResourceManager.getShader(shaderFilename);
        this.projection = new Matrix4f();
    }

    public void setTransform(Matrix4f transform) {
        if(shader == null) return;
        shader.bind();
        shader.setMatrix4f("model", transform);
    }

    public void setProjection(String name, Matrix4f projection) {
        if(shader == null) return;
        shader.bind();
        shader.setMatrix4f(name, projection);
    }

    public void dispose() {
        if(texture != null) texture.dispose();
        if(shader != null) shader.dispose();
    }
}
