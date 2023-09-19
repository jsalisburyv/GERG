package graphics.tilemap;

import core.resources.ResourceManager;
import graphics.textures.Texture2D;
import org.joml.Vector2i;

public class Tileset extends Texture2D {

    private Vector2i texSize;

    public Tileset(String textureFilename, Vector2i texSize) {
        super(ResourceManager.TEXTURE_PATH + textureFilename);
        this.texSize = new Vector2i(texSize);
    }

    public Vector2i getTexSize() { return texSize; }
}
