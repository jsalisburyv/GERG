package graphics.vertex;

import graphics.enums.*;
import graphics.render.*;

import static org.lwjgl.opengl.GL46C.*;

public class VertexBuffer implements Drawable {
    private int vBufferId;
    private int count; // number of floats
    private PrimitiveType type;
    private BufferUsage usage;
    private VertexLayout layout; // TODO: remove layout?


    //<editor-fold desc="CONSTRUCTORS AND 'DESTRUCTORS'">
    public VertexBuffer() {
        this(PrimitiveType.POINTS, BufferUsage.STREAM, 0); // TODO: convert to config?
    }

    public VertexBuffer(int count, PrimitiveType type, BufferUsage usage) {
        this(type, usage, count);
        glBindBuffer(GL_ARRAY_BUFFER, vBufferId);
        glBufferData(GL_ARRAY_BUFFER, count * Float.BYTES, usage.toGLEnum());
    }

    public VertexBuffer(PrimitiveType type, BufferUsage usage, Vertex... vertices) {
        this(type, usage, vertices.length);
        glBindBuffer(GL_ARRAY_BUFFER, vBufferId);
        glBufferData(GL_ARRAY_BUFFER, Vertex.toFloatArray(vertices), usage.toGLEnum());
    }

    public VertexBuffer(PrimitiveType type, BufferUsage usage, int count) {
        this.vBufferId = glGenBuffers();
        this.type = type;
        this.usage = usage;
        this.count = count;
    }

    public void dispose() {
        glDeleteBuffers(vBufferId);
    }
    //</editor-fold>

    //<editor-fold desc="MEMBER FUNCTIONS">
    public void bind(){
        glBindBuffer(GL_ARRAY_BUFFER, vBufferId);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public boolean setData(float[] vertices) {
        return setData(vertices, 0);
    }

    // offset in bufferdata, not vertices
    public boolean setData(float[] vertices, int offset) {
        if(vBufferId == 0) return false;
        if(vertices == null || vertices.length == 0) return false;
        if(offset != 0 && (offset + vertices.length > count)) return false;
        bind();
        if(vertices.length > count) {
            glBufferData(GL_ARRAY_BUFFER, vertices.length * Vertex.BYTES, usage.toGLEnum());
        }
        glBufferSubData(GL_ARRAY_BUFFER, offset, vertices);
        return true;
    }

    public void setData(Vertex... vertices) {
        bind();
        glBufferData(GL_ARRAY_BUFFER, Vertex.toFloatArray(vertices), usage.toGLEnum());
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        target.drawVertexBuffer(this, 0, count, states);
    }
    //</editor-fold>

    //<editor-fold desc="GETTERS AND SETTERS">
    public int getHandle() {
        return this.vBufferId;
    }
    public PrimitiveType getPrimitiveType() {
        return this.type;
    }

    public BufferUsage getUsage() {
        return this.usage;
    }
    public int getVertexCount() {
        return this.count;
    }

    public VertexLayout getLayout() {
        return layout;
    }

    public void setPrimitiveType(PrimitiveType type) {
        this.type = type;
    }

    public void setUsage(BufferUsage usage) {
        this.usage = usage;
    }

    public void setLayout(VertexLayout layout) {
        this.layout = layout;
    }
    //</editor-fold>
}