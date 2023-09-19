package graphics.vertex;

import org.joml.Vector4f;

/**
 * Class that represents Color for rendering
 * @author Jonathan Salisbury
 */
public class Color extends Vector4f {

    /* Constant commonly used colors */
    public static final Color RED = new Color(255,0, 0);
    public static final Color ORANGE = new Color(255,128, 0);
    public static final Color YELLOW = new Color(255,255, 0);
    public static final Color LIME = new Color(128,255, 0);
    public static final Color GREEN = new Color(0,255, 0);
    public static final Color TURQUOISE = new Color(0,255, 128);
    public static final Color CYAN = new Color(0,255, 255);
    public static final Color LIGHT_BLUE = new Color(0,128, 255);
    public static final Color BLUE = new Color(0,0, 255);
    public static final Color PINK = new Color(255,0, 255);
    public static final Color BLACK = new Color(0,0, 0);
    public static final Color GREY = new Color(128,128, 128);
    public static final Color WHITE = new Color(255,255, 255);

    /**
     * Default constructor
     * @param r amount of red between 0 and 255
     * @param g amount of green between 0 and 255
     * @param b amount of blue between 0 and 255
     */
    public Color(float r, float g, float b) {
        super(r, g, b, 255);
    }

    /**
     * Default constructor
     * @param r amount of red between 0 and 255
     * @param g amount of green between 0 and 255
     * @param b amount of blue between 0 and 255
     * @param a amount of alpha between 0 and 255
     */
    public Color(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    /**
     * Default vector constructor
     * @param color Color vector to copy
     */
    public Color(Color color)
    {
        super(color);
    }

    /**
     * Method that returns a float array containing the color info
     * @return float array
     */
    public float[] toFloatArray(){
        float[] array = new float[4];
        array[0] = x;
        array[1] = y;
        array[2] = z;
        array[3] = w;
        return array;
    }

    /**
     * Method that normalices a color to 0 and 1.
     * @return new normaliced instance
     */
    public Color normalize() {
        Color color = new Color(this);
        color.x = x/255f;
        color.y = y/255f;
        color.z = z/255f;
        color.w = w/255f;
        return color;
    }
}
