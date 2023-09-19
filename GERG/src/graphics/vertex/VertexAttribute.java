package graphics.vertex;

import graphics.core.Uniform.DataType;

public class VertexAttribute {

    public int componentCount;
    public int openGLType;
    public boolean normalized;

    public int size;

    public VertexAttribute(DataType type) {
        this(type, false);
    }

    public VertexAttribute(DataType type, boolean normalized) {
        this.componentCount = type.getComponentCount();
        this.openGLType = type.toOpenGL();
        this.normalized = normalized;
        this.size = type.size();
    }
}
