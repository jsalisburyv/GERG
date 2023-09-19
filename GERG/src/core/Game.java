package core;

import events.Event;

/**
 * Interface for the User Game implementation.
 * @author Jonathan Salisbury
 */
public interface Game extends Module {

    /**
     * Method for handling Engine envents
     * @param event triggered Event
     */
    void onEvent(Event event);

    /**
     * Method for adding Imgui functionality
     */
    void onImGuiRender();
}
