import characters.Enemy;
import core.EngineLoop;
import core.Game;
import core.Window;
import core.config.Config;
import core.resources.ResourceManager;
import core.scenes.Menu;
import events.Event;
import events.KeyEvent;
import events.MouseEvent;
import events.WindowEvent;
import graphics.render.Camera;
import graphics.render.RenderStates;
import graphics.render.RenderTarget;
import graphics.render.Sprite;
import graphics.vertex.Color;
import map.Map;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import procgen.bsp.BSPTile;

import static map.Map.TILE_SIZE;

/**
 * Main class for the demonstration
 * @author Jonathan Salisbury
 */
public class Demo implements Game {


    private RenderTarget renderer;
    private RenderStates states;
    private Color background;
    private Map map;
    private Camera camera;
    private LifeCounter lives;

    private Sprite player;
    private Enemy enemy;

    /**
     * Method that initializes all objects used in the demo
     */
    @Override
    public void startUp() {
        renderer = new RenderTarget();
        states = new RenderStates("texture.glsl");
        background = new Color(49, 46, 61);

        map = new Map(100, 100);
        lives = new LifeCounter(5);

        camera = new Camera(6);

        player = new Sprite(new Vector2f(BSPTile.startingPoint).mul(TILE_SIZE), ResourceManager.getTexture("spritesheet.png"), 64, 64, 4);
        player.setTransform(new Matrix4f());
        player.setScale(TILE_SIZE);

        Vector2f startingPos = new Vector2f(BSPTile.startingPoint).add(4, 0).mul(TILE_SIZE);
        enemy = new Enemy(startingPos, map);
        enemy.setScale(TILE_SIZE * 1.2f);
    }

    /**
     * Method that updates all the object and renders them to screen
     * @param deltatime time since last frame
     */
    @Override
    public void update(float deltatime) {
        player.update(deltatime);
        enemy.setPlayerPosition(player.getPosition());
        enemy.update(deltatime);

        states.setProjection("projection", camera.getProjectionMatrix());
        renderer.clear(background);
        renderer.draw(map, states);
        renderer.draw(player, states);
        renderer.draw(enemy, states);

        states.setProjection("projection", new Matrix4f());
        renderer.draw(lives, states);
    }

    /**
     * Method that handles ImGui rendering
     */
    @Override
    public void onImGuiRender() {
//        float[] color = background.normalize().toFloatArray();
//        if(ImGui.colorPicker4("background", color))
//            background = new Color(color[0] * 255, color[1] * 255, color[2] * 255);
    }

    /**
     * Method for managing the events of the demo,
     * moves the character and map with the input of the user
     * @param event triggered Event
     */
    @Override
    public void onEvent(Event event) {
        float SPEED = TILE_SIZE;
        if (event instanceof KeyEvent.KeyPressedEvent evt) {
            Vector2f movement = new Vector2f();
            int direction = 0;
            switch (evt.getKeycode()) {
                case GLFW.GLFW_KEY_W -> {
                    movement.set(0, -SPEED);
                    direction = Sprite.DIRECTION_UP;
                }
                case GLFW.GLFW_KEY_S -> {
                    movement.set(0, SPEED);
                    direction = Sprite.DIRECTION_DOWN;
                }
                case GLFW.GLFW_KEY_A -> {
                    movement.set(-SPEED, 0);
                    direction = Sprite.DIRECTION_LEFT;
                }
                case GLFW.GLFW_KEY_D -> {
                    movement.set(SPEED, 0);
                    direction = Sprite.DIRECTION_RIGHT;
                }
                case GLFW.GLFW_KEY_ESCAPE -> EngineLoop.exit();
            }
            player.startAnimation(direction, 0.1f);
            player.translate(movement);
            camera.translate(movement.negate());
            if (map.checkCollision(player)) {
                player.translate(movement);
                camera.translate(movement.negate());
            }
        } else if(event instanceof KeyEvent.KeyReleasedEvent evt) {
            if(evt.getKeycode() == GLFW.GLFW_KEY_W  || evt.getKeycode() == GLFW.GLFW_KEY_A
                    || evt.getKeycode() == GLFW.GLFW_KEY_S || evt.getKeycode() == GLFW.GLFW_KEY_D ) {
                player.stopAnimation();
            }
        } else if(event instanceof WindowEvent.WindowResizedEvent evt) {
            Vector2f center = new Vector2f(Window.getSize()).div(2f);
            float zoomFactor = 5f;
            Vector2f cameraPosition = new Vector2f(center).sub(new Vector2f(player.getPosition()).mul(zoomFactor));
            camera = new Camera(cameraPosition, zoomFactor);
        } else if(event instanceof MouseEvent.MouseScrolledEvent evt) {
            camera.zoom(evt.getYOffset());
        }
    }

    /**
     * Method that frees the resources being used by the demo
     */
    @Override
    public void shutdown() {
        map.dispose();
        lives.dispose();
        player.dispose();
        enemy.dispose();
        states.dispose();
    }

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        Config.DEFAULT_CONFIG_PATH = "config\\engine.config";
        Demo game = new Demo();
        Menu menu = new MainMenu();
        EngineLoop.start(game, menu);
    }
}