package graphics.vertex;


import static org.lwjgl.opengl.GL45C.*;


public class VertexArray {

    public VertexBuffer vertexBuffer;
    public IndexBuffer indexBuffer;
    private int vArrayId;

    public VertexArray(VertexBuffer vertexBuffer, int... indices) {
        vArrayId = glGenVertexArrays();
        this.indexBuffer = new IndexBuffer(indices);
        setVertexBuffer(vertexBuffer);
    }

    public VertexArray(VertexBuffer vertexBuffer, IndexBuffer indexBuffer) {
        vArrayId = glGenVertexArrays();
        this.indexBuffer = indexBuffer;
        setVertexBuffer(vertexBuffer);
    }

    public void dispose() {
        glDeleteVertexArrays(vArrayId);
    }
    
    public void bind() {
        glBindVertexArray(vArrayId);
        vertexBuffer.bind();
        indexBuffer.bind();
    }
    
    public void unbind() {
        glBindVertexArray(0);
    }
    
    public final void setVertexBuffer(VertexBuffer vertexBuffer) {
        this.vertexBuffer = vertexBuffer;
        bind();

//        VertexLayout layout = vertexBuffer.getLayout();
//        assert layout != null : "Vertex Buffer has no layout!";

        /*
        for(VertexAttribute attr : layout) {
            glEnableVertexAttribArray(index);
            glVertexAttribPointer(index, 
                attr.componentCount,
                attr.openGLType,
                attr.normalized,
                layout.getStride(),
                offset);
            index++;
            offset += attr.size;
        }
        */

        int offset = 0;
        for (int index = 0; index < Vertex.ATTRIBUTES.length; index++) {
            VertexAttribute attr = Vertex.ATTRIBUTES[index];
            glEnableVertexAttribArray(index);
            glVertexAttribPointer(index, attr.componentCount, attr.openGLType, attr.normalized, Vertex.BYTES, offset);
            offset += attr.size;
        }
    }
    
    public final void setIndexBuffer(IndexBuffer indexBuffer) {
        this.indexBuffer = indexBuffer;
    }
}
