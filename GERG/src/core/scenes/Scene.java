package core.scenes;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    /*
        map(lvlName, lvl)
        register -> addLevel to map
        getLevelNames()
        loadLvl() ->
     */

    // camera
    private boolean isRunning;
    protected List<GameObject> gameObjects;

    public Scene() {
        isRunning = false;
    }

    public void init() {
        gameObjects = new ArrayList<>();
    }

    public void start() {
        gameObjects.forEach(GameObject::start);
        this.isRunning = true;
    }

    public void addGameOject(GameObject gameObject) {
        if(!isRunning) {
            gameObjects.add(gameObject);
        } else {
            gameObjects.add(gameObject);
            gameObject.start();
        }
    }

    public abstract void update(double deltaTime);
}
