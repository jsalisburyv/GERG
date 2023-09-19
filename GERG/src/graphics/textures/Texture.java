package graphics.textures;


import java.nio.ByteBuffer;

public interface Texture {
    
    public void bind();
    public void bind(int slot);
    public void unbind();
    public void unbind(int slot);
    
    public void setData(int[] data);
    public void setData(ByteBuffer data);
    public int getWidth();
    public int getHeight();
    
    public void dispose();
}
