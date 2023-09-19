package events;

/**
 * Abstract class that represent a Engine event.
 * @author Jonathan Salisbury
 */
public abstract class Event {

    /**
     * Interface for enabling Event callback functions
     */
    public interface EventCallback {
        public void call(Event event);
    }
}
