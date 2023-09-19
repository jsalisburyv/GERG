package map;

import core.Window;
import graphics.render.Drawable;
import graphics.render.GameObject;
import graphics.render.RenderStates;
import graphics.render.RenderTarget;
import graphics.tilemap.Tilemap;
import graphics.tilemap.Tileset;
import graphics.tilemap.Tile;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import procgen.bsp.BSPTile;

/**
 * Class that holds all the information for the map
 * @author Jonathan Salisbury
 */
public class Map implements Drawable {
    public static float TILE_SIZE;

    private Tileset tileset;
    private Tilemap tilemap;

    private int nrows, ncols;

    /**
     * Default constructor that creates the map using BSP
     * @param nrows number of rows for the map
     * @param ncols number of columns for the map
     */
    public Map(int nrows, int ncols) {
        this.nrows = nrows;
        this.ncols = ncols;
        TILE_SIZE = Window.getMinimumDimension() / nrows;
        tileset = new Tileset("simple3.png", new Vector2i(16, 16));

        int[][] map = BSPTile.generateMap(tileset, nrows, ncols);
        Tile[][] tiles = Autotiling.assignTileTypes(map);
        tilemap = new Tilemap(tiles, tileset);

        float min = Window.getMinimumDimension() / 2;
        tilemap.setTransform(new Matrix4f());
        tilemap.translate(min, min);
        tilemap.setScale(min);
    }

    /**
     * Method for rendering the map using the tileset vertexArray
     * @param target renderer
     * @param states states
     */
    @Override
    public void draw(RenderTarget target, RenderStates states) {
        tilemap.draw(target, states);
    }

    /**
     * Method for checking if a GameObject is colliding with a solid tile
     * @param object GameObject to check
     * @return True if there is a collision
     */
    public boolean checkCollision(GameObject object) {
        Vector2f objectGridPosition = object.getPosition();
        int i = (int) (objectGridPosition.y / TILE_SIZE);
        int j = (int) (objectGridPosition.x / TILE_SIZE);
        //System.out.println("(" + i + ", " + j + ")");
        return !tilemap.getTile(i, j).isPassable();
    }

    /**
     * Method that frees the map resources
     */
    public void dispose() {
        tilemap.dispose();
        tileset.dispose();
    }

    /**
     * Method to obtain the map dimensions
     * @return Vector containing number of rows and cols
     */
    public Vector2i dimensions() {
        return new Vector2i(nrows, ncols);
    }

    /**
     * Method for obtaining a given tile in the map
     * @param i row of the tile
     * @param j col of the tile
     * @return Tile
     */
    public Tile getTile(int i, int j){
        return tilemap.getTile(i, j);
    }
}
