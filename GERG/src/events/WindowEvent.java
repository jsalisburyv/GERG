/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

/**
 * Abstract class that represent a Window event.
 * @author Jonathan Salisbury
 */
public abstract class WindowEvent extends Event {

    /**
     * Abstract class that represent a Window Resizing event.
     * @author Jonathan Salisbury
     */
    public static class WindowResizedEvent extends WindowEvent {
        
        private final int width, height;

        /**
         * Default constructor
         * @param width new width of the window
         * @param height new height of the window
         */
        public WindowResizedEvent(int width, int height) {
            this.width = width;
            this.height = height;
        }

        /**
         * Method for obtaining the new Window width
         * @return
         */
        public int getWidth() { return this.width; }

        /**
         * Method for obtaining the new Window height
         * @return
         */
        public int getHeight() { return this.height; }
    }

    /**
     * Abstract class that represent a Window Closing event.
     * @author Jonathan Salisbury
     */
    public static class WindowClosedEvent extends WindowEvent { }
}
