/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package audio;

import org.lwjgl.openal.AL11;
import org.lwjgl.openal.ALC11;

/**
 * Class for handling OpenAL errors
 * @author Jonathan Salisbury
 */
public class OpenALError {

    /**
     * Method that checks if there is a OpenAL error after a function call.
     */
    public static void checkALError() {
        int result;
        if ((result = AL11.alGetError()) != AL11.AL_NO_ERROR) {
            throw new RuntimeException(getALErrorString(result));
        }
    }

    /**
     * Method that checks if there is a OpenAl context error after a ALC function call.
     * @param handle
     */
    public static void checkALCError(long handle) {
        int result;
        if ((result = ALC11.alcGetError(handle)) != ALC11.ALC_NO_ERROR) {
            throw new RuntimeException(getALCErrorString(result));
        }
    }

    /**
     * Method that return the string value for the given OpenAL error.
     * @param err error number
     * @return String representation of the error.
     */
    private static String getALErrorString(int err) {
        return switch (err) {
            case AL11.AL_NO_ERROR -> "AL_NO_ERROR";
            case AL11.AL_INVALID_NAME -> "AL_INVALID_NAME";
            case AL11.AL_INVALID_ENUM -> "AL_INVALID_ENUM";
            case AL11.AL_INVALID_VALUE -> "AL_INVALID_VALUE";
            case AL11.AL_INVALID_OPERATION -> "AL_INVALID_OPERATION";
            case AL11.AL_OUT_OF_MEMORY -> "AL_OUT_OF_MEMORY";
            default -> "No such error code";
        };
    }

    /**
     * Method that return the string value for the given OpenAL context error.
     * @param err error number
     * @return String representation of the error.
     */
    private static String getALCErrorString(int err) {
        return switch (err) {
            case ALC11.ALC_NO_ERROR -> "AL_NO_ERROR";
            case ALC11.ALC_INVALID_DEVICE -> "ALC_INVALID_DEVICE";
            case ALC11.ALC_INVALID_CONTEXT -> "ALC_INVALID_CONTEXT";
            case ALC11.ALC_INVALID_ENUM -> "ALC_INVALID_ENUM";
            case ALC11.ALC_INVALID_VALUE -> "ALC_INVALID_VALUE";
            case ALC11.ALC_OUT_OF_MEMORY -> "ALC_OUT_OF_MEMORY";
            default -> "no such error code";
        };
    }
}
