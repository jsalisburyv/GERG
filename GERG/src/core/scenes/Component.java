package core.scenes;

public abstract class Component {

    public GameObject gameObject = null;

    public abstract void start();
    public abstract void update(double deltaTime);
}
