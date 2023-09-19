package graphics.vertex;

import org.joml.Vector2f;

import static graphics.core.Uniform.DataType.*;

public class Vertex {

    /* ATTRIBUTES */
    public static final VertexAttribute[] ATTRIBUTES = new VertexAttribute[]{
            new VertexAttribute(FLOAT2), new VertexAttribute(FLOAT4), new VertexAttribute(FLOAT2)};
    
    /* CONSTANTS */
    public static final int FLOATS = 8; // number of float variables
    public static final int BYTES = FLOATS * Float.BYTES; // variables * floatSize

    public Vector2f position;
    public Color color;
    public Vector2f texCoords;

    public Vertex() {
        this.position = new Vector2f();
        this.color = new Color(255, 255, 255).normalize();
        this.texCoords = new Vector2f();
    }

    public Vertex(final Vector2f position){
        this.position = position;
        this.color = new Color(255, 255, 255).normalize();
        this.texCoords = new Vector2f();
    }

    public Vertex(final Vector2f position, final Color color){
        this.position = position;
        this.color = color.normalize();
        this.texCoords = new Vector2f();
    }

    public Vertex(final Vector2f position, final Vector2f texCoords) {
        this.position = position;
        this.color = new Color(255, 255, 255).normalize();
        this.texCoords = texCoords;
    }

    public Vertex(final Vector2f position, final Color color, final Vector2f texCoords) {
        this.position = position;
        this.color = color.normalize();
        this.texCoords = texCoords;
    }

    public Vertex(float x, float y, float s, float t) {
        this.position = new Vector2f(x,y);
        this.color = new Color(255, 255, 255).normalize();
        this.texCoords = new Vector2f(s, t);
    }

    public Vertex(float x, float y, Color color, float s, float t) {
        this.position = new Vector2f(x,y);
        this.color = color.normalize();
        this.texCoords = new Vector2f(s, t);
    }

    public Vertex(float x, float y, Color color) {
        this.position = new Vector2f(x,y);
        this.color = color.normalize();
        this.texCoords = new Vector2f();
    }

    public static float[] toFloatArray(Vertex[] vertices) {
        float[] array = new float[vertices.length * FLOATS];
        for (int vertex = 0; vertex < vertices.length; vertex++) {
            array[vertex* FLOATS] = vertices[vertex].position.x;
            array[vertex* FLOATS + 1] = vertices[vertex].position.y;
            array[vertex* FLOATS + 2] = vertices[vertex].color.x;
            array[vertex* FLOATS + 3] = vertices[vertex].color.y;
            array[vertex* FLOATS + 4] = vertices[vertex].color.z;
            array[vertex* FLOATS + 5] = vertices[vertex].color.w;
            array[vertex* FLOATS + 6] = vertices[vertex].texCoords.x;
            array[vertex* FLOATS + 7] = vertices[vertex].texCoords.y;
        }
        return array;
    }
}
