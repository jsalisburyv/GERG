package audio;


import core.Module;
import core.resources.ResourceManager;

/**
 * Class for managing the audio environment in the engine loop.
 * @author Jonathan Salisbury
 */
public class AudioModule implements Module {
    
    private AudioContext audioContext;

    /**
     * Default constructor that creates the audiocontext.
     */
    public AudioModule() {
        audioContext = new AudioContext();
    }

    /**
     * Method that initializes the audiocontext
     */
    public void startUp() {
        audioContext.init();
    }
    
    public void update(float deltatime) {}

    /**
     * Unloads all audio resorces and deletes the audiocontext
     */
    public void shutdown() {
        ResourceManager.unloadAllAudios();
        audioContext.delete();
    }
}
