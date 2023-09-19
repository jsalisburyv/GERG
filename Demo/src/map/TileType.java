package map;

import graphics.render.GameObject;
import graphics.tilemap.Tile;

import java.util.List;

/**
 * Enum that represents the different types of tiles in the map
 * @author Jonathan Salisbury
 */
public enum TileType implements Tile {
    EMPTY(false, false, false),
    FLOOR(true, true, true),
    FLOOR2(true, true, true),

    CHEST(true, true, true),
    LIGHT(true, true, true),

    WALL_TOP_LEFT(false, false, false),
    WALL_TOP(false, false, false),
    WALL_TOP_RIGHT(false, false, false),

    WALL_BOTTOM_AND_RIGHT(false, false, false),
    WALL_BOTTOM_AND_LEFT(false, false, false),

    WALL_LEFT(false, false, false),
    EMPTY2(false, false, false),
    WALL_RIGHT(false, false, false),

    WALL_TOP_AND_RIGHT(false, false, false),
    WALL_TOP_AND_LEFT(false, false, false),

    WALL_BOTTOM_LEFT(false, false, false),
    WALL_BOTTOM(false, false, false),
    WALL_BOTTOM_RIGHT(false, false, false),

    WALL_TOP_AND_BOTTOM(false, false, false),
    WALL_LEFT_AND_RIGHT(false, false, false),

    WALL_LEFT_TOP_RIGHT(false, false, false),
    WALL_TOP_RIGHT_BOTTOM(false, false, false),
    WALL_RIGHT_BOTTOM_LEFT(false, false, false),
    WALL_BOTTOM_LEFT_TOP(false, false, false),

    EMPTY3(false, false, false),
    WALL_TOP_LEFT_AND_RIGHT(false, false, false),
    WALL_TOP_RIGHT_AND_LEFT(false, false, false),
    WALL_BOT_RIGHT_AND_LEFT(false, false, false),
    WALL_BOT_LEFT_AND_RIGHT(false, false, false),

    EMPTY4(false, false, false),

    WALL_TOP_LEFT_AND_BOT(false, false, false),
    WALL_TOP_RIGHT_AND_BOT(false, false, false),
    WALL_BOT_RIGHT_AND_TOP(false, false, false),
    WALL_BOT_LEFT_AND_TOP(false, false, false),
    EMPTY5(false, false, false);


    private int index;
    private boolean transparent, passable, walkable;

    TileType(boolean transparent, boolean passable, boolean walkable) {
        this.index = this.ordinal();
        this.transparent = transparent;
        this.passable = passable;
        this.walkable = walkable;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public boolean isTransparent() {
        return transparent;
    }

    @Override
    public boolean isPassable() {
        return passable;
    }

    @Override
    public boolean isWalkable() {
        return walkable;
    }

    @Override
    public List<GameObject> getGameObjects() {
        return null;
    }

    @Override
    public void addGameObject(GameObject gameObject) {

    }

    @Override
    public void removeGameObject(GameObject gameObject) {

    }

    @Override
    public String toString() {
        return String.valueOf(index);
    }
}
