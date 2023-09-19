package core;

import events.Event;
import events.KeyEvent.*;
import events.KeyEvent.KeyPressedEvent.KeyPressedCallback;
import events.KeyEvent.KeyReleasedEvent.KeyReleasedCallback;
import events.MouseEvent.MouseButtonEvent.*;
import events.MouseEvent.MouseButtonEvent.MouseButtonPressedEvent.MouseButtonPressedCallback;
import events.MouseEvent.MouseButtonEvent.MouseButtonReleasedEvent.MouseButtonReleasedCallback;
import events.MouseEvent.MouseMovedEvent;
import events.MouseEvent.MouseMovedEvent.MouseMovedCallback;
import events.MouseEvent.MouseScrolledEvent;
import events.MouseEvent.MouseScrolledEvent.MouseScrolledCallback;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Class for managing user input with GLFW
 * @author Jonathan Salisbury
 */
public class Input {

    private static long WINDOW;
    private static KeyPressedCallback keyPressedCallback = (event) -> {};
    private static KeyReleasedCallback keyReleasedCallback = (event) -> {};
    private static MouseButtonPressedCallback mouseButtonPressedCallback = (event) -> {};
    private static MouseButtonReleasedCallback mouseButtonReleasedCallback = (event) -> {};

    /**
     * Method that initializes and sets the callbacks
     * @param windowHandle id for the main window
     * @param callback callback to be set
     */
    public static void init(long windowHandle, Event.EventCallback callback) {
        WINDOW = windowHandle;
        setKeyCallback(callback);
        setMouseButtonCallback(callback);
        setMouseScrollCallback(callback);
        setMousePositionCallback(callback);
    }

    /**
     * Static method that returns the current mouse positions
     * @return float array containing the mouse coordinates.
     */
    public static float[] getMousePosition() {
        double[] xpos = new double[1];
        double[] ypos = new double[1];
        glfwGetCursorPos(WINDOW, xpos, ypos);
        return new float[]{(float) xpos[0], (float) ypos[0]};
    }

    /**
     * Static method that checks if a mouse button is pressed.
     * @param button button to be checked
     * @return True if the button is currently pressed
     */
    public static boolean isMouseButtonPressed(int button) {
        int state = glfwGetMouseButton(WINDOW, button);
        return state == GLFW_PRESS;
    }

    /**
     * Static method that checks if a key is pressed.
     * @param keycode key to be checked
     * @return True if the key is currently pressed
     */
    public static boolean isKeyPressed(int keycode) {
        int state = glfwGetKey(WINDOW, keycode);
        return state == GLFW_PRESS || state == GLFW_REPEAT;
    }

    /**
     * Method for setting the Keypressed callback
     * @param keyPressedCallback new callback to be set
     */
    public static void setKeyPressedCallback(KeyPressedCallback keyPressedCallback) {
        Input.keyPressedCallback = keyPressedCallback;
    }

    /**
     * Method for setting the Keyreleased callback
     * @param keyReleasedCallback new callback to be set
     */
    public static void setKeyReleasedCallback(KeyReleasedCallback keyReleasedCallback) {
        Input.keyReleasedCallback = keyReleasedCallback;
    }

    /**
     * Method for setting the MouseButton Pressed callback
     * @param mouseButtonPressedCallback new callback to be set
     */
    public static void setMouseButtonPressedCallback(MouseButtonPressedCallback mouseButtonPressedCallback) {
        Input.mouseButtonPressedCallback = mouseButtonPressedCallback;
    }

    /**
     * Method for setting the MouseButton Released callback
     * @param mouseButtonReleasedCallback new callback to be set
     */
    public static void setMouseButtonReleasedCallback(MouseButtonReleasedCallback mouseButtonReleasedCallback) {
        Input.mouseButtonReleasedCallback = mouseButtonReleasedCallback;
    }

    /**
     * Method for setting the MouseButton Scrolled callback
     * @param callback new callback to be set
     */
    public static void setMouseScrollCallback(Event.EventCallback callback) {
        glfwSetScrollCallback(WINDOW, (long win, double xOffset, double yOffset) -> {
            MouseScrolledEvent event = new MouseScrolledEvent((float) xOffset, (float) yOffset);
            callback.call(event);
        });
    }

    /**
     * Method for setting the Mouse position callback
     * @param callback new callback to be set
     */
    public static void setMousePositionCallback(Event.EventCallback callback) {
        glfwSetCursorPosCallback(WINDOW, (long win, double xPos, double yPos) -> {
            MouseMovedEvent event = new MouseMovedEvent((float) xPos, (float) yPos);
            callback.call(event);
        });
    }

    /**
     * Method that calls the respective callbacks
     * @param callback
     */
    private static void setKeyCallback(Event.EventCallback callback) {
        glfwSetKeyCallback(WINDOW, (long win, int key, int scancode, int action, int modes) -> {
            Event event = null;
            switch (action) {
                case GLFW_PRESS -> {
                    event = new KeyPressedEvent(key, 0);
                    keyPressedCallback.call((KeyPressedEvent) event);
                }
                case GLFW_RELEASE -> {
                    event = new KeyReleasedEvent(key);
                    keyReleasedCallback.call((KeyReleasedEvent) event);
                }
                case GLFW_REPEAT -> {
                    event = new KeyPressedEvent(key, 1);
                    keyPressedCallback.call((KeyPressedEvent) event);
                }
            }
            callback.call(event);
        });
    }

    private static void setMouseButtonCallback(Event.EventCallback callback) {
        glfwSetMouseButtonCallback(WINDOW, (long win, int button, int action, int modes) -> {
            Event event = null;
            switch (action) {
                case GLFW_PRESS -> {
                    event = new MouseButtonPressedEvent(button);
                    mouseButtonPressedCallback.call((MouseButtonPressedEvent) event);
                }
                case GLFW_RELEASE -> {
                    event = new MouseButtonReleasedEvent(button);
                    mouseButtonReleasedCallback.call((MouseButtonReleasedEvent) event);
                }
            }
            callback.call(event);
        });
    }
}
