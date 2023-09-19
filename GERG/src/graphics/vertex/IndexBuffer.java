package graphics.vertex;

import static org.lwjgl.opengl.GL45C.*;


public class IndexBuffer {
    
    private int iBufferId;
    private final int count;

    public IndexBuffer(int... indices) {
        this.iBufferId = glGenBuffers();
        this.count = indices.length;
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iBufferId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    }

    public void dispose() {
        glDeleteBuffers(iBufferId);
    }

    public void bind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iBufferId);
    }

    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getCount() {
        return count;
    }
}
