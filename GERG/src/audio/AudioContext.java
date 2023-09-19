package audio;

import org.lwjgl.openal.*;
import java.util.logging.Logger;
import static org.lwjgl.openal.ALC10.*;

/**
 * Class used for managing the audio Context.
 * @author Jonathan Salisbury
 */
public class AudioContext {

    private static final Logger LOGGER = Logger.getLogger(AudioContext.class.getSimpleName());

    private final long audioDevice;
    private long audioContext;

    /**
     * Create the context using the default audio device.
     */
    public AudioContext() {
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        audioDevice = alcOpenDevice(defaultDeviceName);
        OpenALError.checkALCError(audioDevice);
    }

    /**
     * Method that initiates and sets this context to be the current.
     */
    public void init() {
        audioContext = alcCreateContext(audioDevice, new int[]{0});
        OpenALError.checkALCError(audioDevice);
        alcMakeContextCurrent(audioContext);
        OpenALError.checkALCError(audioDevice);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        AL.createCapabilities(alcCapabilities);

        LOGGER.config("OpenAL Initialized:");
        LOGGER.config("  Device: %s".formatted(alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER)));
    }
    
    /**
     * Method that disables and deletes this context.
     */
    public void delete() {
        alcDestroyContext(audioContext);
        OpenALError.checkALCError(audioDevice);
        alcCloseDevice(audioDevice); 
        OpenALError.checkALCError(audioDevice);
    }
}
