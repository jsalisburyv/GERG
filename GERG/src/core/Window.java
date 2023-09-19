package core;

import events.Event;
import events.WindowEvent.WindowClosedEvent;
import events.WindowEvent.WindowResizedEvent;
import graphics.core.Framebuffer;
import graphics.core.GraphicsContext;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.logging.Logger;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Class for managing the window with GLFW
 * @author Jonathan Salisbury
 */
public class Window {
    
    private static boolean GLFWInitialized;
    private static final Logger LOGGER = Logger.getLogger(Window.class.getSimpleName());
    private static Window instance;
    
    private final WindowProperties properties;
    private final GraphicsContext context;
    private final long windowHandle, monitor;
    private boolean minimized, updateViewport;
    private int xPos, yPos;
    private int vpWidth, vpHeight;

    /**
     * Method for initalizing the GLFW library.
     */
    private static void initGLFW(){
        if(!GLFWInitialized) {
            boolean success = GLFW.glfwInit();
            assert success : "Unable to initialize GLFW";
            GLFWInitialized = true;
        }
        GLFWErrorCallback.createPrint(System.err).set();
        // glfwSetErrorCallback(Window::GLFWErrorCallback);
    }

    /**
     * Static getter for the singleton instance
     * @param callback event callback just in case of creation
     * @return current instance
     */
    public static Window getWindow(Event.EventCallback callback){
        if(instance == null) {
            instance = new Window(callback);
        }
        return instance;
    }

    /**
     * Private constructor for creating the singleton instance.
     * @param callback Callback function for Engine Events.
     */
    private Window(Event.EventCallback callback) {
        initGLFW();
        if(System.getenv("DEBUG").equals("true")) glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT , GLFW_TRUE);

        monitor = glfwGetPrimaryMonitor();

        properties = WindowProperties.getDefaultProperties();
        properties.setEventCallback(callback);
        /* if(properties.fullscreen){
            GLFWVidMode mode = glfwGetVideoMode(monitor);
            LOGGER.config("Creating Fullscreen Window: %s (%d, %d)".formatted(properties.title, mode.width(), mode.height()));
            vpWidth = mode.width();
            vpHeight = mode.height();
            windowHandle = glfwCreateWindow(mode.width(), mode.height(), properties.title, monitor, NULL);
        } else { */
            GLFWVidMode mode = glfwGetVideoMode(monitor);
            if(properties.width == -1) properties.width = mode.width();
            if(properties.height == -1) properties.height = mode.height();
            LOGGER.config("Creating Window: %s (%d, %d)".formatted(properties.title, properties.width, properties.height));
            vpWidth = properties.width;
            vpHeight = properties.height;
            windowHandle = glfwCreateWindow(properties.width, properties.height, properties.title, NULL, NULL);
       // }
        assert windowHandle != NULL : "Failed to create GLFW Window";

        context = new GraphicsContext(windowHandle);
        context.init();

        setEventCallbacks();
        glfwSwapInterval(properties.vsync ? 1 : 0);
        glfwSetInputMode(windowHandle, GLFW_CURSOR, properties.showCursor ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_DISABLED);

        updateViewport = true;
    }

    /**
     * Method that returns if the window is minimized or not.
     * @return boolean isMinimized.
     */
    public boolean isMinimized() {
        return this.minimized;
    }

    /**
     * Update method that checks if the viewport needs resizing and swaps the render buffers.
     * @param deltatime time since last frame
     */
    public void update(double deltatime) {
        if (!isMinimized()) {
            if(updateViewport) {
                Vector2i windowSize = getSize();
                int minDimension = Math.min(windowSize.x, windowSize.y);
                int offsetX = (windowSize.x - minDimension) / 2;
                int offsetY = (windowSize.y - minDimension) / 2;

                glViewport(0, 0, vpWidth, vpHeight);
                updateViewport = false;
            }
            context.swapBuffers();
            glfwPollEvents();
        }
    }

    /**
     * Gets the window handle from GLFW
     * @return
     */
    public long getHandle() {
        return this.windowHandle;
    }

    /**
     * Method that sets the window to fullscreen depending on the boolean parameter
     * @param fullscreen if window should be in fullscreen mode
     */
    public void setFullscreen(boolean fullscreen) {
        //if(properties.fullscreen == fullscreen) return;
        properties.fullscreen = fullscreen;
        if(fullscreen) {
            // backup size and pos
            try (MemoryStack stack = MemoryStack.stackPush()) {
                IntBuffer xpos = stack.mallocInt(1), ypos = stack.mallocInt(1);
                IntBuffer width = stack.mallocInt(1), height = stack.mallocInt(1);
                glfwGetWindowPos(windowHandle, xpos, ypos);
                glfwGetWindowSize(windowHandle, width, height);
                this.xPos = xpos.get();
                this.yPos = ypos.get();
                properties.width = width.get();
                properties.height = height.get();

                GLFWVidMode mode = glfwGetVideoMode(monitor);
                glfwSetWindowMonitor(windowHandle, monitor, 0, 0, mode.width(), mode.height(), mode.refreshRate());
            }
        } else {
            glfwSetWindowMonitor(windowHandle, NULL, xPos, yPos, properties.width, properties.height, 0);
        }
        updateViewport = true;
    }

    /**
     * Check if window is currently in fullscreen mode
     * @return True if its in fullscreen mode
     */
    public boolean isFullscreen() {
        return properties.fullscreen;
    }

    /**
     * Method that frees all the resources used by the window
     */
    public void dispose() {
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /**
     * Function used for printing GLFW error messages
     * @param error error identifier
     * @param descriptionId identifier for the error message info
     */
    private static void GLFWErrorCallback(int error, long descriptionId) {
        String description = GLFWErrorCallback.getDescription(descriptionId);
        String msg = "GLFW Error (%d): %s".formatted(error, description);
        LOGGER.severe(msg);
    }

    /**
     * Function for setting a window changes callback.
     */
    private void setEventCallbacks(){
        glfwSetWindowCloseCallback(windowHandle, (long win) -> {
            WindowClosedEvent event = new WindowClosedEvent();
            //glfwDestroyWindow(windowHandle);
            properties.callback.call(event);
        });
        glfwSetWindowSizeCallback(windowHandle, (long win, int width, int height) -> {
            WindowResizedEvent event = new WindowResizedEvent(width, height);
            vpWidth = width;
            vpHeight = height;
            updateViewport = true;
            properties.callback.call(event);
        });
    }

    /**
     * Method for getting the current window size
     * @return Vector containing the width and height of the window
     */
    public static Vector2i getSize() {
        int[] width = new int[1], height = new int[1];
        glfwGetWindowSize(instance.windowHandle, width, height);
        return new Vector2i(width[0], height[0]);
    }

    /**
     * Method for getting the current window position
     * @return Vector containing the x and y coordinates of the window
     */
    public static Vector2i getPos() {
        int[] xpos = new int[1], ypos = new int[1];
        glfwGetWindowPos(instance.windowHandle, xpos, ypos);
        return new Vector2i(xpos[0], ypos[0]);
    }

    /**
     * Method for getting the smallest dimension
     * @return smallest value between current width and height
     */
    public static float getMinimumDimension() {
        int[] width = new int[1], height = new int[1];
        glfwGetWindowSize(instance.windowHandle, width, height);
        return Math.min(width[0], height[0]);
    }
}
