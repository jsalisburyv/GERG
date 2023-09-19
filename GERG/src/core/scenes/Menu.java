package core.scenes;

import events.Event;
import events.KeyEvent;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.List;

public abstract class Menu {

    protected static int keyToOpenMenu = GLFW.GLFW_KEY_ESCAPE;

    protected List<MenuOption> options;
    protected int selectedOptionIndex;
    protected boolean inMenu;

    public Menu() {
        options = new ArrayList<>();
        selectedOptionIndex = 0;
        inMenu = true;
    }

    public void addOption(String text, boolean enabled) {
        options.add(new MenuOption(text, enabled));
    }

    public void onEvent(Event event) {
        if(event instanceof KeyEvent.KeyPressedEvent evt) {
            if (evt.getKeycode() == keyToOpenMenu) {
                inMenu = !inMenu;
            } else if (evt.getKeycode() == GLFW.GLFW_KEY_UP) {
                selectedOptionIndex--;
                if (selectedOptionIndex < 0) {
                    selectedOptionIndex = options.size() - 1;
                }
            } else if (evt.getKeycode() == GLFW.GLFW_KEY_DOWN) {
                selectedOptionIndex++;
                if (selectedOptionIndex >= options.size()) {
                    selectedOptionIndex = 0;
                }
            } else if (evt.getKeycode() == GLFW.GLFW_KEY_ENTER) {
                MenuOption selectedOption = options.get(selectedOptionIndex);
                if (selectedOption.isEnabled()) {
                    handleMenuOption(selectedOption);
                }
            }
        }
    }

    public boolean isInMenu(){
        return inMenu;
    }

    public abstract void startUp();
    public abstract void handleMenuOption(MenuOption option);
    public abstract void render();
    public abstract void dispose();


    protected static class MenuOption {
        private final String text;
        private final boolean enabled;

        public MenuOption(String text, boolean enabled) {
            this.text = text;
            this.enabled = enabled;
        }

        public String getText() {
            return text;
        }

        public boolean isEnabled() {
            return enabled;
        }

    }
}
