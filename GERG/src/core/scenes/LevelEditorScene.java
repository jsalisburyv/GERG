package core.scenes;

import core.scenes.components.SpriteRenderer;

public class LevelEditorScene extends Scene {

    private GameObject test;
    private boolean first = true;

    @Override
    public void init() {
        super.init();
        test = new GameObject("test");
        test.addComponent(new SpriteRenderer());
        addGameOject(test);
    }

    public LevelEditorScene() {
        //System.out.println("inside level editor scene");
    }

    @Override
    public void update(double deltaTime) {
        gameObjects.forEach((gameObject -> gameObject.update(deltaTime)));
        if(first){
            GameObject test2 = new GameObject("test2");
            test2.addComponent(new SpriteRenderer());
            addGameOject(test2);
            first = false;
            //mirar scene si running
        }
    }
}
