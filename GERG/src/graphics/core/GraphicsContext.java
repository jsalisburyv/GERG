package graphics.core;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import org.lwjgl.opengl.GL;
import java.util.logging.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.GL_DEBUG_OUTPUT;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GraphicsContext {
    
    private static final Logger LOGGER = Logger.getLogger(GraphicsContext.class.getSimpleName());
    
    private final long windowHandle;
    
    public GraphicsContext(long windowHandle) {
        assert windowHandle != NULL : "Window Handle is NULL";
        this.windowHandle = windowHandle;
    }
    
    public void init() {
        glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();
        //glEnable(GL_DEBUG_OUTPUT);
        //GLUtil.setupDebugMessageCallback();
        LOGGER.config("OpenGL Initialized:");
        LOGGER.config("  Renderer: %s".formatted(glGetString(GL_RENDERER)));
        LOGGER.config("  Version: %s".formatted(glGetString(GL_VERSION)));
    }
    
    public void swapBuffers() {
        GLFW.glfwSwapBuffers(windowHandle);
    }
}
