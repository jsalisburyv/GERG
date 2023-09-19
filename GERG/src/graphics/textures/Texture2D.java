package graphics.textures;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.logging.Logger;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL45C.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;


public class Texture2D implements Texture {
    
    private static final Logger LOGGER = Logger.getLogger(Texture2D.class.getSimpleName());

    private final int texID, width, height, internalFormat, dataFormat;
    
    public Texture2D(String path) {
        stbi_set_flip_vertically_on_load(false);
        IntBuffer tempWidth = BufferUtils.createIntBuffer(1);
        IntBuffer tempHeight = BufferUtils.createIntBuffer(1);
        IntBuffer components = BufferUtils.createIntBuffer(1);
        ByteBuffer data = stbi_load(path, tempWidth, tempHeight, components, 0);
        assert data != null: "Failed to load image '" + path + "'";
        width = tempWidth.get();
        height = tempHeight.get();
        
        int channels = components.get();
        internalFormat = channels == 4 ? GL_RGBA8 : GL_RGB8;
        dataFormat = channels == 4 ? GL_RGBA : GL_RGB;

        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);
        glTextureStorage2D(texID, 1, internalFormat, width, height);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTextureSubImage2D(texID, 0, 0, 0, width, height, dataFormat, GL_UNSIGNED_BYTE, data);
        stbi_image_free(data);
    }
    
    public Texture2D(int width, int height) {
        this.width = width;
        this.height = height;
        internalFormat = GL_RGBA8;
        dataFormat = GL_RGBA;
        
        texID = glCreateTextures(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, texID);
        glTextureStorage2D(texID, 1, internalFormat, width, height);
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    }

    public int getId() {
        return texID;
    }

    @Override
    public void bind() {
        bind(0);
    }

    @Override
    public void bind(int slot) {
        glBindTextureUnit(slot, texID);
    }
    
    @Override
    public void unbind() {
        unbind(0);
    }

    @Override
    public void unbind(int slot) {
        glBindTextureUnit(slot, 0);
    }

    @Override
    public void setData(int[] data) {
        glTextureSubImage2D(texID, 0, 0, 0, width, height, dataFormat, GL_UNSIGNED_BYTE, data);
    }

    @Override
    public void setData(ByteBuffer data) {
        glTextureSubImage2D(texID, 0, 0, 0, width, height, dataFormat, GL_UNSIGNED_BYTE, data);
    }

    @Override
    public int getWidth() {
        return width; 
    }

    @Override
    public int getHeight() {
        return height; 
    }

    @Override
    public void dispose() {
        glDeleteTextures(texID);     
    }
    
}
