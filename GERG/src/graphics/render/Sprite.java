package graphics.render;

import graphics.textures.Texture2D;
import graphics.enums.BufferUsage;
import graphics.enums.PrimitiveType;
import graphics.vertex.Vertex;
import graphics.vertex.VertexArray;
import graphics.vertex.VertexBuffer;
import org.joml.Vector2f;

import java.util.stream.IntStream;

public class Sprite extends GameObject {

    private final int frameWidth, frameHeight;
    private int frameIndex;
    private Texture2D texture;
    private VertexBuffer vb;
    private VertexArray va;

    // Animation
    private boolean animating;
    private int numFrames, startFrameIndex;
    private float frameDuration, elapsedTime;

    // Directions
    public static final int DIRECTION_DOWN = 0;
    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_UP = 3;


    public Sprite(Vector2f position, Texture2D texture, int frameWidth, int frameHeight, int numFrames) {
        super(position, texture.getWidth(), texture.getHeight());
        this.texture = texture;

        this.animating = false;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.numFrames = numFrames;

        int vertexCount = 4;
        vb = new VertexBuffer(PrimitiveType.QUADS, BufferUsage.STREAM, vertexCount);
        this.va = new VertexArray(vb, IntStream.range(0, vertexCount).toArray());
    }

    public void startAnimation(int direction, float frameDuration) {
        int framesPerRow = texture.getWidth() / frameWidth;
        if(animating && startFrameIndex / framesPerRow == direction) return;
        this.frameDuration = frameDuration;
        this.elapsedTime = 0;
        this.startFrameIndex = frameIndex = direction * framesPerRow;
        this.animating = true;
    }

    public void stopAnimation() {
        //this.frameIndex = 0;
        this.animating = false;
    }


    @Override
    public void update(float deltaTime) {
        if(!animating) return;
        elapsedTime += deltaTime;
        if(elapsedTime >= frameDuration) {
            elapsedTime -= frameDuration;
            frameIndex++;

            if(frameIndex >= startFrameIndex + numFrames) {
                frameIndex = startFrameIndex;
                animating = false;
            }
        }
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        states.texture = texture;
        states.setTransform(getTransform());

        int framesPerRow = texture.getWidth() / frameWidth;
        float frameX = frameIndex % framesPerRow;
        float frameY = frameIndex / framesPerRow;
        float texCoordX1 = frameX * frameWidth / texture.getWidth();
        float texCoordY1 = frameY * frameHeight / texture.getHeight();
        float texCoordX2 = (frameX + 1) * frameWidth / texture.getWidth();
        float texCoordY2 = (frameY + 1) * frameHeight / texture.getHeight();
        Vertex[] vertices = new Vertex[] {
            new Vertex(0,0, texCoordX1, texCoordY1),
            new Vertex(1,0, texCoordX2, texCoordY1),
            new Vertex(1,1, texCoordX2, texCoordY2),
            new Vertex(0,1, texCoordX1, texCoordY2),
        };
        vb.setData(vertices);

        target.draw(va, states);
    }

    public void dispose(){
        va.dispose();
        texture.dispose();
    }
}
