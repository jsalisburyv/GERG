package graphics.textures;

import core.resources.ResourceManager;
import graphics.enums.BufferUsage;
import graphics.enums.PrimitiveType;
import graphics.render.RenderStates;
import graphics.render.RenderTarget;
import graphics.vertex.Vertex;
import graphics.vertex.VertexArray;
import graphics.vertex.VertexBuffer;
import org.joml.Vector2f;

import java.util.stream.IntStream;

/**
 * This class contains a font texture for drawing text.
 *
 */
public class Font {

    private Texture2D texture;
    private int size, rows, cols;

    private VertexBuffer vb;
    private VertexArray va;

    public Font(String filename, int size) {
        this.texture = ResourceManager.getTexture(filename);
        this.size = size;
        this.cols = texture.getWidth() / size;
        this.rows = texture.getHeight() / size;

        int vertexCount = 4;
        vb = new VertexBuffer(PrimitiveType.QUADS, BufferUsage.DYNAMIC, vertexCount);
        this.va = new VertexArray(vb, IntStream.range(0, vertexCount).toArray());
    }

    public void draw(String text, RenderTarget renderer, RenderStates states) {
        Vector2f pos = new Vector2f();
        for(char c: text.toCharArray()) {
            int index = c - ' ';
            int i = index / cols, j = index % cols;
            drawChar(i, j, renderer, states, pos);
        }
    }

    private void drawChar(int i, int j, RenderTarget renderer, RenderStates states, Vector2f pos) {
        Vector2f texCoord1 = new Vector2f();
        texCoord1.x = (float) (j * size) / texture.getWidth();
        texCoord1.y = (float) (i * size) / texture.getHeight();
        Vector2f texCoord2 = new Vector2f();
        texCoord2.x = (float) ((j + 1) * size) / texture.getWidth();
        texCoord2.y = (float) ((i + 1) * size) / texture.getHeight();
        Vertex[] vertices = new Vertex[] {
                new Vertex(new Vector2f(0,0).add(pos), new Vector2f(texCoord1.x, texCoord1.y)),
                new Vertex(new Vector2f(1,0).add(pos), new Vector2f(texCoord2.x, texCoord1.y)),
                new Vertex(new Vector2f(1,1).add(pos), new Vector2f(texCoord2.x, texCoord2.y)),
                new Vertex(new Vector2f(0,1).add(pos), new Vector2f(texCoord1.x, texCoord2.y)),
        };
        vb.setData(vertices);
        states.texture = texture;
        renderer.draw(va, states);
    }
}