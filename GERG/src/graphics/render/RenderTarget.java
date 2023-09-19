package graphics.render;


import graphics.vertex.Color;
import graphics.enums.PrimitiveType;
import graphics.vertex.Vertex;
import graphics.vertex.VertexArray;
import graphics.vertex.VertexBuffer;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static java.sql.Types.NULL;
import static org.lwjgl.opengl.GL45.*;

public class RenderTarget {

    public RenderTarget() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void clear() {
        clear(Color.BLACK);
    }

    public void clear(Color color) {
        glClearColor(color.x/255f, color.y/255f, color.z/255f, color.w/255f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void clear(float r, float g, float b, float a, boolean normalized) {
        if(normalized) {
            glClearColor(r, g, b, a);
        } else {
            glClearColor(r/255f, g/255f, b/255f, a/255f);
        }
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void draw(Drawable drawable) { draw(drawable, RenderStates.DEFAULT);}

    public void draw(Drawable drawable, RenderStates states) {
        drawable.draw(this, states);
    }

    public void draw(Vertex[] vertices, PrimitiveType type) {
        drawVertices(vertices, type, RenderStates.DEFAULT);
    }

    public void drawVertices(Vertex[] vertices, PrimitiveType type, RenderStates states) {
        if(vertices == null || vertices.length == 0) return;
        setupDraw(states);
        //boolean enableTexCoordsArray = states.texture != null || states.shader != null;
        FloatBuffer data = BufferUtils.createFloatBuffer(vertices.length * Vertex.FLOATS);
        data.put(Vertex.toFloatArray(vertices));
        data.rewind();
        //System.out.println(data);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        data.position(0);
        glVertexPointer(2, GL_FLOAT, Vertex.BYTES, data);
        data.position(8);
        glColorPointer(4, GL_FLOAT, Vertex.BYTES, data);
        data.position(24);
        glTexCoordPointer(2, GL_FLOAT, Vertex.BYTES, data);

        glDrawArrays(type.toGLEnum(), 0, vertices.length);

        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_COLOR_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);

        cleanupDraw(states);
    }

    public void drawVertexBuffer(VertexBuffer vertexBuffer) {
        drawVertexBuffer(vertexBuffer, RenderStates.DEFAULT);
    }

    public void drawVertexBuffer(VertexBuffer vertexBuffer, RenderStates states) {
        drawVertexBuffer(vertexBuffer, 0, vertexBuffer.getVertexCount(), states);
    }

    public void drawVertexBuffer(VertexBuffer vertexBuffer, int firstVertex, int vertexCount) {
        drawVertexBuffer(vertexBuffer, firstVertex,vertexCount, RenderStates.DEFAULT);
    }

    public void drawVertexBuffer(VertexBuffer vertexBuffer, int firstVertex, int vertexCount, RenderStates states) {
        if(firstVertex > vertexBuffer.getVertexCount()) return;
        vertexCount = Math.min(vertexCount, vertexBuffer.getVertexCount() - firstVertex);
        if(vertexCount == 0 || vertexBuffer.getHandle() == 0) return;

        setupDraw(states);
        vertexBuffer.bind();

        /* Always enable texture coordinates
        if (!m_cache.enable || !m_cache.texCoordsArrayEnabled)*/

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        glVertexPointer(2, GL_FLOAT, Vertex.BYTES, 0);
        glColorPointer(4, GL_FLOAT, Vertex.BYTES, 8);
        glTexCoordPointer(2, GL_FLOAT, Vertex.BYTES, 24);

        glDrawArrays(vertexBuffer.getPrimitiveType().toGLEnum(), firstVertex, vertexCount);

        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_COLOR_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);

        vertexBuffer.unbind();
        cleanupDraw(states);
    }

    public void drawSquare() {
        
    }

    public void draw(VertexArray data, RenderStates states) {
        setupDraw(states);
        data.bind();
        glDrawElements(data.vertexBuffer.getPrimitiveType().toGLEnum(), data.indexBuffer.getCount(), GL_UNSIGNED_INT, NULL);
        cleanupDraw(states);
    }

    private void setupDraw(RenderStates states) {
        if(states.texture != null) {
            states.texture.bind();
        }
        if(states.shader != null) {
            states.shader.bind();
        }
    }

    private void cleanupDraw(RenderStates states) {
        if(states.texture != null) {
            states.texture.unbind();
        }
        if(states.shader != null) {
            states.shader.unbind();
        }
    }
}
