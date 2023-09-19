package graphics.tilemap;

import graphics.core.Transformable;
import graphics.enums.BufferUsage;
import graphics.enums.PrimitiveType;
import graphics.render.Drawable;
import graphics.render.RenderStates;
import graphics.render.RenderTarget;
import graphics.vertex.Vertex;
import graphics.vertex.VertexArray;
import graphics.vertex.VertexBuffer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;

import java.util.stream.IntStream;

public class Tilemap extends Transformable implements Drawable {

    private static final int[] DX = {0, 1, 1, 0};
    private static final int[] DY = {0, 0, 1, 1};

    private int width, height;
    private Tile[][] tiles;
    private Tileset tileset;
    private VertexBuffer buffer;
    private VertexArray tilemap;

    public Tilemap(Tile[][] tiles, Tileset tileset) {
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.tiles = tiles;
        this.tileset = tileset;

        buffer = new VertexBuffer(PrimitiveType.QUADS, BufferUsage.DYNAMIC, width * height * 4);
        this.tilemap = new VertexArray(buffer, IntStream.range(0, width * height * 4).toArray());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }
        return tiles[x][y];
    }

    public void setTile(int x, int y, Tile tile) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }
        tiles[x][y] = tile;
    }

    public Tileset getTileset() {
        return tileset;
    }

    public void setTileset(Tileset tileset) {
        this.tileset = tileset;
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        updateBuffer();
        states.texture = tileset;
        states.setTransform(getTransform());
        target.draw(tilemap, states);
    }

    private void updateBuffer() {
        Vector2i texSize = tileset.getTexSize();
        float tileWidth = texSize.x / (float) (texSize.x * width);
        float tileHeight = texSize.y / (float) (texSize.y * height);

        Matrix4f trans = getTransform();

        Vertex[] vertices = new Vertex[width * height * DX.length];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Vector2f[] texCoords = Tile.getTextCoords(tileset, tiles[i][j]);
                for (int k = 0; k < DX.length; k++) { // for each vertex
                    int n = i * height * DX.length + j * DX.length + k; // 1d index
                    vertices[n] = new Vertex();
                    vertices[n].position = new Vector2f((j + DX[k]) * tileWidth * 2 - 1, ((i + DY[k]) * tileHeight * 2 - 1));
                    vertices[n].texCoords = texCoords[k];
                }
            }
        }
        buffer.setData(vertices);
    }

    public void dispose() {
        tileset.dispose();
        buffer.dispose();
        tilemap.dispose();
    }
}


