package characters;

import core.resources.ResourceManager;
import graphics.render.Sprite;
import map.Map;
import org.joml.Vector2f;
import org.joml.Vector2i;
import utils.Pathfinding;

import java.util.List;

import static map.Map.TILE_SIZE;

/**
 * Class to create enemies in the demo
 * @author Jonathan Salisbury
 */
public class Enemy extends Sprite {

    private static final int WAIT_TILL_CHASE = 3;
    private static final int REFRESH_PATH = 3;
    private static final int VISION_RADIUS = 10;

    private Vector2f playerPosition;
    private float timeElapsed;
    private Map map;
    private boolean isChasing, forceRefresh;
    private List<Pathfinding.Node> path;

    private int nodeIndex;

    /**
     * Default Constructor
     * @param position starting position of the enemy
     * @param map map object
     */
    public Enemy(Vector2f position, Map map) {
        super(position, ResourceManager.getTexture("enemy_attack.png"), 40, 40, 7);
        this.timeElapsed = 0;
        this.map = map;
        isChasing = false;
    }

    /**
     * Method for updating the path to the player
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        chasePlayer(deltaTime);
        super.update(deltaTime);
    }

    /**
     * Method for chasing the player, it updates the path every REFRESH_PATH seconds
     * @param deltaTime time since last frame
     */
    public void chasePlayer(float deltaTime) {
        if (playerPosition == null) {
            return;
        }
        if (!isChasing && isPlayerInRadius()) {
            isChasing = true;
        }
        if (!isChasing) {
            // Handle casual movement
            // ...
            return;
        }
        timeElapsed += deltaTime;
        if (path == null || timeElapsed > REFRESH_PATH || nodeIndex >= path.size()) {
            timeElapsed -= REFRESH_PATH;
            refreshPath();
        }
        if (path != null && nodeIndex < path.size()) {
            updateNextTilePosition(deltaTime);
        }
    }

    /**
     * Method that recalculates the path to the player
     */
    private void refreshPath() {
        Vector2i start = new Vector2i((int) (position.x / TILE_SIZE), (int) (position.y / TILE_SIZE));
        Vector2i goal = new Vector2i((int) (playerPosition.x / TILE_SIZE), (int) (playerPosition.y / TILE_SIZE));
        path = Pathfinding.findPath(start, goal, map);
        nodeIndex = 1;
    }

    /**
     * Method that moves the enemy to the next position of the path
     * @param deltaTime time since last frame
     */
    private void updateNextTilePosition(float deltaTime) {
        Vector2f nextTilePos = new Vector2f(path.get(nodeIndex).getPosition()).mul(TILE_SIZE);
        if (position.equals(nextTilePos, 0.01f)) {
            nodeIndex++;
            if (nodeIndex < path.size()) {
                nextTilePos.set(path.get(nodeIndex).getPosition()).mul(TILE_SIZE);
            }
        }
        Vector2f direction = new Vector2f(nextTilePos).sub(position).normalize();
        float distance = TILE_SIZE * 3 * deltaTime;
        translate(direction.mul(distance));
    }


    /**
     * Method that check if the player is in the specified radius
     * Not used yet
     * @return
     */
    public boolean isPlayerInRadius(){
        Vector2i start = new Vector2i((int) (position.x / TILE_SIZE), (int) (position.y / TILE_SIZE));
        Vector2i goal = new Vector2i((int) (playerPosition.x / TILE_SIZE), (int) (playerPosition.y / TILE_SIZE));
        Vector2i direction = new Vector2i(goal).sub(start).absolute();
        //return (direction.x > VISION_RADIUS || direction.y > VISION_RADIUS);
        return true;
    }

    /**
     * Method for setting the player position for path calculations
     * @param playerPosition current player position on the map
     */
    public void setPlayerPosition(Vector2f playerPosition) {
        this.playerPosition = new Vector2f(playerPosition);
    }
}
