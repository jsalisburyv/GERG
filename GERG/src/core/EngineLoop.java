package core;

import ImGui.ImGuiLayer;
import audio.AudioModule;
import core.config.Config;
import core.scenes.Menu;
import core.scenes.SceneModule;
import events.Event;
import events.KeyEvent;
import events.WindowEvent.WindowClosedEvent;
import org.lwjgl.glfw.GLFW;
import system.Clock;
import system.Time;
import utils.GERGLogger;

/**
 * Main Class of the Game engine that manages the modules and the gameloop.
 * @author Jonathan Salisbury
 */
public class EngineLoop {
    
    /* Static Variables */
    private static EngineLoop instance;
    private static Clock clock;
    
    public boolean running;
    private float accumulateTime, intervalTime;
    private int maxSimulationCount;
    
    /* Modules */
    Window window;
    ImGuiLayer imgui;
    AudioModule audioModule;
    SceneModule sceneModule;

    Game game;
    Menu menu;

    /**
     * Starting method to be called by the game.
     * @param game Instance of the game to be run.
     * @param menu Instance of the menu for the game.
     */
    public static void start(Game game, Menu menu) {
        EngineLoop loop = getInstance(game, menu);
        if(loop.running)
            throw new RuntimeException("Cannot start engine after it has already been started!");
        loop.run();
    }

    /**
     * Method that shutsdown the engine.
     */
    public static void exit() {
        EngineLoop.getInstance(null, null).running = false;
    }

    /**
     * Private constructor used for creating the singleton instance.
     * @param game Instance of the game to be run.
     * @param menu Instance of the menu for the game.
     */
    private EngineLoop(Game game, Menu menu) {
        Config.init();
        GERGLogger.init();
        
        /* Create Modules */
        window = Window.getWindow(this::onEvent);
        imgui = new ImGuiLayer();

        audioModule = new AudioModule();
        sceneModule = new SceneModule();

        this.game = game;
        this.menu = menu;
        
        Input.init(window.getHandle(), this::onEvent);
    }
    
    public void destroy() {
        /* Delete Modules */
    }
    
    /* Static Functions */

    /**
     * Static getter for the singleton instance
     * @param game Instance of the game to be run in case of creation.
     * @param menu Instance of the menu for the game in case of creation.
     * @return instance of the EngineLoop
     */
    public static EngineLoop getInstance(Game game, Menu menu) {
        if(instance == null) {
            instance = new EngineLoop(game, menu);
        }
        return instance;
    }

    /**
     * Static getter for the singleton Clock variable.
     * @return instance of the Clock.
     */
    public static Clock getClock() {
        if(clock == null) {
            clock = new Clock();
        }
        return clock;
    }
    
    /* Class functions */

    /**
     * Main method of the EngineLoop, it starts the modules, then calls periodically the update method and finally
     * shutsdown the modules.
     */
    public void run() {
        startUp();
        while(running) {
            update();
        }
        shutdown();
    }

    /**
     * startUp method that initializes all the modules.
     */
    private void startUp(){
        intervalTime = 1.0f / Config.getIntValue("engine.max_fps");
        maxSimulationCount = Config.getIntValue("engine.max_simulation_count");
        running = true;
        
        /* Start Modules */
        imgui.startup(window.getHandle());
        audioModule.startUp();
        sceneModule.startUp();

        game.startUp();
        menu.startUp();
        
        startGameClock();
        if(window.isFullscreen()) window.setFullscreen(true);
    }

    /**
     * Method that is periodically called to update the game and the modules each frame
     */
    private void update() {
        Time deltaTime = clock.restart();
        for (int i = 0; i < maxSimulationCount && accumulateTime > intervalTime; i++) {      
            fixedUpdate();
            accumulateTime -= intervalTime;
        }
        variableUpdate(deltaTime.asSeconds());
    }

    /**
     * Method that updates the modules not dependant on the deltatime.
     */
    private void fixedUpdate() { }

    /**
     * Method that updates the modules that depend on the deltatime.
     * @param deltaTime time since the last frame
     */
    private void variableUpdate(final float deltaTime) {
        if(menu.isInMenu()) {
            menu.render();
        } else {
            //window.beginFrame();
            game.update(deltaTime);
            //window.endFrame();

            imgui.beginFrame();
            game.onImGuiRender();
            imgui.endFrame();
        }

        //sceneModule.update(deltaTime);
        window.update(deltaTime);
    }

    /**
     * Method that shutdowns all of the modules
     */
    private void shutdown() {
        game.shutdown();

        /* Shutdown modules */
        sceneModule.shutdown();
        audioModule.shutdown();

        imgui.dispose();
        window.dispose();
    }

    /**
     * Method for starting the engine clock.
     */
    private void startGameClock() {
        getClock();
    }

    /**
     * Method for handling Engine Events, if its a WindowClosedEvent it shutdowns the engine, otherwise it cascades it
     * to the Game onEvent method.
     * @param event
     */
    private void onEvent(Event event) {
        if(event instanceof WindowClosedEvent evt) {
            this.running = false;
        }
        if(event instanceof KeyEvent.KeyPressedEvent evt) {
            if(evt.getKeycode() == GLFW.GLFW_KEY_F11){
                window.setFullscreen(!window.isFullscreen());
            }
        }
        menu.onEvent(event);
        game.onEvent(event);
    }
}
