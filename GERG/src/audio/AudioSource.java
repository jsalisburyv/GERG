package audio;

import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.openal.AL10.*;

/**
 * Class that represents an audio source for 3d audio.
 * @author Jonathan Salisbury
 */
public class AudioSource {
    
    private final int sourcePointer;

    /**
     * Constructor that creates the audio soruce using the given AudioClip
     * @param clip Audio to be played by the source
     */
    public AudioSource(AudioClip clip) {
        this.sourcePointer = alGenSources();
        alSourcei(sourcePointer, AL_BUFFER, clip.getPointer());
        OpenALError.checkALError();
    }

    /**
     * Method that changes the clip of the source.
     * @param clip new Audio to be played  by the source.
     */
    public void setAudioClip(AudioClip clip){
        alSourcei(sourcePointer, AL_BUFFER, clip.getPointer());
        OpenALError.checkALError();
    }

    /**
     * Method that starts playing the audioclip.
     */
    public void play(){
        alSourcePlay(sourcePointer);
        OpenALError.checkALError();
    }

    /**
     * Method that pauses the audioclip (if played will continue).
     */
    public void pause(){
        alSourcePause(sourcePointer);
        OpenALError.checkALError();
    }

    /**
     * Method that stops the audioclip (if played it will restart).
     */
    public void stop(){
        alSourceStop(sourcePointer);
        OpenALError.checkALError();
    }

    /**
     * Util method for setting the gain value of the clip
     * @param volume new volume to set for the source
     */
    public void setVolume(float volume){
        setProperty(Property.GAIN, volume);
    }

    /**
     * method that deletes the source.
     */
    public void delete(){
        alDeleteSources(sourcePointer);
        OpenALError.checkALError();
    }

    /**
     * Method that sets a boolean property
     * @param prop property to be changed
     * @param value new value to set
     */
    public void setProperty(Property prop, boolean value) {
        alSourcei(this.sourcePointer, prop.toOpenALConstant(), value ? AL_TRUE : AL_FALSE);
        OpenALError.checkALError();
    }

    /**
     * Method that sets a integer property
     * @param prop property to be changed
     * @param value new value to set
     */
    public void setProperty(Property prop, int value) {
        alSourcei(this.sourcePointer, prop.toOpenALConstant(), value);
        OpenALError.checkALError();
    }

    /**
     * Method that sets a float property
     * @param prop property to be changed
     * @param value new value to set
     */
    public void setProperty(Property prop, float value) {
        alSourcef(this.sourcePointer, prop.toOpenALConstant(), value);
        OpenALError.checkALError();
    }

    /**
     * Method that sets a Vector3f property
     * @param prop property to be changed
     * @param value new value to set
     */
    public void setProperty(Property prop, Vector3f value) {
        alSource3f(this.sourcePointer, prop.toOpenALConstant(), value.x, value.y, value.z);
        OpenALError.checkALError();
    }

    /**
     * Method that returns the current value of a boolean property
     * @param prop property to be obtained
     * @return current value
     */
    public boolean getPropertyBool(Property prop) {
        int value = alGetSourcei(this.sourcePointer, prop.toOpenALConstant());
        OpenALError.checkALError();
        return value == AL_TRUE;
    }

    /**
     * Method that returns the current value of a integer property
     * @param prop property to be obtained
     * @return current value
     */
    public int getPropertyInt(Property prop) {
        int value = alGetSourcei(this.sourcePointer, prop.toOpenALConstant());
        OpenALError.checkALError();
        return value;
    }

    /**
     * Method that returns the current value of a float property
     * @param prop property to be obtained
     * @return current value
     */
    public float getPropertyFloat(Property prop) {
        float value = alGetSourcef(this.sourcePointer, prop.toOpenALConstant());
        OpenALError.checkALError();
        return value;
    }

    /**
     * Method that returns the current value of a Vector3f property
     * @param prop property to be obtained
     * @return current value
     */
    public Vector3f getPropertyVector3f(Property prop) {
        float[] val1 = new float[1];
        float[] val2 = new float[1];
        float[] val3 = new float[1];
        alGetSource3f(this.sourcePointer, prop.toOpenALConstant(), val1, val2, val3);
        OpenALError.checkALError();
        return new Vector3f(val1[0], val2[0], val3[0]);
    }

    /**
     * Auxiliary class that containes all modifiable properties of the source
     */
    public static enum Property {
        PITCH,
        GAIN,
        MIN_GAIN,
        MAX_GAIN,
        MAX_DISTANCE,
        ROLLOFF_FACTOR,
        CONE_OUTER_GAIN,
        CONE_INNER_ANGLE,
        REFERENCE_DISTANCE,
        POSITION,
        VELOCITY,
        DIRECTION,
        SOURCE_RELATIVE,
        LOOPING,
        BUFFER,
        SOURCE_STATE;

        private int toOpenALConstant() {
            return switch (this) {
                case PITCH -> AL_PITCH;
                case GAIN -> AL_GAIN;
                case MIN_GAIN -> AL_MIN_GAIN;
                case MAX_GAIN -> AL_MAX_GAIN;
                case MAX_DISTANCE -> AL_MAX_DISTANCE;
                case ROLLOFF_FACTOR -> AL_ROLLOFF_FACTOR;
                case CONE_OUTER_GAIN -> AL_CONE_OUTER_GAIN;
                case CONE_INNER_ANGLE -> AL_CONE_INNER_ANGLE;
                case REFERENCE_DISTANCE -> AL_REFERENCE_DISTANCE;
                case POSITION -> AL_POSITION;
                case VELOCITY -> AL_VELOCITY;
                case DIRECTION -> AL_DIRECTION;
                case SOURCE_RELATIVE -> AL_SOURCE_RELATIVE;
                case LOOPING -> AL_LOOPING;
                case BUFFER -> AL_BUFFER;
                case SOURCE_STATE -> AL_SOURCE_STATE;
            };
        }
    }
}
