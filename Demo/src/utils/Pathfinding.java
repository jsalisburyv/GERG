package utils;

import graphics.tilemap.Tile;
import map.Map;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class for implementing the pathfinding algorithm for the enemy
 */
public class Pathfinding {

    /**
     * Method that finds the path from the stort position to the goal.
     * @param start position to start the search
     * @param goal position to reach
     * @param map current map for detecting walls
     * @return Reconstructed path
     */
    public static List<Node> findPath(Vector2i start, Vector2i goal, Map map) {
        if (!isValid(start, map) || !isValid(goal, map)) return null;

        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();
        Node startNode = new Node(start);
        Node goalNode = new Node(goal);
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node current = openList.stream().min(Comparator.comparingInt(Node::getF)).orElse(null);
            if (current == null) break;
            openList.remove(current);
            closedList.add(current);

            if (current.pos.equals(goalNode.pos)) {
                return reconstructPath(current);
            }

            for (int[] dir : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
                Vector2i newPos = new Vector2i(current.pos.x + dir[0], current.pos.y + dir[1]);
                if (isValid(newPos, map)) {
                    Node neighbor = new Node(newPos);
                    int newG = current.g + 1; // Increment the cost by 1
                    if (!closedList.contains(neighbor) || newG < neighbor.g) {
                        neighbor.g = newG;
                        neighbor.h = Math.abs(newPos.x - goal.x) + Math.abs(newPos.y - goal.y);
                        neighbor.parent = current;
                        if (!openList.contains(neighbor)) {
                            openList.add(neighbor);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Method that reconstructs the final path to travel it
     * @param node last node found in the search
     * @return list of nodes that represeants the path
     */
    private static List<Node> reconstructPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Checks whether a tile is valid for the path
     * @param pos position of the tile to be checked
     * @param map map for checking tile properties
     * @return True if is valid to walk
     */
    private static boolean isValid(Vector2i pos, Map map) {
        Vector2i size = map.dimensions();
        if(pos.x >= 0 && pos.x < size.x && pos.y >= 0 && pos.y < size.y) {
            Tile tile = map.getTile(pos.y, pos.x);
            return tile.isWalkable();
        }
        return false;
    }

    /**
     * Auxiliary class that represents a node in the path
     */
    public static class Node {
        Vector2i pos;
        int g, h;
        Node parent;

        Node(Vector2i pos) {
            this.pos = new Vector2i(pos);
        }

        int getF() {
            return g + h;
        }

        public Vector2i getPosition(){
            return new Vector2i(pos);
        }
    }
}
