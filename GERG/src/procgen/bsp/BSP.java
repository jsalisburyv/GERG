package procgen.bsp;

import java.awt.*;
import java.util.Random;

public class BSP {

    public static int MAP_SIZE = 50;
    private static int SEED = 5;
    public static int ITERATIONS = 4;
    public static boolean CHECK_RATIO = true;
    public static float VERTICAL_RATIO = 0.45f;
    public static float HORIZONTAL_RATIO = 0.45f;

    public static boolean HAS_PADDING = true;

    public static int HORIZONTAL_PADDING = 3;
    public static int VERTICAL_PADDING = 3;

    private static Random rand = new Random(SEED);

    public static int[][] MAP = new int[MAP_SIZE][MAP_SIZE];

    public static int[][] createMap() {
        Node root = splitNode(new Rectangle(0, 0, MAP_SIZE , MAP_SIZE), BSP.ITERATIONS);
        root.createRoom();
        return MAP;
    }

    public static Node createMap(int width, int height) {
        return splitNode(new Rectangle(0, 0, width , height), BSP.ITERATIONS);
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
                System.out.println("vertical padding");
                room.y += VERTICAL_PADDING;
                room.height -= VERTICAL_PADDING * 2;
            }
            if(HAS_PADDING) { // has vertical padding
                room.x += HORIZONTAL_PADDING;
                room.width -= HORIZONTAL_PADDING * 2;
            }
            return room;
        }

        public void createRoom() {
            if(this.left == null && this.right == null) {
                Rectangle room = getRoom();
                for (int i = room.x; i < room.x + room.width; i++) {
                    for (int j = room.y; j < room.y + room.height; j++) {
                        MAP[j][i] = 0;
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
                    for (int i = start; i < end; i++) {
                        MAP[left.center.y][i] = 0;
                    }
                }
                left.createRoom();
                right.createRoom();
            }
        }
    }
}
