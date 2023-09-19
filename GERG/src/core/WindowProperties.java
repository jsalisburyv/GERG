package core;

import core.config.Config;
import events.Event.EventCallback;

/**
 * Class that represents the properties that can be set for a Window.
 * @author Jonathan Salisbury
 */
public class WindowProperties {
    public String title;
    public int width, height;
    public boolean vsync, fullscreen, showCursor;
    public EventCallback callback;
    //cursor

    /**
     * Default constructor that initialices the properties to the config file.
     */
    private WindowProperties() {
        this.title = Config.getStringValue("window.title");
        this.width = Config.getIntValue("window.width");
        this.height = Config.getIntValue("window.height");
        this.vsync = Config.getBoolValue("window.vsync");
        this.fullscreen = Config.getBoolValue("window.fullscreen");
        this.showCursor = Config.getBoolValue("window.show_cursor");
    }

    /**
     * Constructor with parameters.
     * @param title String name for the window.
     * @param width int width of the window.
     * @param height int height of the window.
     * @param vsync boolean if vsync should be enabled.
     * @param fullscreen boolean if fullscreen should be enabled.
     * @param showCursor boolean if cursor should be shown.
     * @param callback callback function for Engine events.
     */
    public WindowProperties(String title, int width, int height, boolean vsync, boolean fullscreen, boolean showCursor, EventCallback callback) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vsync = vsync;
        this.fullscreen = fullscreen;
        this.showCursor = showCursor;
        this.callback = callback;
    }

    /**
     * Method that returns the default construction of the properties.
     * @return WindowProperties instance.
     */
    public static WindowProperties getDefaultProperties() {
        return new WindowProperties();
    }

    /**
     * Method for setting the callback for Engine events.
     * @param callback function callback for the events.
     */
    public void setEventCallback(EventCallback callback) {
        this.callback = callback;
    }
}
