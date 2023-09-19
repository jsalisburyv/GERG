package audio;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.system.MemoryStack;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.libc.LibCStdlib.free;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;

/**
 * Class that holds the data and openAL handle for an audio clip.
 * @author Jonathan Salisbury
 */
public class AudioClip {

    private final int bufferPointer; // OpenAL handle

    /**
     * Allocate the handler, and load the buffer data of the given filepath.
     * 
     * @param filepath
     */
    public AudioClip(String filepath) {
        this.bufferPointer = alGenBuffers();
        OpenALError.checkALError();
        
        FileType type = FileType.getType(filepath);
        AudioData data = type.loadData(filepath);

        alBufferData(bufferPointer, data.format, 
                data.rawAudioBuffer, data.sampleRate);
        OpenALError.checkALError();
        free(data.rawAudioBuffer);
    }

    /**
     * Returns the OpenAL handle for the audio data.
     * 
     * @return int handle id
     */
    public int getPointer() {
        return this.bufferPointer;
    }

    /**
     * Deletes the audio data from OpenAL.
     */
    public void delete() {
        alDeleteBuffers(bufferPointer);
        OpenALError.checkALError();
    }

    /**
     * Enum that represents the different audio file formats supported.
     * ATM only OGG is supported.
     */
    public enum FileType {
        OGG,
        WAV,
        MP3;

        /**
         * Method that returns the FileType of a file given its name.
         * @param filepath source file
         * @return Enum value
         */
        public static FileType getType(String filepath) {
            int lastIndexOf = filepath.lastIndexOf(".");
            if (lastIndexOf != -1) {
                String ext = filepath.substring(lastIndexOf + 1);
                if (ext.equalsIgnoreCase(OGG.name())) {
                    return OGG;
                } else if (ext.equalsIgnoreCase(WAV.name())) {
                    return WAV;
                } else if (ext.equalsIgnoreCase(MP3.name())) {
                    return MP3;
                }
            }
            throw new RuntimeException("Audio File format not supported.");
        }

        /**
         * Returns the AudioData of the given file depending of its extension.
         * 
         * @param filepath
         * @return 
         */
        public AudioData loadData(String filepath) {
            return switch (this) {
                case OGG -> loadOGG(filepath);
                case WAV -> loadWAV(filepath);
                case MP3 -> loadMP3(filepath);
                default -> null;
            };
        }

        private AudioData loadOGG(String filepath) {
            AudioData data = new AudioData();
            int channels;
            try ( MemoryStack stack = stackPush()) {
                IntBuffer channelsBuffer = stack.mallocInt(1),
                        sampleRateBuffer = stack.mallocInt(1);
                data.rawAudioBuffer = stb_vorbis_decode_filename(filepath,
                        channelsBuffer, sampleRateBuffer);
                channels = channelsBuffer.get(0);
                data.sampleRate = sampleRateBuffer.get(0);
            }
            data.format = channels == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;
            return data;
        }

        private AudioData loadWAV(String filepath) {
            return null;
        }

        private AudioData loadMP3(String filepath) {
            return null;
        }
    }

    /**
     * Class that represents the necessary information for loading a clip into 
     * a OpenAl buffer.
     */
    private static class AudioData {

        private ShortBuffer rawAudioBuffer;
        private int format, sampleRate;
    }
}
