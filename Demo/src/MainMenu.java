import core.scenes.Menu;
import graphics.render.Camera;
import graphics.render.RenderStates;
import graphics.render.RenderTarget;
import graphics.textures.Font;
import graphics.vertex.Color;
import org.joml.Matrix4f;

/**
 * Class for displaying the menu
 * Not functional due to text rendering
 * @author Jonathan Salisbury
 */
public class MainMenu extends Menu {

    private RenderTarget renderer;
    private RenderStates states;
    private Font font;
    private Camera camera;

    public MainMenu() {
        this.inMenu = false;
        addOption("Start Game", true);
        addOption("Exit", true);

    }

    @Override
    public void startUp() {
        renderer = new RenderTarget();
        font = new Font("font.png", 32);
        states = new RenderStates("texture.glsl");
        states.setTransform(new Matrix4f());

        camera = new Camera();
    }


    @Override
    public void handleMenuOption(Menu.MenuOption option) {

    }

    @Override
    public void render() {
        if(!inMenu) return;
        states.setProjection("projection", camera.getProjectionMatrix());
        MenuOption option = options.get(0);
        renderer.clear(Color.BLACK);
        font.draw(option.getText(), renderer, new RenderStates());

    }

    @Override
    public void dispose() {

    }
}
