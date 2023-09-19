package core;

/**
 * Interface for the different modules of the engine
 * @author Jonathan Salisbury
 */
public interface Module {

    /**
     * Method that is called on engine startup
     */
    public void startUp();

    /**
     * Method that is periodically called each frame
     * @param deltatime time since last frame
     */
    public void update(float deltatime);

    /**
     * Method that is called before engine shutdown
     */
    public void shutdown();
}
