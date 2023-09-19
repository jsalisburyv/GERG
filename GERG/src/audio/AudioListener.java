package audio;

import org.joml.Vector3f;
import static org.lwjgl.openal.AL10.*;

/**
 * Singleton Class that represents the Listener in a 3d audio context.
 * @author Jonathan Salisbury
 */
public class AudioListener {
    
    private static AudioListener instance;
    
    private AudioListener() {}
    
    /**
     * Static method that returns the current instance of the singleton. If 
     * no instance is present it creates one.
     * 
     * @return Listener instance
     */
    public static AudioListener get() {
        if(instance == null) {
            instance = new AudioListener();
        }
        return instance;
    }
    
    /**
     * Method that modifies the volume of the listener.
     * 
     * @param volume new volume from 0 to 1.
     */
    public void setVolume(float volume){
        alListenerf(AL_GAIN, volume);
        OpenALError.checkALError();
    }
    
    /**
     * Method that modifies the listener position.
     * 
     * @param position Vector containing new position
     */
    public void setPosition(Vector3f position) {
        alListener3f(AL_POSITION, position.x, position.y, position.z);
        OpenALError.checkALError();
    }
    
    /**
     * Method that modifies the listener velocity.
     * 
     * @param velocity Vector containing new velocity
     */
    public void setVelocity(Vector3f velocity) {
        alListener3f(AL_VELOCITY, velocity.x, velocity.y, velocity.z);
        OpenALError.checkALError();
    }
    
    /**
     * Method that modifies the listener orientation.
     * 
     * @param at vector containing the front values from the listener.
     * @param up vector containing the up values from the listener.
     */
    public void setOrientation(Vector3f at, Vector3f up){
        float[] orientation = {at.x, at.y, at.z, up.x, up.y, up.z};
        alListenerfv(AL_ORIENTATION, orientation);
        OpenALError.checkALError();
    }
}
