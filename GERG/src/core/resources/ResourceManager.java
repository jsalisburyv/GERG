package core.resources;

import audio.AudioClip;
import core.config.Config;
import graphics.core.Shader;
import graphics.textures.Texture2D;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that loads and stores the engine resources.
 * @author Jonathan Salisbury
 */
public class ResourceManager {

    public static final String ASSET_PATH = Config.getStringValue("assets.directory");
    public static final String AUDIO_PATH = ASSET_PATH + "audio/";
    public static final String SHADER_PATH = ASSET_PATH + "shaders/";
    public static final String TEXTURE_PATH = ASSET_PATH + "textures/";
    private static final Map<String, AudioClip> audios = new HashMap<>();
    private static final Map<String, Shader> shaders = new HashMap<>();
    private static final Map<String, Texture2D> textures = new HashMap<>();

    /**
     * Method for getting a specific audio file, if the file is already loaded it is returned directly, otherwise
     * it is loaded and stored for future uses.
     * @param filename string filename of the resource.
     * @return Audioclip instance of the resource.
     */
    public static AudioClip getAudio(String filename) {
        String path = AUDIO_PATH + filename;
        File file = new File(path);
        if(audios.containsKey(file.getAbsolutePath())){
            return audios.get(file.getAbsolutePath());
        }
        AudioClip audio = new AudioClip(path);
        audios.put(file.getAbsolutePath(), audio);
        return audio;
    }

    /**
     * Method for getting a specific shader file, if the file is already loaded it is returned directly, otherwise
     * it is loaded and stored for future uses.
     * @param filename string filename of the resource.
     * @return Shader instance of the resource.
     */
    public static Shader getShader(String filename) {
        String path = SHADER_PATH + filename;
        File file = new File(path);
        if(shaders.containsKey(file.getAbsolutePath())){
            return shaders.get(file.getAbsolutePath());
        }
        Shader shader = new Shader(path);
        shaders.put(file.getAbsolutePath(), shader);
        return shader;
    }

    /**
     * Method for getting a specific texture file, if the file is already loaded it is returned directly, otherwise
     * it is loaded and stored for future uses.
     * @param filename string filename of the resource.
     * @return Texture instance of the resource.
     */
    public static Texture2D getTexture(String filename) {
        String path = TEXTURE_PATH + filename;
        File file = new File(path);
        if(textures.containsKey(file.getAbsolutePath())){
            return textures.get(file.getAbsolutePath());
        }
        Texture2D texture = new Texture2D(path);
        textures.put(file.getAbsolutePath(), texture);
        return texture;
    }

    /**
     * Method for unloading all audio resources at engine shutdown.
     */
    public static void unloadAllAudios() {
        for(AudioClip clip: audios.values()) {
            clip.delete();
        }
    }
}
