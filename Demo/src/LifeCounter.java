import core.resources.ResourceManager;
import graphics.textures.Texture2D;
import graphics.render.Drawable;
import graphics.render.RenderStates;
import graphics.render.RenderTarget;
import graphics.render.Sprite;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import procgen.bsp.BSPTile;

import static map.Map.TILE_SIZE;

/**
 * Class for displaying the hearts on the top left of the screen.
 */
public class LifeCounter implements Drawable {

    private int numLives;
    private int liveCount;
    private Texture2D emptyHeart;
    private Texture2D filledHeart;

    private Sprite[] sprites;

    /**
     * Default constructor
     * @param numLives number of lives to display
     */
    public LifeCounter(int numLives) {
        this.numLives = numLives;
        this.liveCount = numLives;

        filledHeart = ResourceManager.getTexture("filled_heart.png");
        emptyHeart = ResourceManager.getTexture("empty_heart.png");

        Vector2f startingPos = new Vector2f(-0.99f, 0.95f);
        sprites = new Sprite[numLives];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Sprite(new Vector2f(startingPos), filledHeart, 16, 16, 1);
            sprites[i].setTransform(new Matrix4f());
            sprites[i].scale(0.05f);
            startingPos.x += 0.05;
        }
    }


    /**
     * Method that renders the sprites to the screen
     * @param target renderer
     * @param states states
     */
    @Override
    public void draw(RenderTarget target, RenderStates states) {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i].draw(target, states);
        }
    }

    /**
     * Method that frees the sprites resources
     */
    public void dispose() {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i].dispose();
        }
    }
}
