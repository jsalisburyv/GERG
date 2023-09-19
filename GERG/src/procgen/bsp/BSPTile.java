package procgen.bsp;

import graphics.tilemap.Tileset;
import org.joml.Vector2f;

import java.awt.*;
import java.util.Random;

public class BSPTile {

    public static int MAP_SIZE = 50;
    private static int SEED = 42;

    public static final float ITERATIONS_RATIO = 0.7f;
    public static int ITERATIONS;
    public static boolean CHECK_RATIO = true;
    public static float VERTICAL_RATIO = 0.45f;
    public static float HORIZONTAL_RATIO = 0.45f;

    public static boolean HAS_PADDING = true;
    public static int PADDING = 1;

    private static Random rand = new Random(SEED);
    private static Tileset tileset;
    public static Vector2f startingPoint;

    public static int[][] MAP = new int[MAP_SIZE][MAP_SIZE];

    private static int calculateIterations(int nrows, int ncols) {
        int maxDimension = Math.max(nrows, ncols);
        int maxIterations = (int) (Math.log(maxDimension) / Math.log(2));
        return (int) (maxIterations * ITERATIONS_RATIO + 1);
    }

    public static int[][] generateMap(Tileset tileset, int nrows, int ncols) {
        BSPTile.ITERATIONS = calculateIterations(nrows, ncols);
        BSPTile.tileset = tileset;
        MAP = new int[nrows][ncols];
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                MAP[i][j] = 1;
            }
        }
        Node root = splitNode(new Rectangle(0, 0, ncols , nrows), BSPTile.ITERATIONS);
        root.createRoom();

        Node topLeft = getTopLeftRoom(root);
        startingPoint = new Vector2f(topLeft.center.x, topLeft.center.y);
        return MAP;
    }

    private static Node splitNode(Rectangle rect, int iter) {
        Node root = new Node(rect);
        if(iter != 0) {
            Rectangle[] rects = randomSplit(rect);
            root.left = splitNode(rects[0], iter-1);
            root.right = splitNode(rects[1], iter-1);
        }
        return root;
    }

    private static Rectangle[] randomSplit(Rectangle rect) {
        if(rect == null) return null;
        Rectangle rect1, rect2;
        if(rand.nextBoolean()) { // is Vertical
            rect1 = new Rectangle(rect.x, rect.y, rand.nextInt(1, rect.width), rect.height);
            rect2 = new Rectangle(rect.x + rect1.width, rect.y, rect.width - rect1.width, rect.height);
            if(CHECK_RATIO && (rect1.width / (float) rect1.height < HORIZONTAL_RATIO || rect2.width / (float) rect2.height < HORIZONTAL_RATIO)) {
                return randomSplit(rect);
            }
        } else { // is Horizontal
            rect1 = new Rectangle(rect.x, rect.y, rect.width, rand.nextInt(1, rect.height));
            rect2 = new Rectangle(rect.x, rect.y + rect1.height, rect.width, rect.height - rect1.height);
            if(CHECK_RATIO && (rect1.height / (float) rect1.width < VERTICAL_RATIO || rect2.height / (float) rect2.width < VERTICAL_RATIO)) {
                return randomSplit(rect);
            }
        }
        return new Rectangle[]{rect1, rect2};
    }

    private static Node getTopLeftRoom(Node node) {
        if (node.left == null && node.right == null) {
            return node;
        } else {
            return getTopLeftRoom(node.left);
        }
    }

    public static class Node {

        Rectangle rect;
        Point center;
        Node left, right;

        Node(Rectangle rect) {
            this.rect = rect;
            this.center = new Point(rect.x + (rect.width)/2, rect.y + (rect.height)/2);
            left = right = null;
        }

        Rectangle getRoom() {
            Rectangle room = new Rectangle(rect);
            if(HAS_PADDING) { // has vertical padding
                room.y += PADDING;
                room.height -= PADDING * 2;
            }
            if(HAS_PADDING) { // has vertical padding
                room.x += PADDING;
                room.width -= PADDING * 2;
            }
            return room;
        }

        public void createRoom() {
            if(this.left == null && this.right == null) {
                Rectangle room = getRoom();
                for (int i = room.y; i < room.y + room.height; i++) {
                    for (int j = room.x; j < room.x + room.width; j++) {
                        MAP[i][j] = 0;
                    }
                }

            } else {
                if(left.center.x == right.center.x) {
                    int start = Math.min(left.center.y, right.center.y);
                    int end = Math.max(left.center.y, right.center.y);
                    for (int i = start; i < end; i++) {
                        MAP[i][left.center.x] = 0;
                    }
                } else if(left.center.y == right.center.y) {
                    int start = Math.min(left.center.x, right.center.x);
                    int end = Math.max(left.center.x, right.center.x);
                    for (int j = start; j < end; j++) {
                        MAP[left.center.y][j] = 0;
                    }
                }
                left.createRoom();
                right.createRoom();
            }
        }
    }
}
