package ImGui;

import core.Window;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.internal.flag.ImGuiDockNodeFlags;
import imgui.type.ImBoolean;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;

/**
 * Class that integrates ImGui into the engine
 * @author Jonathan Salisbury
 */
public class ImGuiLayer {

    private final ImGuiImplGlfw implGlfw;
    private final ImGuiImplGl3 implOpengl;
    private long window;

    /**
     * Default constructor
     */
    public ImGuiLayer(){
        implGlfw = new ImGuiImplGlfw();
        implOpengl = new ImGuiImplGl3();
    }

    /**
     * Initializes and configurates ImGui.
     * @param window window identifier for rendering context.
     */
    public void startup(long window) {
        ImGui.createContext();
        ImGui.styleColorsDark();

        final ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);                                // We don't want to save .ini file
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);  // Enable Keyboard Controls
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);      // Enable Docking
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);    // Enable Multi-Viewport / Platform Windows
        io.setConfigViewportsNoTaskBarIcon(true);

        implGlfw.init(window, true);
        implOpengl.init();
    }

    /**
     * Method that enables imgui rendering, it's called before the OnImGuiRender.
     */
    public void beginFrame() {
        implGlfw.newFrame();
        ImGui.newFrame();

//        int windowFlags = ImGuiWindowFlags.MenuBar | ImGuiWindowFlags.NoDocking;
//        Vector2i windowPos = Window.getPos();
//        ImGui.setNextWindowPos(windowPos.x, windowPos.y, ImGuiCond.Always);
//        Vector2i windowSize = Window.getSize();
//        ImGui.setNextWindowSize(windowSize.x, windowSize.y);
//        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0);
//        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0);
//        windowFlags |= ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse |
//                ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove |
//                ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;
//
//        ImGui.begin("Dockspace Demo", new ImBoolean(true), windowFlags);
//        ImGui.popStyleVar(2);
//        ImGui.dockSpace(ImGui.getID("Dockspace"));
    }

    /**
     * Method that finishes imgui rendering, it's called after the OnImGuirender.
     */
    public void endFrame() {
        ImGuiIO io = ImGui.getIO();
        Vector2i windowSize = Window.getSize();
        io.setDisplaySize(windowSize.x, windowSize.y);

        //ImGui.end();

        ImGui.render();
        implOpengl.renderDrawData(ImGui.getDrawData());

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupWindowPtr = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }
    }

    /**
     * Method that frees all imgui resources.
     */
    public void dispose() {
        implOpengl.dispose();
        implGlfw.dispose();
        ImGui.destroyContext();
    }
}
