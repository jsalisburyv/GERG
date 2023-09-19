package graphics.tilemap;

import graphics.render.GameObject;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.List;

public interface Tile {

    int getIndex();

    boolean isTransparent(); // can see objects behind or blocks sight.
    boolean isPassable(); // can projectiles go through the tile.
    boolean isWalkable (); // can you walk through the tile.

    public List<GameObject> getGameObjects();
    public void addGameObject(GameObject gameObject);
    public void removeGameObject(GameObject gameObject);

    public static Vector2f[] getTextCoords(Tileset tileset, Tile tile) {
        int id = tile.getIndex();

        Vector2i texSize = tileset.getTexSize();

        float texWidth = texSize.x / (float) (tileset.getWidth());
        float texHeight = texSize.y / (float) (tileset.getHeight());

        int numTexTiles = tileset.getWidth() / texSize.x;

        float x = (id % numTexTiles) * texWidth;
        float y = (id / numTexTiles) * texHeight;


        Vector2f topLeft = new Vector2f(x, y);
        Vector2f topRight = new Vector2f(x + texWidth, y);
        Vector2f bottomLeft = new Vector2f(x, y + texHeight);
        Vector2f bottomRight = new Vector2f(x + texWidth, y + texHeight);
        return new Vector2f[] {topLeft, topRight, bottomRight, bottomLeft};
    }
}
