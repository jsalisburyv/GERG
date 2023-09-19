package core.scenes;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private String name;
    private List<Component> components;

    public GameObject(String name) {
        this.name = name;
        components = new ArrayList<>();
    }

    public void addComponent(Component component) {
        this.components.add(component);
        component.gameObject = this;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            if(componentClass.isAssignableFrom(component.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for(Component component: components) {
            if(componentClass.isAssignableFrom(component.getClass())) {
                return componentClass.cast(component);
            }
        }
        return null;
    }

    public void start() {
        components.forEach(Component::start);
    }

    public void update(double deltaTime) {
        components.forEach((component) -> component.update(deltaTime));
    }
}
