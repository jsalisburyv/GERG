package procgen.bsp;

import javax.swing.*;

import java.awt.*;

import static procgen.bsp.BSP.*;

public class BSPTest extends JPanel {

    static int WIDTH = 400;
    static int HEIGHT = 400;
    static int SQUARE_SIZE = WIDTH / MAP_SIZE;
    static boolean DRAW_GRID = false;

    private final Node root;

    public BSPTest(Node root) {
        this.root = root;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setOpaque(true);
        setBackground(Color.BLACK);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(new Color(0, 255, 0));
        paint(g2d, root);

        g2d.setColor(Color.gray);
        drawRooms(g2d, root);

        g2d.setStroke(new BasicStroke(SQUARE_SIZE));
        drawPaths(g2d, root);
        g2d.setStroke(new BasicStroke(1));

        if(DRAW_GRID) {
            g2d.setColor(Color.WHITE);
            for (int i = 0; i < MAP_SIZE; i++) {
                g2d.drawLine(0, i * SQUARE_SIZE, MAP_SIZE * SQUARE_SIZE, i * SQUARE_SIZE);
                g2d.drawLine(i * SQUARE_SIZE, 0, i * SQUARE_SIZE, MAP_SIZE * SQUARE_SIZE);
            }
        }
    }

    public static void paint(Graphics2D g2d, Node node) {
        g2d.drawRect(node.rect.x * SQUARE_SIZE, node.rect.y * SQUARE_SIZE, node.rect.width * SQUARE_SIZE, node.rect.height * SQUARE_SIZE);
        if(node.left != null) paint(g2d, node.left);
        if(node.right != null) paint(g2d, node.right);
    }

    public static void drawPaths(Graphics2D g2d, Node node) {
        if(node.left == null && node.right == null) {
            return;
        }
        g2d.drawLine(node.left.center.x * SQUARE_SIZE, node.left.center.y * SQUARE_SIZE ,
                node.right.center.x * SQUARE_SIZE, node.right.center.y * SQUARE_SIZE);
        drawPaths(g2d, node.left);
        drawPaths(g2d, node.right);
    }

    public static void drawRooms(Graphics2D g2d, Node node) {
        if(node.left == null && node.right == null) {
            Rectangle room = node.getRoom();
            g2d.fillRect(room.x * SQUARE_SIZE, room.y * SQUARE_SIZE, room.width * SQUARE_SIZE, room.height * SQUARE_SIZE);
        } else {
            drawRooms(g2d, node.left);
            drawRooms(g2d, node.right);
        }
    }

    public static void main(String[] args) {
        Node root = BSP.createMap(MAP_SIZE, MAP_SIZE);
        root.createRoom();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(new BSPTest(root));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
